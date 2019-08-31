package com.example.apppermisos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apppermisos.AdaptadorRevisionPermisos;
import com.example.apppermisos.R;
import com.example.apppermisos.objetos.Permiso;

import java.util.ArrayList;


public class RevisionPermisosFragment extends Fragment {

    private ListView lv_revision_per;
    private ArrayList<Permiso> permisos;
    private View revisionPermisos;

    public RevisionPermisosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       revisionPermisos =  inflater.inflate(R.layout.fragment_revision_permisos, container, false);

        permisos = new ArrayList<>();

        Adapter adaptadorRevPer = new AdaptadorRevisionPermisos(getContext(),permisos);
        lv_revision_per= revisionPermisos.findViewById(R.id.lv_revision_per);
        lv_revision_per.setAdapter((ListAdapter) adaptadorRevPer);
        lv_revision_per.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getContext(),permisos.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });
        return revisionPermisos;
    }


  /*  public void getPerPen(){
        Permiso permiso = new Permiso();
        permiso.setNum_sol(1);
        permiso.setPermiso_per("Administrativo");
        solicitudes.add(new Solicitud(1,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,0,permiso));
        solicitudes.add(new Solicitud(4,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,0,permiso));
        solicitudes.add(new Solicitud(7,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,0,permiso));
    }*/
}
