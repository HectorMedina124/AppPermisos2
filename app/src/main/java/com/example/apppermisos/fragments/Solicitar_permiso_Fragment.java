package com.example.apppermisos.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.DocentesActivity;
import com.example.apppermisos.Login_Activity;
import com.example.apppermisos.R;
import com.example.apppermisos.objetos.Persona;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Solicitar_permiso_Fragment extends Fragment {
    private Persona per;
    private Persona per2;
    private boolean correcto;
    private TextView  solicitante;
    private TextView fechaSolicitud;
    private Spinner tipoPermiso;
    private EditText descripcion;
    private TextView fechaini;
    private TextView fechafin;
    private Spinner horaInicio;
    private Spinner minutoInicio;
    private Spinner horaFin;
    private Spinner minutoFin;
    private Spinner destinatario;
    private Button enviar;
    private Button cancelar;
    private RequestQueue requestQueue;
    private ArrayList<Persona> aprobadores;
    private String rfc2;
    private PermisosFrag.OnFragmentInteractionListener mListener;
    private ArrayList<String> nombres;
    private View vista;

    public Solicitar_permiso_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            per = getArguments().getParcelable("Persona");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_solicitar_permiso_, container, false);
        solicitante=vista.findViewById(R.id.txtnombreuser);
        fechaSolicitud=vista.findViewById(R.id.txtfecha);
        tipoPermiso=vista.findViewById(R.id.spinner_tipoper);
        descripcion=vista.findViewById(R.id.txtdescripcion);
        horaInicio=vista.findViewById(R.id.sp_horai);
        horaFin=vista.findViewById(R.id.sp_horaf);
        minutoInicio=vista.findViewById(R.id.sp_mini);
        minutoFin=vista.findViewById(R.id.sp_minf);
        destinatario=vista.findViewById(R.id.spinner_aprobadores);
        enviar=vista.findViewById(R.id.btn_enviar);
        cancelar=vista.findViewById(R.id.btn_cancelar);
        fechaini=vista.findViewById(R.id.spinner_date);
        fechafin=vista.findViewById(R.id.spinner_datef);
        solicitante.setText(per.getNombre()+" "+per.getApellidoPaterno()+" "+per.getApellidoMaterno());
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date date= Calendar.getInstance().getTime();
        String fechaSol = simpleDateFormat.format(date);
        fechaSolicitud.setText(fechaSol);
        traerAprobadores("http://puntosingular.mx/app_permisos/consultardirectivos.php");
        Handler han= new Handler();
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                nombres= new ArrayList<>();
                for(int i=0;i<aprobadores.size();i++){
                    nombres.add(aprobadores.get(i).getNombre()+" "+aprobadores.get(i).getApellidoPaterno()+" "+aprobadores.get(i).getApellidoMaterno());
                }
                ArrayAdapter<String> items= new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,nombres);
                destinatario.setAdapter(items);
            }
        },5000);


        fechaini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c= Calendar.getInstance();
                int diai,mesi,anioi;
                anioi=c.get(Calendar.YEAR);
                mesi=c.get(Calendar.MONTH);
                diai=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                        String fechaelegida="";
                        fechaelegida+=""+anio;
                        if(mes<10) {
                            fechaelegida+="-0"+(mes+1);
                        }else{
                            fechaelegida+="-"+(mes+1);
                        }
                        if(dia<10){
                            fechaelegida+="-0"+dia;
                        }else{
                            fechaelegida+="-"+dia;
                        }
                        fechaini.setText(fechaelegida);
                    }
                },anioi,mesi,diai);
                datePickerDialog.show();
            }
        });
        fechafin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c= Calendar.getInstance();
                int diaf,mesf,aniof;
                aniof=c.get(Calendar.YEAR);
                mesf=c.get(Calendar.MONTH);
                diaf=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                        String fechaelegida="";
                        fechaelegida+=""+anio;
                        if(mes<10) {
                            fechaelegida+="-0"+(mes+1);
                        }else{
                            fechaelegida+="-"+(mes+1);
                        }
                        if(dia<10){
                            fechaelegida+="-0"+dia;
                        }else{
                            fechaelegida+="-"+dia;
                        }
                        fechafin.setText(fechaelegida);
                    }
                },aniof,mesf,diaf);
                datePickerDialog.show();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().onBackPressed();
                Toast.makeText(getContext(),"Se ha cancelado la solicitud",Toast.LENGTH_LONG).show();
                Intent activity = new Intent(getContext(), DocentesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("Persona",per);
                activity.putExtra("Persona1",per);
                startActivity(activity);
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int aprobador=Integer.parseInt(destinatario.getSelectedItemId()+"");
                rfc2=aprobadores.get(aprobador).getClave();
                hacerSolicitud("http://puntosingular.mx/app_permisos/hacersolicitud?rfcsolicitante="+per.getRfc()+"&fechaini="+fechaini.getText().toString()+"&fechafin="+fechafin.getText().toString()+"&horaI="+horaInicio.getSelectedItem().toString().substring(0,2)+":"+minutoInicio.getSelectedItem().toString()+":00"+"&horaF="+horaFin.getSelectedItem().toString().substring(0,2)+":"+minutoFin.getSelectedItem().toString()+":00"+"&fechasolicitud=curdate()&fechaAprobacion=null&cveAutoriza="+rfc2+"&tipopermiso="+(tipoPermiso.getSelectedItemId()+1)+"&descripcion="+descripcion.getText().toString()+"");
            }
        });
        return vista;
    }


    public void traerAprobadores(String url){
        JsonArrayRequest jsonArrayRequest2= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                aprobadores= new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        per2=new Persona();
                        per2.setClave(jsonObject.getString("cve_per"));
                        per2.setNombre(jsonObject.getString("nom_per"));
                        per2.setApellidoPaterno(jsonObject.getString("ap_per"));
                        per2.setApellidoMaterno(jsonObject.getString("am_per"));
                        per2.setRfc(jsonObject.getString("rfc_per"));
                        aprobadores.add(per2);
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest2);

    }


    public void hacerSolicitud(String url){
        StringRequest stringRequest= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Su solicitud ha sido registrada",Toast.LENGTH_LONG).show();
                Intent activity = new Intent(getContext(), DocentesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("Persona",per);
                activity.putExtra("Persona1",per);
                startActivity(activity);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al registrar Solicitud "+error.toString(), Toast.LENGTH_SHORT).show();
                //Snackbar.make(getView(), "Error al registrar solicitud", Snackbar.LENGTH_SHORT).show();
                correcto=false;
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PermisosFrag.OnFragmentInteractionListener) {
            mListener = (PermisosFrag.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}