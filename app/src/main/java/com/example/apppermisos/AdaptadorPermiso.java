package com.example.apppermisos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorPermiso extends ArrayAdapter {


    private Context contexto;
    private ArrayList<Solicitud> solicitudes;
    private ImageView btn_status;

    public AdaptadorPermiso(Context context, ArrayList<Solicitud> solicitudes) {
        super(context, R.layout.item_permisos, solicitudes);
        this.contexto = context;
        this.solicitudes = solicitudes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(contexto);
        View item = inflater.inflate(R.layout.item_permisos, null);
        btn_status = item.findViewById(R.id.btn_status);

        TextView tipo_per= item.findViewById(R.id.tv_tipo_per);
        tipo_per.setText(solicitudes.get(position).getPermiso().getPermiso_per());
        TextView fecha_solicitud = item.findViewById(R.id.tv_fecha_sol);
        fecha_solicitud.setText((CharSequence) solicitudes.get(position).getF_solicitud());
        TextView persona_autorizo = item.findViewById(R.id.tv_autorizo_per);
        persona_autorizo.setText(solicitudes.get(position).getPersona_autoriza());

        if(solicitudes.get(position).getEstatus_sol() == 0){
            btn_status.setImageResource(R.drawable.pendiente);
        }else if(solicitudes.get(position).getEstatus_sol() == 1){
            btn_status.setImageResource(R.drawable.abrobado);
        }else{
            btn_status.setImageResource(R.drawable.denegado);
        }


        return item;
    }

}
