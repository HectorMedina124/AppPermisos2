package com.example.apppermisos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apppermisos.objetos.Permiso;

import java.util.ArrayList;

public class AdaptadorPermiso extends ArrayAdapter {


    private Context contexto;
    private ArrayList<Permiso> permisos;
    private ImageView btn_status;

    public AdaptadorPermiso(Context context, ArrayList<Permiso> permisos) {
        super(context, R.layout.item_permisos, permisos);
        this.contexto = context;
        this.permisos = permisos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(contexto);
        View item = inflater.inflate(R.layout.item_permisos, null);
        btn_status = item.findViewById(R.id.btn_status);

        TextView tipo_per= item.findViewById(R.id.tv_tipo_per);
        tipo_per.setText(permisos.get(position).getTipoPermiso());
        TextView fecha_solicitud = item.findViewById(R.id.tv_fecha_sol);
        fecha_solicitud.setText(permisos.get(position).getFechaSolicitud());
        TextView persona_autorizo = item.findViewById(R.id.tv_autorizo_per);
        persona_autorizo.setText(permisos.get(position).getPersonaAutoriza());

        if(permisos.get(position).getStatus().equals("0")){
            btn_status.setImageResource(R.drawable.pendiente);
        }else if(permisos.get(position).getStatus().equals("1")){
            btn_status.setImageResource(R.drawable.abrobado);
        }else if(permisos.get(position).getStatus().equals("2")){
            btn_status.setImageResource(R.drawable.denegado);
        }


        return item;
    }

}
