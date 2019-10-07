package com.example.apppermisos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.fragments.PermisosFrag;
import com.example.apppermisos.fragments.PermisosPendientesFrag;
import com.example.apppermisos.fragments.Solicitar_permiso_Fragment;
import com.example.apppermisos.fragments.TodosPermisosFragment;
import com.example.apppermisos.fragments.cambiarPasswordFrag;
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,PermisosPendientesFrag.OnFragmentInteractionListener,cambiarPasswordFrag.OnFragmentInteractionListener,PermisosFrag.OnFragmentInteractionListener{
    private Persona per;
    private ArrayList<Permiso>permisos;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        permisos = new ArrayList<>();
        obtenerPermisos("http://puntosingular.mx/app_permisos/PermisosDelDia.php");





    }

    @Override
    public void onBackPressed() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        TextView tvNombre = findViewById(R.id.nom_Directivo);
        per= getIntent().getParcelableExtra("Persona1");
        ImageView im = findViewById(R.id.img_Dir);
        tvNombre.setText(per.getNombre()+" "+per.getApellidoPaterno()+" "+per.getApellidoMaterno());

        if(per.getSexo().equals("F")){
            im.setImageResource(R.drawable.user_mujer);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        boolean fragmentTransaction = false;
        Fragment fragment = null;
        int id = item.getItemId();

        //si solicita permisos
        if(id == R.id.nav_reg_per){
            fragment = new Solicitar_permiso_Fragment();
            fragmentTransaction = true;
        }else if (id == R.id.nav_permain) { //ver mis permisos
            fragment = new PermisosFrag();
            fragmentTransaction = true;
        } else if (id == R.id.nav_contraseñaadm) {//cambiar contraseña
            fragment = new cambiarPasswordFrag();
            fragmentTransaction = true;
        }else if(id== R.id.nav_cerrarSesionadm){ //cerrar Sesion
            Intent i = new Intent(this,Login_Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }else if(id == R.id.nav_per2main){ //Permisos pendientes de autorizacion
            fragment = new PermisosPendientesFrag();
            fragmentTransaction = true;

        }else if(id == R.id.nav_allPermisos){ //Ver todos los permisos del personal
            fragment = new TodosPermisosFragment();
            fragmentTransaction = true;
        }else if(id == R.id.nav_reportes){
            //Solicitar Permisos de lectura y escritura
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                        1000);
            }


            GenerarReporte gr = new GenerarReporte(MainActivity.this);


            if(!per.getPermisos().isEmpty()){
                gr.obtenerDatos(per);
                //Toast.makeText(MainActivity.this,"Si hay permisos para hoy"+per.getPermisos(),Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this,"No hay permisos registrados para el dia de hoy",Toast.LENGTH_LONG).show();
            }

        }



        if(fragmentTransaction){
            Bundle bundle= new Bundle();
            bundle.putParcelable("Persona",per);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal,fragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public  void obtenerPermisos(String url){
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
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
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
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }


}