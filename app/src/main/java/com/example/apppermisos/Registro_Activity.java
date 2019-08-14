package com.example.apppermisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button registrar;
    private EditText curp;
    private TextView nom;
    private TextView ap;
    private TextView am;
    private RequestQueue requestQueue;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_);
        registrar= findViewById(R.id.btn_registrarse);
        curp=findViewById(R.id.textCurp);
        nom=findViewById(R.id.textNombre);
        ap=findViewById(R.id.textPaterno);
        am=findViewById(R.id.textMaterno);
        btnBuscar=findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Registro_Activity.this,Login_Activity.class);
                startActivity(i);
            }
        });
    }
    public void ejecutarServicio(String url){
        StringRequest stringRequest= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Operacion exitosa", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
              Map<String,String>parametros=new HashMap<>();
              parametros.put("curp",curp.getText().toString());
              return parametros;

            }

        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void buscar(){
        String curp1=curp.getText().toString();
        String url="http://puntosingular.mx/app_permisos/ConsultarCurp.php?curp="+curp1;
        if(!curp1.isEmpty()){
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registro_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("hola");
                }
            });
            requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);

        }
        else{

        }
    }
}
