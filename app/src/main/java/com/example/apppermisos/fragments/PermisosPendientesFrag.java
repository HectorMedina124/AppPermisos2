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
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PermisosPendientesFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PermisosPendientesFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PermisosPendientesFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Persona per;
    private ArrayList<Permiso>permisos;

    private OnFragmentInteractionListener mListener;
    private RequestQueue requestQueue;

    public PermisosPendientesFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PermisosPendientesFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static PermisosPendientesFrag newInstance(String param1, String param2) {
        PermisosPendientesFrag fragment = new PermisosPendientesFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler hn= new Handler();
        if (getArguments() != null) {
            per = getArguments().getParcelable("Persona");
            obtenerPermisosPendientes("http://puntosingular.mx/app_permisos/ConsultarPermisosPendientes?cve_per="+per.getClave());
        }
        hn.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),permisos.get(0).toString(),Toast.LENGTH_SHORT).show();

            }
        },500);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permisos_pendientes, container, false);
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
    public  void obtenerPermisosPendientes(String url){
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                permisos=new ArrayList<>();
                SimpleDateFormat df= new SimpleDateFormat("yy-MM-dd");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Permiso permiso=new Permiso();
                        permiso.setNumSol(jsonObject.getInt("num_sol"));
                        permiso.setDesc(jsonObject.getString("Descripcion"));
                        String fechaF=jsonObject.getString("f_fin_sol");
                        String fechaI=jsonObject.getString("f_inicio_sol");
                        String fechaSol=jsonObject.getString("f_solicitud");
                        String fechaAprob=jsonObject.getString("f_autorizacion");
                        permiso.setFechaFin(fechaF);
                        permiso.setFechaInicio(fechaI);
                        permiso.setFechaSolicitud(fechaSol);
                        permiso.setFechaAutorizacion(fechaAprob);
                        permiso.setHoraFin(jsonObject.getString("h_fin_sol"));
                        permiso.setHoraI(jsonObject.getString("h_inicio_sol"));
                        permiso.setPersonaAutoriza(jsonObject.getString("personaAut"));
                        permiso.setStatus(jsonObject.getString("estatus_sol"));
                        permiso.setTipoPermiso(jsonObject.getString("permiso_per"));
                        permisos.add(permiso);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                per.setPermisos(permisos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);


    }
}