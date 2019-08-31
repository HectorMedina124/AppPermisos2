package com.example.apppermisos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.apppermisos.objetos.Permiso;

import java.util.ArrayList;

public class AdaptadorRevisionPermisos extends ArrayAdapter {
    private Context contexto;
    private ArrayList<Permiso> permisos;

    public AdaptadorRevisionPermisos(Context context, ArrayList<Permiso> permisos) {
        super(context, R.layout.item_revision_permisos,permisos);
        this.contexto = context;
        this.permisos = permisos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(contexto);
        View item = inflater.inflate(R.layout.item_revision_permisos, null);


        TextView usuario_solicito= item.findViewById(R.id.tv_nom_doc);
        //usuario_solicito.setText(solicitudes.get(position).getCurp_usu());
        TextView hfi = item.findViewById(R.id.tv_hfi);
        //hfi.setText((CharSequence) solicitudes.get(position).getH_fin_sol());
        TextView hff = item.findViewById(R.id.tv_hff);
        //hff.setText((CharSequence) solicitudes.get(position).getH_fin_sol());
        return item;
    }
}
