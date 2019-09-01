package com.example.apppermisos.objetos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Persona implements Parcelable {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String rol;
    private String rfc;
    private String clave;
    private String sexo;
    private ArrayList<Permiso>Permisos;

    protected Persona(Parcel in) {
        nombre = in.readString();
        apellidoPaterno = in.readString();
        apellidoMaterno = in.readString();
        rol = in.readString();
        rfc = in.readString();
        clave = in.readString();
        sexo = in.readString();
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };

    public void setPermisos(ArrayList<Permiso> permisos) {
        Permisos = permisos;
    }


public Persona(){
}
    public void AgregarPermiso(Permiso permiso){
          this.Permisos.add(permiso);
    }

    public ArrayList<Permiso> getPermisos() {
        return this.Permisos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(apellidoPaterno);
        parcel.writeString(apellidoMaterno);
        parcel.writeString(rol);
        parcel.writeString(rfc);
        parcel.writeString(clave);
        parcel.writeString(sexo);
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}

