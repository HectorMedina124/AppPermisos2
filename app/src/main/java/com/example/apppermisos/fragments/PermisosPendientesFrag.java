package com.example.apppermisos.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.AdaptadorPerPen;
import com.example.apppermisos.AdaptadorPermiso;
import com.example.apppermisos.DetallesPermisosFragment;
import com.example.apppermisos.R;
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class PermisosPendientesFrag extends Fragment {
    private Persona per;
    private ArrayList<Permiso>permisos;

    private OnFragmentInteractionListener mListener;
    private RequestQueue requestQueue;
    private View permisosP;
    private ListView listaP;

    public PermisosPendientesFrag() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler hn= new Handler();
        permisos=new ArrayList<>();
        if (getArguments() != null) {
            per = getArguments().getParcelable("Persona");
            obtenerPermisosPendientes("http://puntosingular.mx/app_permisos/ConsultarPermisosPendientes?cve_per="+per.getClave());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        permisosP=inflater.inflate(R.layout.fragment_permisos_pendientes, container, false);
        Handler hn= new Handler();
        hn.postDelayed(new Runnable() {
            @Override
            public void run() {
                Adapter adaptadorPermisos = new AdaptadorPerPen(getContext(), permisos);
                if(!permisos.isEmpty()) {
                    listaP = permisosP.findViewById(R.id.listPermisosPendientes);
                    listaP.setAdapter((ListAdapter) adaptadorPermisos);
                    listaP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Fragment fragment = new DetallesPermisosFragment();
                            Bundle b = new Bundle();
                            b.putParcelable("Persona", per);
                            b.putInt("indice", i);
                            fragment.setArguments(b);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal, fragment).commit();
                            //Toast.makeText(getActivity().getApplicationContext(), "Seleccionaste " + permisos.get(i), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else {
                    TextView txtVacio=permisosP.findViewById(R.id.tv_vacio2);
                    txtVacio.setVisibility(View.VISIBLE);

                }

            }
        },500);
        return permisosP;
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
                        permiso.setPersonaSolicita(jsonObject.getString("personaSol"));
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
