package com.example.apppermisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login_Activity extends AppCompatActivity {
    private Button iniciar;
    private TextView registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        iniciar=findViewById(R.id.btn_iniciarsesion);
        registrar=findViewById(R.id.btn_registrar);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iniciarsesion= new Intent(Login_Activity.this,MainActivity.class);
                startActivity(iniciarsesion);
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
}
