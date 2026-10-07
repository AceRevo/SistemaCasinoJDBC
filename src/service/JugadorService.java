package service;

import dao.JugadorDAO;
import model.Equipo;
import model.HistorialLogros;
import model.Jugador;
import model.RankingJugador;
import model.TipoEstadistica;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JugadorService {
    private final JugadorDAO jugadorDAO;

    public JugadorService(JugadorDAO jugadorDAO) {
        this.jugadorDAO = Objects.requireNonNull(jugadorDAO);
    }

    public void guardar(Jugador jugador) throws SQLException {
        Objects.requireNonNull(jugador, "El jugador es obligatorio.");
        validarJugador(jugador);
        jugadorDAO.insertar(jugador);
    }

    public Optional<Jugador> buscarPorId(long id) throws SQLException {
        validarId(id);
        return jugadorDAO.buscarPorId(id);
    }

    public boolean actualizar(Jugador jugador) throws SQLException {
        Objects.requireNonNull(jugador, "El jugador es obligatorio.");
        validarId(jugador.getId());
        validarJugador(jugador);
        return jugadorDAO.actualizar(jugador);
    }

    public boolean eliminar(long id) throws SQLException {
        validarId(id);
        return jugadorDAO.eliminar(id);
    }

    public List<Jugador> listarPorEquipo(long equipoId) throws SQLException {
        validarId(equipoId);
        return jugadorDAO.listarPorEquipo(equipoId);
    }

    public Optional<Equipo> buscarEquipo(long jugadorId) throws SQLException {
        validarId(jugadorId);
        return jugadorDAO.buscarEquipoPorJugador(jugadorId);
    }

    public List<Jugador> top3Goleadores(long equipoId) throws SQLException {
        validarId(equipoId);
        return jugadorDAO.topGoleadores(equipoId);
    }

    public Optional<HistorialLogros> obtenerHistorial(long jugadorId) throws SQLException {
        validarId(jugadorId);
        return jugadorDAO.obtenerHistorial(jugadorId);
    }

    public Optional<RankingJugador> calcularRanking(long jugadorId, TipoEstadistica estadistica)
            throws SQLException {
        validarId(jugadorId);
        Objects.requireNonNull(estadistica, "La estadística del ranking es obligatoria.");
        return jugadorDAO.calcularRanking(jugadorId, estadistica);
    }

    private void validarJugador(Jugador jugador) {
        if (jugador.getNombre() == null || jugador.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del jugador es obligatorio.");
        }
        if (jugador.getPosicion() == null || jugador.getPosicion().isBlank()) {
            throw new IllegalArgumentException("La posición del jugador es obligatoria.");
        }
        if (jugador.getEquipoId() <= 0) {
            throw new IllegalArgumentException("El ID del equipo debe ser positivo.");
        }
        if (jugador.getGoles() < 0 || jugador.getAsistencias() < 0 || jugador.getMvps() < 0) {
            throw new IllegalArgumentException("Las estadísticas del jugador no pueden ser negativas.");
        }
    }

    private void validarId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo.");
        }
    }
}
