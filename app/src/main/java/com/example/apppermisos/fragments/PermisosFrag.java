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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PermisosFrag extends Fragment {
    private Persona per;
    private RequestQueue requestQueue;
    public PermisosFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler hn= new Handler();

        if (getArguments() != null) {
            per = getArguments().getParcelable("Persona");
            llenarPermisos("http://puntosingular.mx/app_permisos/ConsultarPermisosHistorial?rfc="+per.getRfc());
        }
        hn.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), per.getPermisos().get(0).getFechaAutorizacion(), Toast.LENGTH_SHORT).show();

            }
        },500);
    }

    private void llenarPermisos(String url) {
            JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    ArrayList<Permiso> permisos=new ArrayList<Permiso>();
                    SimpleDateFormat df= new SimpleDateFormat("yy-MM-dd");
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            Permiso permiso=new Permiso();
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

}
