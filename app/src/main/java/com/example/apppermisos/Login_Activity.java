package com.example.apppermisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.objetos.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_Activity extends AppCompatActivity {
    private Button iniciar;
    private TextView registrar;
    private EditText rfc;
    private EditText pass;
    private RequestQueue requestQueue;
     Persona per;
    private boolean correcto=false;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        rfc=findViewById(R.id.txt_rfcLogin);
        pb=findViewById(R.id.progressBar);
        pass=findViewById(R.id.txt_contraseñaLogin);
        iniciar=findViewById(R.id.btn_iniciarsesion);
        registrar=findViewById(R.id.btn_registrar);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login("http://puntosingular.mx/app_permisos/Login?rfc="+rfc.getText().toString()+"&pass="+pass.getText().toString().toUpperCase());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(correcto){
                            pb.setVisibility(View.GONE);

                            if(per.getRol().equals("Director")|| per.getRol().equals("SubDirector")|| per.getRol().equals("Root")|| per.getRol().equals("Jefe")){
                                Intent activity= new Intent(Login_Activity.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("Persona",per);
                                activity.putExtra("Persona1",per);
                                startActivity(activity);

                            }
                            else if(per.getRol().equals("Docente")|| per.getRol().equals("Administrativo")) {
                                Intent activity = new Intent(Login_Activity.this, DocentesActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("Persona",per);
                                activity.putExtra("Persona1",per);
                                startActivity(activity);

                            }
                            else if(per.getRol().equals("Prefecto")){
                                Intent activity= new Intent(Login_Activity.this,PrefectoInicio.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("Persona",per);
                                activity.putExtra("Persona1",per);
                                startActivity(activity);

                            }
                        }
                        else{
                            pb.setVisibility(View.GONE);

                            Toast.makeText(Login_Activity.this, "El usuario y la contraseña son incorrectos", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, 1000);


            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrar= new Intent(Login_Activity.this,Registro_Activity.class);
                startActivity(registrar);
            }
        });
    }
    public void login(String url){
        pb.setVisibility(View.VISIBLE);
        final String rfc1=rfc.getText().toString();
        String pass1=pass.getText().toString();

            JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    per=new Persona();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                           per.setNombre(jsonObject.getString("nom_per"));
                            per.setApellidoPaterno(jsonObject.getString("ap_per"));
                            per.setApellidoMaterno(jsonObject.getString("am_per"));
                            per.setClave(jsonObject.get("cve_per").toString());
                            per.setRfc(rfc1);
                            per.setRol(jsonObject.getString("rol_rol"));
                            per.setSexo(jsonObject.getString("sexo_per"));
                            correcto=true;
                            Toast.makeText(Login_Activity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(Login_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    correcto=false;


                }
            });
            requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);



    }
}
