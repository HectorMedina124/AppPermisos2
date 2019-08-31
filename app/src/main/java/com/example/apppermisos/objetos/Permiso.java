package com.example.apppermisos.objetos;

import java.util.Date;

public class Permiso {
    private String fechaInicio;
    private String fechaFin;
    private String horaI;
    private String horaFin;
    private String fechaSolicitud;
    private String personaAutoriza;
    private String status;
    private String desc;
    private String fechaAutorizacion;
    private String tipoPermiso;

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraI() {
        return horaI;
    }

    public void setHoraI(String horaI) {
        this.horaI = horaI;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getPersonaAutoriza() {
        return personaAutoriza;
    }

    public void setPersonaAutoriza(String personaAutoriza) {
        this.personaAutoriza = personaAutoriza;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Permiso{" +
                "fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", horaI='" + horaI + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", fechaSolicitud='" + fechaSolicitud + '\'' +
                ", personaAutoriza='" + personaAutoriza + '\'' +
                ", status='" + status + '\'' +
                ", desc='" + desc + '\'' +
                ", fechaAutorizacion='" + fechaAutorizacion + '\'' +
                ", tipoPermiso='" + tipoPermiso + '\'' +
                '}';
    }
}
