package com.example.apppermisos.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.DatePickerFragment;
import com.example.apppermisos.R;
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Solicitar_permiso_Fragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private Persona per;
    private Persona per2;
    private boolean correcto=false;
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
    private String rfc2;
    private PermisosFrag.OnFragmentInteractionListener mListener;

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
        View vista=inflater.inflate(R.layout.fragment_solicitar_permiso_, container, false);
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
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy");

        fechaini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment= new DatePickerFragment();
             dialogFragment.show(getActivity().getSupportFragmentManager(),"Elegir fecha");

            }
        });
        fechafin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment= new DatePickerFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(),"Elegir fecha");
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        solicitante.setText(per.getNombre()+" "+per.getApellidoPaterno()+" "+per.getApellidoMaterno());
        Date date= Calendar.getInstance().getTime();
        String fechas = simpleDateFormat.format(date);
        fechaSolicitud.setText(fechas);
        //rfc2=traerRfcAprobador();
        //hacerSolicitud("http://puntosingular.mx/app_permisos/hacersolicitud?rfcsolicitante="+per.getRfc().toString()+"&fechaini="+fechainicio+"&fechafin="+fechafinal+"&horaI="+horainicio+"&horaF="+horafinal+"&fechasolicitud=curdate()&fechaAprobacion=null&cveAutoriza="+rfc2+"&tipopermiso="+(tipoPermiso.getSelectedItemId()+1)+"&descripcion="+descripcion.getText().toString()+"");
        return vista;
    }
    public String traerRfcAprobador(String url){
        JsonArrayRequest jsonArrayRequest2= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                per2=new Persona();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        per2.setRfc(jsonObject.getString("rfc_per"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(Login_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest2);
        return per2.getRfc().toString();
    }
    public void hacerSolicitud(String url){
        JsonArrayRequest jsonArrayRequest2= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getContext(),"Su solicitud ah sido registrada",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al registrar Solicitud "+error.toString(), Toast.LENGTH_SHORT).show();
                correcto=false;
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest2);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int anio, int mes, int diadelmes) {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR,anio);
        c.set(Calendar.MONTH,mes);
        c.set(Calendar.DAY_OF_MONTH,diadelmes);
        String fechaactual= DateFormat.getDateInstance().format(c.getTime());
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