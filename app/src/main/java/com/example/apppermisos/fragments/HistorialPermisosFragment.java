package com.example.apppermisos.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.apppermisos.AdaptadorPermiso;
import com.example.apppermisos.R;
import com.example.apppermisos.objetos.Permiso;

import java.util.ArrayList;

public class HistorialPermisosFragment extends Fragment {

    private RadioButton rb_todos;
    private RadioButton rb_aprobados;
    private RadioButton rb_denegados;
    private ListView lv_solicitudes;
    private ArrayList<Permiso> permisos;
    private View hpf;


    public HistorialPermisosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        hpf =  inflater.inflate(R.layout.fragment_historial_permisos, container, false);
        rb_todos = hpf.findViewById(R.id.rb_todos);
        rb_aprobados = hpf.findViewById(R.id.rb_aprobados);
        rb_denegados = hpf.findViewById(R.id.rb_denegados);


        permisos = new ArrayList<Permiso>();
        //listarTodos();


        rb_todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos = new ArrayList<Permiso>();
                //listarTodos();
                Adapter adaptadorPermisos = new AdaptadorPermiso(getContext(),permisos);
                lv_solicitudes= hpf.findViewById(R.id.lv_permisos);
                lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
            }
        });
        rb_aprobados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos = new ArrayList<Permiso>();
                //listarTodos();
                Adapter adaptadorPermisos = new AdaptadorPermiso(getContext(),permisos);
                lv_solicitudes= hpf.findViewById(R.id.lv_permisos);
                lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
            }
        });
        rb_denegados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos = new ArrayList<Permiso>();
                //listarTodos();
                Adapter adaptadorPermisos = new AdaptadorPermiso(getContext(),permisos);
                lv_solicitudes= hpf.findViewById(R.id.lv_permisos);
                lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
            }
        });

        return hpf;
    }


}
