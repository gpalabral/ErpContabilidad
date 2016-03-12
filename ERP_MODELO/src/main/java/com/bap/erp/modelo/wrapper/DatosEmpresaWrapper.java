package com.bap.erp.modelo.wrapper;

public class DatosEmpresaWrapper {

    private String razonSocial;
    private String subtitulo;
    private String nit;
    private String gestion;
    private String autorizacion;
    private String direccion;

    public DatosEmpresaWrapper() {
        this.razonSocial = "";
        this.subtitulo = "";
        this.nit = "";
        this.gestion = "";
        this.autorizacion = "";
        this.direccion = "";
    }

    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
}
