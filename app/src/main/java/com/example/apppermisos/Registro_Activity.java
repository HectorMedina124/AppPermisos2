package com.example.apppermisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Registro_Activity extends AppCompatActivity {
    private EditText rfc;
    private TextView nom;
    private TextView ap;
    private TextView am;
    private EditText pass;
    private Button btnRegistrar;
    private RequestQueue requestQueue;
    private ImageButton btnBuscar;
    private String clave;

    public  boolean existe;
    public String k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_);

        rfc=findViewById(R.id.textRfc);
        nom=findViewById(R.id.textNombre);
        ap=findViewById(R.id.textPaterno);
        am=findViewById(R.id.textMaterno);
        pass=findViewById(R.id.txtContraseña);
        btnRegistrar=findViewById(R.id.btn_registrarse);
        btnRegistrar.setEnabled(false);
        btnBuscar=findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!existe){
                    if(pass.getText().toString().isEmpty()){
                        Toast.makeText(Registro_Activity.this, "Favor de introducir una contraseña", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        registrar("http://puntosingular.mx/app_permisos/Registrar?rfc="+
                                rfc.getText().toString().toUpperCase()+"&password="+pass.getText().toString()+"&clavePersona="+clave);
                    }
                }

                else{
                    Toast.makeText(Registro_Activity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void registrar(String url){

        StringRequest stringRequest= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Registro exitoso", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),Login_Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void buscar(){
        String rfc1=rfc.getText().toString();
        if(!rfc1.isEmpty()){
            String url="http://puntosingular.mx/app_permisos/ConsultarRfc.php?rfc="+rfc1;

            JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            nom.setText("Nombre: "+jsonObject.getString("nom_per"));
                            ap.setText("Apellido Paterno: "+jsonObject.getString("ap_per"));
                            am.setText("Apellido Materno: "+jsonObject.getString("am_per"));
                            clave=jsonObject.get("cve_per").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    rfc.setEnabled(false);
                    btnRegistrar.setEnabled(true);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registro_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
            existe("http://puntosingular.mx/app_permisos/ConsultarUsuario?rfc="+rfc.getText().toString());


        }
        else{
            Toast.makeText(Registro_Activity.this,"Rfc Incorrecto", Toast.LENGTH_SHORT).show();


        }
    }
    public void existe(String url){
        StringRequest stringRequest= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response+" "+existe, Toast.LENGTH_SHORT).show();

                if(response.equals("1")){
                    existe=true;
                }
                else{
                    existe=false;

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

