package com.example.apppermisos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.apppermisos.fragments.PermisosFrag;
import com.example.apppermisos.fragments.Solicitar_permiso_Fragment;
import com.example.apppermisos.fragments.cambiarPasswordFrag;
import com.example.apppermisos.objetos.Persona;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.Menu;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

public class DocentesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,PermisosFrag.OnFragmentInteractionListener,Solicitar_permiso_Fragment.OnFragmentInteractionListener,DatePickerDialog.OnDateSetListener,cambiarPasswordFrag.OnFragmentInteractionListener{
    private Persona per;
    private TextView tvNombre;
    private ImageView im;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docentes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.docentes, menu);
        tvNombre= findViewById(R.id.tvNombreDocente);
        per= getIntent().getParcelableExtra("Persona1");
        im= findViewById(R.id.imgDocente);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        boolean select=false;
        if (id == R.id.nav_Solicitar) {
            fragment= new Solicitar_permiso_Fragment();
            select=true;
        } else if (id == R.id.nav_mispermisos) {
            fragment= new PermisosFrag();
            select=true;
        }else if(id == R.id.nav_cerrarSesion){
            Intent i = new Intent(this,Login_Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getFragmentManager().popBackStack();
            startActivity(i);
            finish();
        }else if(id == R.id.nav_contraseña){
            fragment= new cambiarPasswordFrag();
            select=true;
        }
        if(select){
            Bundle bundle= new Bundle();
            bundle.putParcelable("Persona",per);
            fragment.setArguments(bundle);
            //eliminar fragments anteriores
            getSupportFragmentManager().beginTransaction().replace(R.id.content_Docentes,fragment).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
