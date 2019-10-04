package com.example.apppermisos;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apppermisos.fragments.PermisosDelDia;
import com.example.apppermisos.objetos.Permiso;
import com.example.apppermisos.objetos.Persona;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GenerarReporte {

    private Context context;
    private Paragraph paragraph;
    private Date date = new Date();
    private String NOMBRE_DIRECTORIO ="Reportes de permisos";
    private String NOMBRE_DOCUMENTO = "Reporte de ";
    private ListView listaP;
    private ArrayList<String[]> rows;
    private ArrayList<String[]> rowSS;
    private PermisosDelDia pdd;
    private String[] header = {"Solicitante","Fecha de solicitud","Tipo de permiso","Fecha Inicio","Fecha fin","Hora inicio","Hora Fin","Autorizó","Descripcion"};



    public GenerarReporte(Context context) {
        this.context = context;
    }

    public void obtenerDatos(Persona persona){
        if (persona != null) {
            //Toast.makeText(context,persona.toString(),Toast.LENGTH_LONG).show();
           if( persona.getPermisos()!= null){ //si recuperamos permisos para hoy
                crearPDF(persona.getPermisos());
                //Toast.makeText(context,"Si hay permisos"+persona.getPermisos(),Toast.LENGTH_LONG).show();
           }else{//si no hay ningun permiso para el dia de hoy
                Toast.makeText(context,"No se tienen permisos registrados para hoy",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(context,"Persona vacia",Toast.LENGTH_LONG).show();
        }
    }


    public void crearPDF(ArrayList<Permiso> permisos){
        //si se crearon los renglones de la tabla
        rows = getRowPermisoS(permisos);
        if(rows != null){
            //Toast.makeText(context,"Si se crearon los renglones de la tabla",Toast.LENGTH_LONG).show();
            Document document = new Document();
            Calendar c = Calendar.getInstance();
            String dia = Integer.toString(c.get(Calendar.DATE));
            String mes = Integer.toString(c.get(Calendar.MONTH)+1);
            String annio = Integer.toString(c.get(Calendar.YEAR));

            switch(mes){
                case "1": mes = "enero"; break;
                case "2": mes = "febrero"; break;
                case "3": mes = "marzo"; break;
                case "4": mes = "abril"; break;
                case "5": mes = "mayo"; break;
                case "6": mes = "junio"; break;
                case "7": mes = "julio"; break;
                case "8": mes = "agosto"; break;
                case "9": mes = "septiembre"; break;
                case "10":mes = "octubre"; break;
                case "11":mes = "noviembre"; break;
                case "12":mes = "diciembre"; break;
            }

            try{
                File file = crearFichero(NOMBRE_DOCUMENTO+dia+"-"+mes+"-"+annio+".pdf");
                FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
                PdfWriter pdfWriter = PdfWriter.getInstance(document,ficheroPDF);

                document.open();
                document.add(new Paragraph("Reportes de permisos\n\n"));
                document.add(new Paragraph("Fecha de reporte: "+dia+" de "+mes+" del "+annio+"\n\n"));

                //Insertamos una tabla
                try {
                    paragraph = new Paragraph();
                    PdfPTable table = new PdfPTable(header.length);
                    table.setWidthPercentage(100);
                    PdfPCell pdfPCell;
                    int indexC = 0;
                    while(indexC <header.length){
                        pdfPCell = new PdfPCell((new Phrase(header[indexC++])));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(pdfPCell);
                    }
                    for(int indexR = 0; indexR < rows.size();indexR++){
                        String [] row = rows.get(indexR);
                       for(indexC = 0; indexC<rows.size();indexC++){
                            pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                            //pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            //pdfPCell.setFixedHeight(40);
                            table.addCell(pdfPCell);
                        }
                       /*for(indexC = 0; indexC<rows.size();indexC++){
                            table.addCell(row[indexC]);
                        }*/
                    }
                    paragraph.add(table);
                    document.add(paragraph);
                }catch (Exception e){
                    Toast.makeText(context,"Error al crear la tabla",Toast.LENGTH_LONG).show();
                }

            } catch (DocumentException e) {

            }catch (IOException e){

            }finally {
                document.close();
            }
        }else{//si no se crearon los renglones de la tabla
            Toast.makeText(context,"No se crearon los renglones de la tabla",Toast.LENGTH_LONG).show();
        }

    }

    public File crearFichero(String nombreFichero){
        File ruta = getRuta();
        File fichero = null;
        if(ruta != null){
            fichero = new File(ruta,nombreFichero);
        }

        return fichero;
    }

    public File getRuta(){
        File ruta = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),NOMBRE_DIRECTORIO);

            if(ruta != null){
                if(ruta.mkdirs()){
                    if(!ruta.exists()){
                        return null;
                    }
                }
            }
        }
        return  ruta;
    }

    public ArrayList<String[]> getRowPermisoS(ArrayList<Permiso> permisos){
        if(permisos != null){
            rowSS = new ArrayList<>();
            for(int i =0;i<permisos.size();i++){
                rowSS.add(new String[]{permisos.get(i).getPersonaSolicita(),
                        permisos.get(i).getFechaSolicitud(),
                        permisos.get(i).getTipoPermiso(),
                        permisos.get(i).getFechaInicio(),
                        permisos.get(i).getFechaFin(),
                        permisos.get(i).getHoraI(),
                        permisos.get(i).getHoraFin(),
                        permisos.get(i).getPersonaAutoriza(),
                        permisos.get(i).getDesc()});
            }
        }else {
            Toast.makeText(context,"No hay permisos para crear los renglones de la tabla",Toast.LENGTH_LONG).show();
        }
        return rowSS;
    }

}
