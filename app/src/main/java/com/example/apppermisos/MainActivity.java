package com.example.apppermisos;

import android.net.Uri;
import android.os.Bundle;

import com.example.apppermisos.fragments.PermisosPendientesFrag;
import com.example.apppermisos.fragments.PermisosPendietesFragment;
//import com.example.apppermisos.fragments.RevisionPermisosFragment;
import com.example.apppermisos.objetos.Persona;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,PermisosPendientesFrag.OnFragmentInteractionListener {


    private FragmentManager fm;
    private Fragment fr;
    private Persona per;
    private TextView tvNombre;
    private TextView cargo;
    private ImageView im;
    private TextView puesto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();
        fr = fm.findFragmentById(R.id.layout_principal);

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
        getMenuInflater().inflate(R.menu.main, menu);
        tvNombre= findViewById(R.id.nom_Dir);
        per= getIntent().getParcelableExtra("Persona1");
        im= findViewById(R.id.img_Directivo);
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
        if (id == R.id.nav_permain) {

        } else if (id == R.id.nav_per2main) {
            fragment = new PermisosPendientesFrag();
            fragmentTransaction = true;
        }
        if(fragmentTransaction){
            Bundle bundle= new Bundle();
            bundle.putParcelable("Persona",per);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}