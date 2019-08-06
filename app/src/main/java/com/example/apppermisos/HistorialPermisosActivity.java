package com.example.apppermisos;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistorialPermisosActivity extends AppCompatActivity {

    private RadioButton rb_todos;
    private RadioButton rb_aprobados;
    private RadioButton rb_denegados;
    private ListView lv_solicitudes;
    private ArrayList<Solicitud> solicitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_permisos);

        rb_todos = findViewById(R.id.rb_todos);
        rb_aprobados = findViewById(R.id.rb_aprobados);
        rb_denegados = findViewById(R.id.rb_denegados);


        solicitudes = new ArrayList<Solicitud>();
        listarTodos();


         rb_todos.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) { solicitudes = new ArrayList<Solicitud>();
                 listarTodos();
                 Adapter adaptadorPermisos = new AdaptadorPermiso(getApplicationContext(),solicitudes);
                 lv_solicitudes= findViewById(R.id.lv_permisos);
                 lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
             }
         });
        rb_aprobados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitudes = new ArrayList<Solicitud>();
                listarAprobados();
                Adapter adaptadorPermisos = new AdaptadorPermiso(getApplicationContext(),solicitudes);
                lv_solicitudes= findViewById(R.id.lv_permisos);
                lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
            }
        });
        rb_denegados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitudes = new ArrayList<Solicitud>();
                listarDenegados();
                Adapter adaptadorPermisos = new AdaptadorPermiso(getApplicationContext(),solicitudes);
                lv_solicitudes= findViewById(R.id.lv_permisos);
                lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
            }
        });


    }

    private void listarTodos() {
        Permiso permiso=new Permiso();
        permiso.setNum_sol(1);
        permiso.setPermiso_per("Administrativo");
        solicitudes.add(new Solicitud(1,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,0,permiso));
        solicitudes.add(new Solicitud(2,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,1,permiso));
        solicitudes.add(new Solicitud(3,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,2,permiso));
        solicitudes.add(new Solicitud(4,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,0,permiso));
        solicitudes.add(new Solicitud(5,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,1,permiso));
        solicitudes.add(new Solicitud(6,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,2,permiso));
        solicitudes.add(new Solicitud(7,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,0,permiso));
    }
    private void listarAprobados() {
        Permiso permiso=new Permiso();
        permiso.setNum_sol(1);
        permiso.setPermiso_per("Administrativo");
        solicitudes.add(new Solicitud(2,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,1,permiso));
        solicitudes.add(new Solicitud(5,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,1,permiso));
    }  private void listarDenegados() {
        Permiso permiso=new Permiso();
        permiso.setNum_sol(1);
        permiso.setPermiso_per("Administrativo");
        solicitudes.add(new Solicitud(3,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,2,permiso));
        solicitudes.add(new Solicitud(6,"GAAS970915MMN01",null,null,null,null,null,null,"Licenciado Juan Romero",1,2,permiso));
    }

}
