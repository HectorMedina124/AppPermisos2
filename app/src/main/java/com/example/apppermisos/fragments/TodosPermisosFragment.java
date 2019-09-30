package com.example.apppermisos.fragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import com.example.apppermisos.objetos.ObjetoAux;
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodosPermisosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodosPermisosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RequestQueue requestQueue;
    private HistorialPermisosGeneralFrag.OnFragmentInteractionListener mListener;
    private RadioButton rb_todos;
    private RadioButton rb_aprobados;
    private RadioButton rb_denegados;
    private RadioButton rb_pendientes;
    private ListView lv_solicitudes;
    private ArrayList<Permiso> permisos,aceptados,denegados;
    private View hg;
    private Dialog customDialog = null;
    private TextView titulo;
    private JsonArrayRequest jsonArrayRequest;
    private ArrayList<Persona> personas;
    private String per_au;


    public TodosPermisosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodosPermisosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodosPermisosFragment newInstance(String param1, String param2) {
        TodosPermisosFragment fragment = new TodosPermisosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permisos=new ArrayList<>();
        llenarPersonas("http://puntosingular.mx/app_permisos/Personas.php");
        llenarPermisos("http://puntosingular.mx/app_permisos/ConsultarPermisosHistorialGeneral.php");



        //Handler hn=new Handler();
       /* hn.postDelayed(new Runnable() {
            @Override
            public void run() {
                llenarNombres("http://puntosingular.mx/app_permisos/ConsultarPermisosHistorialGeneral.php");
            }
        },2000);*/

       //asociarRFC();


        /*if (getArguments() != null) {
            per=getArguments().getParcelable("Persona");
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hg = inflater.inflate(R.layout.fragment_todos_permisos, container, false);

        rb_aprobados = hg.findViewById(R.id.rb_aprobados);
        rb_denegados = hg.findViewById(R.id.rb_denegados);
        rb_pendientes = hg.findViewById(R.id.rb_pendientes);

        titulo= hg.findViewById(R.id.tv_permiso);
        titulo.setVisibility(View.INVISIBLE);


        /*rb_todos.setOnClickListener(new View.OnClickListener() {
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
        });*/

        rb_aprobados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permisos!=null){
                    //Toast.makeText(getActivity(),"Entro",Toast.LENGTH_SHORT).show();
                    Adapter adaptadorPermisos = new AdaptadorPermiso(getContext(), llenarAprobados(permisos));
                    lv_solicitudes = hg.findViewById(R.id.lv_permisos);
                    lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
                    lv_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Toast.makeText(getContext(),llenarAprobados(permisos).get(i).toString(),Toast.LENGTH_SHORT).show();
                            mostrar(hg, llenarAprobados(permisos).get(i));
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
                    lv_solicitudes = hg.findViewById(R.id.lv_permisos);
                    lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
                    lv_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Toast.makeText(getContext(),llenarDenegados(permisos).get(i).toString(),Toast.LENGTH_SHORT).show();
                            mostrar(hg, llenarDenegados(permisos).get(i));
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
                    lv_solicitudes = hg.findViewById(R.id.lv_permisos);
                    lv_solicitudes.setAdapter((ListAdapter) adaptadorPermisos);
                    lv_solicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Toast.makeText(getContext(), llenarPendientes(permisos).get(i).toString(), Toast.LENGTH_SHORT).show();
                            mostrar(hg, llenarPendientes(permisos).get(i));
                        }
                    });
                }else {
                    titulo.setVisibility(View.VISIBLE);
                }
            }
        });

        return  hg;
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

    public void llenarPermisos(String url){
        jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                permisos=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Permiso permiso=new Permiso();
                        permiso.setNumSol(jsonObject.getInt("num_sol"));
                        permiso.setRfcSol(jsonObject.getString("rfc_usu"));
                        String fechaI=jsonObject.getString("f_inicio_sol");
                        permiso.setFechaInicio(fechaI);
                        String fechaF=jsonObject.getString("f_fin_sol");
                        permiso.setFechaFin(fechaF);
                        permiso.setHoraI(jsonObject.getString("h_inicio_sol"));
                        permiso.setHoraFin(jsonObject.getString("h_fin_sol"));
                        String fechaSol=jsonObject.getString("f_solicitud");
                        permiso.setFechaSolicitud(fechaSol);
                        String fechaAprob=jsonObject.getString("f_autorizacion");
                        permiso.setFechaAutorizacion(fechaAprob);
                        String cve_au = jsonObject.getString("persona_autoriza");
                        permiso.setPersonaAutoriza(extraerNombre(cve_au));
                        permiso.setTipoPermiso(jsonObject.getString("permiso_per"));
                        permiso.setStatus(jsonObject.getString("estatus_sol"));
                        permiso.setDesc(jsonObject.getString("Descripcion"));
                        permiso.setPersonaSolicita(jsonObject.getString("nom_per")+" "+jsonObject.getString("ap_per")+" "+jsonObject.getString("am_per"));
                        permisos.add(permiso);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                //per.setPermisos(permisos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);


    }

    public void llenarPersonas(String url){
        jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                personas=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Persona persona = new Persona();
                        persona.setClave(jsonObject.getString("cve_per"));
                        persona.setNombre(jsonObject.getString("nom_per")+" "+jsonObject.getString("ap_per")+" "+jsonObject.getString("am_per"));
                        personas.add(persona);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                //per.setPermisos(permisos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);


    }



/*
    public  void llenarNombres(String url){
        objetoAuxes=new ArrayList<>();
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        ObjetoAux obj= new ObjetoAux();
                        obj.setNombre(jsonObject.getString("persona"));
                        obj.setRfc(jsonObject.getString("rfc_per"));
                        objetoAuxes.add(obj);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                //per.setPermisos(permisos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void asociarRFC(){
        for(int i=0;i<objetoAuxes.size();i++){
            for(int j=0;j<permisos.size();i++){
                if(objetoAuxes.get(i).getRfc().equals(permisos.get(j).getRfcSol())){
                    permisos.get(j).setPersonaSolicita(objetoAuxes.get(i).getNombre());
                }
            }

        }

    }
*/

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


        TextView nombre = customDialog.findViewById(R.id.txtnombreuser);
        nombre.setText(permiso.getPersonaSolicita());
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


    public String extraerNombre(String cve){

        if(personas != null){
            for (int i=0;i<personas.size();i++){
                if(personas.get(i).getClave().equals(cve)){
                    per_au = personas.get(i).getNombre();
                }
            }
        }else{
            Toast.makeText(getContext(),"No hay personas registradas", Toast.LENGTH_SHORT).show();
        }
        return  per_au;
    }

}
