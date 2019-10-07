package com.example.apppermisos.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.AdaptadorPermiso;
import com.example.apppermisos.R;
import com.example.apppermisos.Registro_Activity;
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PermisosFrag extends Fragment {
    private Persona per;
    private RequestQueue requestQueue;
    private OnFragmentInteractionListener mListener;
    private RadioButton rb_todos;
    private RadioButton rb_aprobados;
    private RadioButton rb_denegados;
    private RadioButton rb_pendientes;
    private ListView lv_solicitudes;
    private ArrayList<Permiso> permisos,aceptados,denegados;
    private View todosPer;
    private Dialog customDialog = null;
    private boolean tienePermisos;
    private TextView titulo;

    public PermisosFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler hn= new Handler();
        if (getArguments() != null) {
            per = getArguments().getParcelable("Persona");
            //Toast.makeText(getActivity(),per.getRfc(), Toast.LENGTH_SHORT).show();
            llenarPermisos("http://puntosingular.mx/app_permisos/ConsultarPermisosHistorial?rfc="+per.getRfc());
        }else{
            Toast.makeText(getActivity(),"Argumentos vacios", Toast.LENGTH_SHORT).show();
        }

        hn.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getActivity(),"TOAST vacio" +per.getPermisos().get(0).getFechaAutorizacion(), Toast.LENGTH_SHORT).show();
            }
        },500);
    }

    private void llenarPermisos(String url) {
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
                            permiso.setPersonaAutoriza(jsonObject.getString("personaAut"));
                            permiso.setStatus(jsonObject.getString("estatus_sol"));
                            permiso.setTipoPermiso(jsonObject.getString("permiso_per"));
                            permisos.add(permiso);
                            //Toast.makeText(getActivity(),permisos.get(i).toString(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            //Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            //e.printStackTrace();
                            titulo= todosPer.findViewById(R.id.tv_permiso);
                            titulo.setVisibility(View.VISIBLE);
                        }
                    }
                    per.setPermisos(permisos);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    titulo= todosPer.findViewById(R.id.tv_permiso);
                    titulo.setVisibility(View.VISIBLE);
                }
            });

        requestQueue= Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonArrayRequest);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        todosPer = inflater.inflate(R.layout.fragment_permisos, container, false);

            rb_todos = todosPer.findViewById(R.id.rb_todos);
            rb_aprobados = todosPer.findViewById(R.id.rb_aprobados);
            rb_denegados = todosPer.findViewById(R.id.rb_denegados);
            rb_pendientes = todosPer.findViewById(R.id.rb_pendientes);

            titulo= todosPer.findViewById(R.id.tv_permiso);
            titulo.setVisibility(View.INVISIBLE);


            rb_todos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(permisos!=null){
                    //Toast.makeText(getActivity(),permisos.get(1).toString(), Toast.LENGTH_SHORT).show();
                        Adapter adaptadorPermisos = new AdaptadorPermiso(getContext(), permisos);
                        lv_solicitudes = todosPer.findViewById(R.id.lv_permisos);
                        lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
                        lv_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //Toast.makeText(getContext(),permisos.get(i).toString(),Toast.LENGTH_SHORT).show();
                                mostrar(todosPer, permisos.get(i));
                            }
                        });
                    }else{
                        titulo.setVisibility(View.VISIBLE);
                    }
                }
            });

            rb_aprobados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(permisos!=null){
                        //Toast.makeText(getActivity(),"Entro",Toast.LENGTH_SHORT).show();
                    Adapter adaptadorPermisos = new AdaptadorPermiso(getContext(), llenarAprobados(permisos));
                    lv_solicitudes = todosPer.findViewById(R.id.lv_permisos);
                    lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
                    lv_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Toast.makeText(getContext(),llenarAprobados(permisos).get(i).toString(),Toast.LENGTH_SHORT).show();
                            mostrar(todosPer, llenarAprobados(permisos).get(i));
                        }
                    });

                }else{
                        titulo.setVisibility(View.VISIBLE);
                    }
                }
            });
            rb_denegados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(permisos!=null){
                    Adapter adaptadorPermisos = new AdaptadorPermiso(getContext(), llenarDenegados(permisos));
                    lv_solicitudes = todosPer.findViewById(R.id.lv_permisos);
                    lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
                    lv_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Toast.makeText(getContext(),llenarDenegados(permisos).get(i).toString(),Toast.LENGTH_SHORT).show();
                            mostrar(todosPer, llenarDenegados(permisos).get(i));
                        }
                    });
                }else{
                        titulo.setVisibility(View.VISIBLE);
                    }
                }
            });
            rb_pendientes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(permisos!=null){
                    Adapter adaptadorPermisos = new AdaptadorPermiso(getContext(), llenarPendientes(permisos));
                    lv_solicitudes = todosPer.findViewById(R.id.lv_permisos);
                    lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
                    lv_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Toast.makeText(getContext(), llenarPendientes(permisos).get(i).toString(), Toast.LENGTH_SHORT).show();
                            mostrar(todosPer, llenarPendientes(permisos).get(i));
                        }
                    });
                }else {
                        titulo.setVisibility(View.VISIBLE);
                    }
                }
            });

            return todosPer;
        }



    public ArrayList<Permiso> llenarPendientes(ArrayList<Permiso> permisos){
        ArrayList<Permiso> pendientes = new ArrayList<>();
        for(int i = 0;i<permisos.size();i++){
            if(permisos.get(i).getStatus().equals("0")){
                pendientes.add(permisos.get(i));
            }
        }
        return pendientes;
    }
    public ArrayList<Permiso> llenarAprobados(ArrayList<Permiso> permisos){
        aceptados = new ArrayList<>();
        for(int i = 0;i<permisos.size();i++){
            if(permisos.get(i).getStatus().equals("1")){
                aceptados.add(permisos.get(i));
            }
        }
        return aceptados;
    }
    public ArrayList<Permiso> llenarDenegados(ArrayList<Permiso> permisos){
        denegados = new ArrayList<>();
        for(int i = 0;i<permisos.size();i++){
            if(permisos.get(i).getStatus().equals("2")){
                denegados.add(permisos.get(i));
            }
        }
        return denegados;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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


    public void mostrar(View view,Permiso permiso){
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(getContext(),R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.cuadro_de_dialogo);

        ImageView imagen = customDialog.findViewById(R.id.im_revisionP);
        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Detalles permiso");
        TextView solicitante = customDialog.findViewById(R.id.txtnombreuser);
        //solicitante = permiso.getSolicitante;
        TextView estatus = customDialog.findViewById(R.id.tv_status);
        if(permiso.getStatus().equals("0")){
            estatus.setText("Pendiente");
        }else if(permiso.getStatus().equals("1")){
            estatus.setText("Aceptado");
        }else if(permiso.getStatus().equals("2")){
            estatus.setText("Denegado");
        }

        String nom = per.getNombre() +" "+ per.getApellidoPaterno() +" "+ per.getApellidoMaterno();

        TextView nombre = customDialog.findViewById(R.id.txtnombreuser);
        nombre.setText(nom);
        TextView fechaSolicitud = customDialog.findViewById(R.id.txtfecha);
        fechaSolicitud.setText(permiso.getFechaSolicitud());
        TextView tipoPermiso = customDialog.findViewById(R.id.tv_tipo_permiso_r);
        tipoPermiso.setText(permiso.getTipoPermiso());
        TextView fechaInicio = customDialog.findViewById(R.id.tv_fecha_inicio);
        fechaInicio.setText(permiso.getFechaInicio());
        TextView fechaFin = customDialog.findViewById(R.id.tv_fecha_fin);
        fechaFin.setText(permiso.getFechaFin());
        TextView horaInicio = customDialog.findViewById(R.id.tv_hora_inicio);
        horaInicio.setText(permiso.getHoraI());
        TextView horaFin = customDialog.findViewById(R.id.tv_hora_fin);
        horaFin.setText(permiso.getHoraFin());
        TextView personaAutorizo = customDialog.findViewById(R.id.tv_persona_autorizo);
        personaAutorizo.setText(permiso.getPersonaAutoriza());
        TextView descripcion = customDialog.findViewById(R.id.tv_descripcion_r);
        descripcion.setText(permiso.getDesc());
        TextView textoAutorizo = customDialog.findViewById(R.id.textAutoriza);
        if (permiso.getStatus().equals("0")){
            textoAutorizo.setText("Se solicito autorizacion a: ");
        }else if(permiso.getStatus().equals("2")){
            textoAutorizo.setText("Denegado por: ");
        }
        ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
                Toast.makeText(getContext(), R.string.aceptar, Toast.LENGTH_SHORT).show();
            }
        });

        customDialog.show();
    }

}
