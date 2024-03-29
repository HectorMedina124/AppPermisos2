package com.example.apppermisos;

public class Rows {

    private String personaSolicita;
    private String fechaSolicitud;
    private String tipoPermiso;
    private String fechaInicio;
    private String fechaFin;
    private String horaI;
    private String horaFin;
    private String personaAutoriza;
    private String desc;


    public String getPersonaSolicita() {
        return personaSolicita;
    }

    public void setPersonaSolicita(String personaSolicita) {
        this.personaSolicita = personaSolicita;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
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

    public String getPersonaAutoriza() {
        return personaAutoriza;
    }

    public void setPersonaAutoriza(String personaAutoriza) {
        this.personaAutoriza = personaAutoriza;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Rows{" +
                "personaSolicita='" + personaSolicita + '\'' +
                ", fechaSolicitud='" + fechaSolicitud + '\'' +
                ", tipoPermiso='" + tipoPermiso + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", horaI='" + horaI + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", personaAutoriza='" + personaAutoriza + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
