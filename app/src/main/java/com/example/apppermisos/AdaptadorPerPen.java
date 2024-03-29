package com.example.apppermisos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;

import java.util.ArrayList;

public class AdaptadorPerPen extends ArrayAdapter {
    private Context contexto;
    private ArrayList<Permiso> permisos;
    private Persona per;
    private View item;

    public AdaptadorPerPen(Context context, ArrayList<Permiso> permisos) {
        super(context, R.layout.item_permisos_pendientes, permisos);
        this.contexto = context;
        this.permisos = permisos;
        this.per=per;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(contexto);
        item = inflater.inflate(R.layout.item_permisos_pendientes, null);
        ImageView iv = item.findViewById(R.id.btn_status);
        TextView usuario_solicito= item.findViewById(R.id.tv_usuario_sol);
        //usuario_solicito.setText(permisos.get(position).);
        TextView tipo_per = item.findViewById(R.id.tv_tipo_per);
        tipo_per.setText(permisos.get(position).getTipoPermiso());
        usuario_solicito.setText(permisos.get(position).getPersonaSolicita());
        return item;
    }

}
