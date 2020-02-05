package app.model;

/**
 * Response
 */
public class Response {
    private int duracion;
    private String observaciones;
    private Request consulta;
    private String categoria;
    private String personal_nombre;

    public Response() {

    }

    public String getPersonal_nombre() {
        return personal_nombre;
    }

    public void setPersonal_nombre(String personal_nombre) {
        this.personal_nombre = personal_nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Request getConsulta() {
        return consulta;
    }

    public void setConsulta(Request consulta) {
        this.consulta = consulta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Response(int duracion, String observaciones, Request consulta, String personal_nombre) {
        this.setDuracion(duracion);
        this.setObservaciones(observaciones);
        this.setConsulta(consulta);
        this.setPersonal_nombre(personal_nombre);
    }

    @Override
    public String toString() {
        return "Response [categoria=" + categoria + ", consulta=" + consulta + ", duracion=" + duracion
                + ", observaciones=" + observaciones + ", personal_nombre=" + personal_nombre + "]";
    }
    
}