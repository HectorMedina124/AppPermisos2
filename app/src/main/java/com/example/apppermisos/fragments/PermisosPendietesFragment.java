package com.example.apppermisos.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.apppermisos.objetos.Permiso;

import java.util.ArrayList;

public class PermisosPendietesFragment extends Fragment {

    private ListView lv_per_pen;
    private ArrayList<Permiso> permisos;

    public PermisosPendietesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return null;
    }


   /* public void getPerPen(){
        Permiso permiso = new Permiso();
        permiso.setNum_sol(1);
        permiso.setPermiso_per("Administrativo");
        solicitudes.add(new Solicitud(1,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,0,permiso));
        solicitudes.add(new Solicitud(4,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,0,permiso));
        solicitudes.add(new Solicitud(7,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,0,permiso));
    }*/

}
