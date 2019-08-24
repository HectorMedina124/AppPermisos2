package com.example.apppermisos.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.R;
import com.example.apppermisos.Registro_Activity;
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PermisosFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PermisosFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PermisosFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Persona per;

    private OnFragmentInteractionListener mListener;
    private RequestQueue requestQueue;

    public PermisosFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PermisosFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static PermisosFrag newInstance(String param1, String param2) {
        PermisosFrag fragment = new PermisosFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            per = getArguments().getParcelable("Persona");
            llenarPermisos("http://puntosingular.mx/app_permisos/ConsultarPermisosHistorial?rfc="+per.getRfc());
        }
    }

    private void llenarPermisos(String url) {
            JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    ArrayList<Permiso> permisos=new ArrayList<Permiso>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            Permiso permiso=new Permiso();
//                            permiso.setDesc(jsonObject.getString("Descripcion"));
//                            Date fechaF=new Date(jsonObject.getString("f_fin_sol"));
//                            Date fechaI=new Date(jsonObject.getString("f_inicio_sol"));
//                            Date fechaSol=new Date(jsonObject.getString("f_solicitud"));
//                            Date fechaAprob=new Date(jsonObject.getString("f_autorizacion"));
//                            permiso.setFechaFin(fechaF);
//                            permiso.setFechaInicio(fechaI);
//                            permiso.setFechaSolicitud(fechaSol);
//                            permiso.setFechaAutorizacion(fechaAprob);
                            permiso.setHoraFin(jsonObject.getString("h_fin_sol"));
                            permiso.setHoraI(jsonObject.getString("h_inicio_sol"));
                            permiso.setPersonaAutoriza(jsonObject.getString("persona_autoriza"));
                            permiso.setStatus(jsonObject.getString("estatus_sol"));
                            permiso.setTipoPermiso(jsonObject.getString("permiso_per"));
                            permisos.add(permiso);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    per.setPermisos(permisos);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        requestQueue= Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonArrayRequest);


        }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permisos, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
