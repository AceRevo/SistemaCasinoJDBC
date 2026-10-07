package model;

public class HistorialLogros {
    private final String jugador;
    private final String equipo;
    private final int trofeosEquipo;
    private final int mvpsJugador;
    private final int asistenciasJugador;

    public HistorialLogros(String jugador, String equipo, int trofeosEquipo,
                           int mvpsJugador, int asistenciasJugador) {
        this.jugador = jugador;
        this.equipo = equipo;
        this.trofeosEquipo = trofeosEquipo;
        this.mvpsJugador = mvpsJugador;
        this.asistenciasJugador = asistenciasJugador;
    }

    @Override
    public String toString() {
        return "Historial de " + jugador + " (" + equipo + "): trofeos del equipo="
                + trofeosEquipo + ", MVPs del jugador=" + mvpsJugador
                + ", asistencias del jugador=" + asistenciasJugador;
    }
}
