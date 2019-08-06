package com.example.apppermisos;


import java.sql.Date;
import java.sql.Time;

public class Solicitud {

    private int num_sol;
    private String curp_usu;
    private Date f_inicio_sol;
    private Date f_fin_sol;
    private Time h_inicio_sol;
    private Time h_fin_sol;
    private Date f_solicitud;
    private Date f_autorizacion;
    private String Persona_autoriza;
    private int cve_per;
    private int estatus_sol;
    private Permiso permiso;


    public Solicitud() {
    }

    public Solicitud(int num_sol, String curp_usu, Date f_inicio_sol, Date f_fin_sol, Time h_inicio_sol, Time h_fin_sol, Date f_solicitud, Date f_autorizacion, String persona_autoriza, int cve_per, int estatus_sol, Permiso permiso) {
        this.num_sol = num_sol;
        this.curp_usu = curp_usu;
        this.f_inicio_sol = f_inicio_sol;
        this.f_fin_sol = f_fin_sol;
        this.h_inicio_sol = h_inicio_sol;
        this.h_fin_sol = h_fin_sol;
        this.f_solicitud = f_solicitud;
        this.f_autorizacion = f_autorizacion;
        Persona_autoriza = persona_autoriza;
        this.cve_per = cve_per;
        this.estatus_sol = estatus_sol;
        this.permiso = permiso;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }


    public int getNum_sol() {
        return num_sol;
    }

    public void setNum_sol(int num_sol) {
        this.num_sol = num_sol;
    }

    public String getCurp_usu() {
        return curp_usu;
    }

    public void setCurp_usu(String curp_usu) {
        this.curp_usu = curp_usu;
    }

    public Date getF_inicio_sol() {
        return f_inicio_sol;
    }

    public void setF_inicio_sol(Date f_inicio_sol) {
        this.f_inicio_sol = f_inicio_sol;
    }

    public Date getF_fin_sol() {
        return f_fin_sol;
    }

    public void setF_fin_sol(Date f_fin_sol) {
        this.f_fin_sol = f_fin_sol;
    }

    public Time getH_inicio_sol() {
        return h_inicio_sol;
    }

    public void setH_inicio_sol(Time h_inicio_sol) {
        this.h_inicio_sol = h_inicio_sol;
    }

    public Time getH_fin_sol() {
        return h_fin_sol;
    }

    public void setH_fin_sol(Time h_fin_sol) {
        this.h_fin_sol = h_fin_sol;
    }

    public Date getF_solicitud() {
        return f_solicitud;
    }

    public void setF_solicitud(Date f_solicitud) {
        this.f_solicitud = f_solicitud;
    }

    public Date getF_autorizacion() {
        return f_autorizacion;
    }

    public void setF_autorizacion(Date f_autorizacion) {
        this.f_autorizacion = f_autorizacion;
    }

    public String getPersona_autoriza() {
        return Persona_autoriza;
    }

    public void setPersona_autoriza(String persona_autoriza) {
        Persona_autoriza = persona_autoriza;
    }

    public int getCve_per() {
        return cve_per;
    }

    public void setCve_per(int cve_per) {
        this.cve_per = cve_per;
    }

    public int getEstatus_sol() {
        return estatus_sol;
    }

    public void setEstatus_sol(int estatus_sol) {
        this.estatus_sol = estatus_sol;
    }
}
