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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.AdaptadorPerPen;
import com.example.apppermisos.DetallesPermisosFragment;
import com.example.apppermisos.R;
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PermisosDelDia extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Persona per;
    private ArrayList<Permiso>permisos;
    private RequestQueue requestQueue;
    private View permisosD;
    private Dialog customDialog = null;
    private ListView listaP;
    public PermisosDelDia() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler hn= new Handler();
        permisos=new ArrayList<>();
        permisos=new ArrayList<>();
        if (getArguments() != null) {
            per = getArguments().getParcelable("Persona");
            obtenerPermisos("http://puntosingular.mx/app_permisos/PermisosDelDia.php");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        permisosD=inflater.inflate(R.layout.fragment_permisos_del_dia, container, false);
        Handler hn= new Handler();
        hn.postDelayed(new Runnable() {
            @Override
            public void run() {
                Adapter adaptadorPermisos = new AdaptadorPerPen(getContext(), permisos);
                if(!permisos.isEmpty()) {
                    listaP= permisosD.findViewById(R.id.permisos_del_dia);
                    listaP.setAdapter((ListAdapter) adaptadorPermisos);
                    listaP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            //Toast.makeText(getActivity().getApplicationContext(), "Seleccionaste " + permisos.get(i), Toast.LENGTH_SHORT).show();
                            mostrar(permisosD, permisos.get(i));
                        }
                    });
                }
                else {
                    TextView txtVacio=permisosD.findViewById(R.id.tv_vacio3);
                    txtVacio.setVisibility(View.VISIBLE);

                }

            }
        },500);
        return permisosD;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public  void obtenerPermisos(String url){
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
                        permiso.setNumSol(jsonObject.getInt("num_sol"));
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
                        permiso.setPersonaSolicita(jsonObject.getString("personaSol"));
                        permiso.setStatus(jsonObject.getString("estatus_sol"));
                        permiso.setTipoPermiso(jsonObject.getString("permiso_per"));
                        permisos.add(permiso);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                per.setPermisos(permisos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

    public void mostrar(View view,Permiso permiso){
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(getContext(),R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.cuadro_de_dialogo_dia);

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
        TextView descripcion = customDialog.findViewById(R.id.tv_descripcion_r);
        descripcion.setText(permiso.getDesc());
        ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
                //Toast.makeText(getContext(), R.string.aceptar, Toast.LENGTH_SHORT).show();
            }
        });

        customDialog.show();
    }
}
