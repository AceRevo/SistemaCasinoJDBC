package dao;

import config.DatabaseConnection;
import model.Equipo;
import model.HistorialLogros;
import model.Jugador;
import model.RankingJugador;
import model.TipoEstadistica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JugadorDAO {
    public void insertar(Jugador jugador) throws SQLException {
        String sql = "INSERT INTO jugador (nombre, posicion, goles, asistencias, mvps, equipo_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            asignarParametros(statement, jugador);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    jugador.setId(keys.getLong(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para el jugador.");
                }
            }
        }
    }

    public Optional<Jugador> buscarPorId(long id) throws SQLException {
        String sql = "SELECT id, nombre, posicion, goles, asistencias, mvps, equipo_id "
                + "FROM jugador WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapear(resultSet)) : Optional.empty();
            }
        }
    }

    public boolean actualizar(Jugador jugador) throws SQLException {
        String sql = "UPDATE jugador SET nombre = ?, posicion = ?, goles = ?, asistencias = ?, "
                + "mvps = ?, equipo_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            asignarParametros(statement, jugador);
            statement.setLong(7, jugador.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM jugador WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public List<Jugador> listarPorEquipo(long equipoId) throws SQLException {
        String sql = "SELECT id, nombre, posicion, goles, asistencias, mvps, equipo_id "
                + "FROM jugador WHERE equipo_id = ? ORDER BY nombre";
        return consultarJugadores(sql, equipoId);
    }

    public Optional<Equipo> buscarEquipoPorJugador(long jugadorId) throws SQLException {
        String sql = "SELECT e.id, e.nombre, e.ciudad, e.trofeos "
                + "FROM equipo e JOIN jugador j ON e.id = j.equipo_id WHERE j.id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, jugadorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(new Equipo(resultSet.getLong("id"), resultSet.getString("nombre"),
                        resultSet.getString("ciudad"), resultSet.getInt("trofeos")));
            }
        }
    }

    public List<Jugador> topGoleadores(long equipoId) throws SQLException {
        String sql = "SELECT id, nombre, posicion, goles, asistencias, mvps, equipo_id "
                + "FROM jugador WHERE equipo_id = ? ORDER BY goles DESC, nombre ASC LIMIT 3";
        return consultarJugadores(sql, equipoId);
    }

    private List<Jugador> consultarJugadores(String sql, long equipoId) throws SQLException {
        List<Jugador> jugadores = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, equipoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    jugadores.add(mapear(resultSet));
                }
            }
        }
        return jugadores;
    }

    public Optional<HistorialLogros> obtenerHistorial(long jugadorId) throws SQLException {
        String sql = "SELECT j.nombre AS jugador_nombre, e.nombre AS equipo_nombre, e.trofeos, "
                + "j.mvps, j.asistencias FROM jugador j JOIN equipo e ON e.id = j.equipo_id "
                + "WHERE j.id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, jugadorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(new HistorialLogros(resultSet.getString("jugador_nombre"),
                        resultSet.getString("equipo_nombre"), resultSet.getInt("trofeos"),
                        resultSet.getInt("mvps"), resultSet.getInt("asistencias")));
            }
        }
    }

    public Optional<RankingJugador> calcularRanking(long jugadorId, TipoEstadistica estadistica)
            throws SQLException {
        String columna = estadistica.getColumna();
        String sql = "SELECT objetivo.id, objetivo.nombre, objetivo." + columna + " AS valor, "
                + "(SELECT COUNT(*) + 1 FROM jugador otro WHERE otro." + columna
                + " > objetivo." + columna + ") AS posicion FROM jugador objetivo WHERE objetivo.id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, jugadorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(new RankingJugador(resultSet.getLong("id"),
                        resultSet.getString("nombre"), estadistica, resultSet.getInt("valor"),
                        resultSet.getLong("posicion")));
            }
        }
    }

    private void asignarParametros(PreparedStatement statement, Jugador jugador) throws SQLException {
        statement.setString(1, jugador.getNombre());
        statement.setString(2, jugador.getPosicion());
        statement.setInt(3, jugador.getGoles());
        statement.setInt(4, jugador.getAsistencias());
        statement.setInt(5, jugador.getMvps());
        statement.setLong(6, jugador.getEquipoId());
    }

    private Jugador mapear(ResultSet resultSet) throws SQLException {
        return new Jugador(resultSet.getLong("id"), resultSet.getString("nombre"),
                resultSet.getString("posicion"), resultSet.getInt("goles"),
                resultSet.getInt("asistencias"), resultSet.getInt("mvps"),
                resultSet.getLong("equipo_id"));
    }
}
