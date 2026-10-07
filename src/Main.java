import config.DatabaseConnection;
import dao.EquipoDAO;
import dao.JugadorDAO;
import model.Equipo;
import model.Jugador;
import model.RankingJugador;
import model.TipoEstadistica;
import service.EquipoService;
import service.JugadorService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        EquipoService equipoService = new EquipoService(new EquipoDAO());
        JugadorService jugadorService = new JugadorService(new JugadorDAO());

        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Conexión establecida a la base de datos: " + connection.getCatalog());
        }

        Equipo equipo = new Equipo("Tiburones FC", "Valencia", 4);
        equipoService.guardar(equipo);
        System.out.println("Equipo insertado: " + equipo);

        Jugador delantero = new Jugador("Alex Delantero", "Delantero", 18, 7, 3, equipo.getId());
        Jugador mediocampista = new Jugador("Sam Mediocampista", "Mediocampista", 9, 15, 2, equipo.getId());
        Jugador defensa = new Jugador("Chris Defensa", "Defensa", 5, 3, 1, equipo.getId());
        jugadorService.guardar(delantero);
        jugadorService.guardar(mediocampista);
        jugadorService.guardar(defensa);

        System.out.println("\nJugadores del equipo (relación 1:N):");
        jugadorService.listarPorEquipo(equipo.getId()).forEach(System.out::println);

        System.out.println("\nEquipo del jugador (relación N:1):");
        System.out.println(jugadorService.buscarEquipo(delantero.getId()).orElseThrow());

        System.out.println("\nTop 3 goleadores del equipo:");
        jugadorService.top3Goleadores(equipo.getId()).forEach(System.out::println);

        System.out.println("\nHistorial de logros del jugador:");
        System.out.println(jugadorService.obtenerHistorial(delantero.getId()).orElseThrow());

        RankingJugador ranking = jugadorService.calcularRanking(delantero.getId(), TipoEstadistica.GOLES)
                .orElseThrow();
        System.out.println("\nRanking general por goles:");
        System.out.println(ranking);
    }
}
