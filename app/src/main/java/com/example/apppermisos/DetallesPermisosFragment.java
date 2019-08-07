package com.example.apppermisos;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetallesPermisosFragment extends Fragment {

    private Button btn_aprob;
    private Button btn_den;



    public DetallesPermisosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detallesPermiso =  inflater.inflate(R.layout.fragment_detalles_permisos, container, false);
        btn_aprob = detallesPermiso.findViewById(R.id.btn_aprobado);
        btn_den = detallesPermiso.findViewById(R.id.btn_rechazado);

        btn_aprob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Tu solicitud ha sido aprobada",Toast.LENGTH_LONG).show();

            }
        });
        btn_den.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Tu solicitud no ha sido aprobada",Toast.LENGTH_LONG).show();
            }
        });


        return detallesPermiso;
    }

}
