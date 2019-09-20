package com.example.apppermisos;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.fragments.PermisosPendientesFrag;
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;

import org.json.JSONArray;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetallesPermisosFragment extends Fragment {
    private Persona per;
    private Button btn_aprob;
    private Button btn_den;
    private RequestQueue requestQueue;
    private ImageView imagen;
    private TextView nombre;
    private TextView fechasol;
    private TextView tipoper;
    private TextView descripcion;
    private TextView fecha1;
    private TextView fecha2;
    private PermisosPendientesFrag.OnFragmentInteractionListener mListener;

    public DetallesPermisosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detallesPermiso =  inflater.inflate(R.layout.fragment_detalles_permisos, container, false);
        btn_aprob = detallesPermiso.findViewById(R.id.btn_aprobado);
        btn_den = detallesPermiso.findViewById(R.id.btn_rechazado);
        imagen=detallesPermiso.findViewById(R.id.im_revisionP);
        nombre= detallesPermiso.findViewById(R.id.tv_nombre_docente_r);
        fechasol = detallesPermiso.findViewById(R.id.tv_fecha_solicitud_r);
        descripcion = detallesPermiso.findViewById(R.id.tv_descripcion_r);
        fecha1 = detallesPermiso.findViewById(R.id.tv_fecha1_r);
        fecha2 = detallesPermiso.findViewById(R.id.tv_fecha2_r);
        tipoper = detallesPermiso.findViewById(R.id.tv_tipo_permiso_r);
        per= getArguments().getParcelable("Persona");
        int indice= getArguments().getInt("indice");
        Permiso permiso= per.getPermisos().get(indice);
        nombre.setText(permiso.getPersonaSolicita());
        fechasol.setText(permiso.getFechaSolicitud());
        descripcion.setText(permiso.getDesc());
        tipoper.setText(permiso.getTipoPermiso());
        fecha1.setText(permiso.getFechaInicio());
        fecha2.setText(permiso.getFechaFin());
        final int num=permiso.getNumSol();
        btn_aprob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aprobarPermiso("http://puntosingular.mx/app_permisos/modificarStatus?estatus=1&numSol="+num+"");
                Toast.makeText(getContext(), "El permiso fue aprobado",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.layout_principal)).commit();
            }
        });
        btn_den.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denegarPermiso("http://puntosingular.mx/app_permisos/modificarStatus?estatus=2&numSol="+num+"");
                Toast.makeText(getContext(), "El permiso fue denegado",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.layout_principal)).commit();
            }
        });
        return detallesPermiso;
    }
    public void aprobarPermiso(String url){
        StringRequest stringRequest= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"El permiso fue aprobado",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al aprobar permiso "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    public void denegarPermiso(String url){
        StringRequest stringRequest= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"El permiso fue denegado",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al denegar permiso "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PermisosPendientesFrag.OnFragmentInteractionListener) {
            mListener = (PermisosPendientesFrag.OnFragmentInteractionListener) context;
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

}
