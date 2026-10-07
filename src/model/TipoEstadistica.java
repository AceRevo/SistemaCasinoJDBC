package model;

public enum TipoEstadistica {
    GOLES("goles"),
    ASISTENCIAS("asistencias");

    private final String columna;

    TipoEstadistica(String columna) {
        this.columna = columna;
    }

    public String getColumna() {
        return columna;
    }
}
