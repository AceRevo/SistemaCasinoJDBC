package model;

public class RankingJugador {
    private final long jugadorId;
    private final String nombreJugador;
    private final TipoEstadistica estadistica;
    private final int valor;
    private final long posicion;

    public RankingJugador(long jugadorId, String nombreJugador, TipoEstadistica estadistica,
                          int valor, long posicion) {
        this.jugadorId = jugadorId;
        this.nombreJugador = nombreJugador;
        this.estadistica = estadistica;
        this.valor = valor;
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return "RankingJugador{jugador='" + nombreJugador + "', estadistica=" + estadistica
                + ", valor=" + valor + ", posicion=" + posicion + '}';
    }
}
