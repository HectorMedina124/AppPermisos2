package com.example.apppermisos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorPerPen extends ArrayAdapter {
    private Context contexto;
    private ArrayList<Solicitud> solicitudes;

    public AdaptadorPerPen(Context context, ArrayList<Solicitud> solicitudes) {
        super(context, R.layout.item_permisos_pendientes, solicitudes);
        this.contexto = context;
        this.solicitudes = solicitudes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(contexto);
        View item = inflater.inflate(R.layout.item_permisos_pendientes, null);


        TextView usuario_solicito= item.findViewById(R.id.tv_usuario_sol);
        usuario_solicito.setText(solicitudes.get(position).getCurp_usu());
        TextView tipo_per = item.findViewById(R.id.tv_tipo_per);
        tipo_per.setText(solicitudes.get(position).getPermiso().getPermiso_per());


        return item;
    }

}
