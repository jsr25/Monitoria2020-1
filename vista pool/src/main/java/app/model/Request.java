package app.model;

import java.util.Date;

/**
 * Request
 */
public class Request {
    private int id;
    private String usuario;
    private String descripcion;
    private String equipo;
    private String estado;
    private Date inicio;

    public Request() {

    }

    public Request(String usuario, String descripcion, String equipo, String estado, Date inicio) {
        this.setUsuario(usuario);
        this.setDescripcion(descripcion);
        this.setEquipo(equipo);
        this.setEstado(estado);
        this.setInicio(inicio);
    }

    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "Request { Id: "+this.id+
        ", Usuario: "+this.usuario+
        ", Descripcion: "+this.descripcion+
        ", Equipo: "+this.equipo+
        ", Estado: "+this.estado+
        ", Inicio: "+this.inicio.toString() +"}";
    }

}