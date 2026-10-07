package model;

public class Jugador {
    private long id;
    private String nombre;
    private String posicion;
    private int goles;
    private int asistencias;
    private int mvps;
    private long equipoId;

    public Jugador(long id, String nombre, String posicion, int goles, int asistencias, int mvps, long equipoId) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.goles = goles;
        this.asistencias = asistencias;
        this.mvps = mvps;
        this.equipoId = equipoId;
    }

    public Jugador(String nombre, String posicion, int goles, int asistencias, int mvps, long equipoId) {
        this(0, nombre, posicion, goles, asistencias, mvps, equipoId);
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

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getMvps() {
        return mvps;
    }

    public void setMvps(int mvps) {
        this.mvps = mvps;
    }

    public long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(long equipoId) {
        this.equipoId = equipoId;
    }

    @Override
    public String toString() {
        return "Jugador{id=" + id + ", nombre='" + nombre + "', posicion='" + posicion
                + "', goles=" + goles + ", asistencias=" + asistencias + ", mvps=" + mvps
                + ", equipoId=" + equipoId + '}';
    }
}
