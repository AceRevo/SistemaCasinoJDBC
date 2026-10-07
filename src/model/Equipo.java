package model;

public class Equipo {
    private long id;
    private String nombre;
    private String ciudad;
    private int trofeos;

    public Equipo(long id, String nombre, String ciudad, int trofeos) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.trofeos = trofeos;
    }

    public Equipo(String nombre, String ciudad, int trofeos) {
        this(0, nombre, ciudad, trofeos);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getTrofeos() {
        return trofeos;
    }

    public void setTrofeos(int trofeos) {
        this.trofeos = trofeos;
    }

    @Override
    public String toString() {
        return "Equipo{id=" + id + ", nombre='" + nombre + "', ciudad='" + ciudad
                + "', trofeos=" + trofeos + '}';
    }
}
