package com.example.apppermisos.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.DocentesActivity;
import com.example.apppermisos.Login_Activity;
import com.example.apppermisos.MainActivity;
import com.example.apppermisos.PrefectoInicio;
import com.example.apppermisos.R;
import com.example.apppermisos.objetos.Persona;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link cambiarPasswordFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link cambiarPasswordFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cambiarPasswordFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Persona per;
    EditText etPassO;
    EditText etPassN;

    private OnFragmentInteractionListener mListener;
    private View vista;
    private RequestQueue requestQueue;

    public cambiarPasswordFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cambiarPasswordFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static cambiarPasswordFrag newInstance(String param1, String param2) {
        cambiarPasswordFrag fragment = new cambiarPasswordFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            per = getArguments().getParcelable("Persona");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_cambiar_password, container, false);
        Button btn= vista.findViewById(R.id.btn_cambiarpass);
        etPassO= vista.findViewById(R.id.et_old_pass);
        etPassN= vista.findViewById(R.id.et_new_pass);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(per.getContraseña().equals(etPassO.getText().toString())){
                    cambiarContraseña("http://puntosingular.mx/app_permisos/CambiarContrase%C3%B1a?rfc="+per.getRfc()+"&password="+etPassN.getText().toString());


                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Contraseña actual incorrecta",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return vista;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void cambiarContraseña(String url){
        StringRequest stringRequest= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity().getApplicationContext(),"Cambio exitoso", Toast.LENGTH_SHORT).show();
                if(per.getRol().equals("Docente") || per.getRol().equals("Administrativo")){
                    Intent activity = new Intent(getContext(), DocentesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Persona",per);
                    activity.putExtra("Persona1",per);
                    startActivity(activity);
                }
                else if(per.getRol().equals("Director")|| per.getRol().equals("SubDirector")|| per.getRol().equals("Root")|| per.getRol().equals("Jefe")){
                    Intent activity = new Intent(getContext(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Persona",per);
                    activity.putExtra("Persona1",per);
                    startActivity(activity);

                }  else if(per.getRol().equals("Prefecto")){
                    Intent activity = new Intent(getContext(), PrefectoInicio.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Persona",per);
                    activity.putExtra("Persona1",per);
                    startActivity(activity);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


}
