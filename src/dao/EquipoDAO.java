package dao;

import config.DatabaseConnection;
import model.Equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class EquipoDAO {
    public void insertar(Equipo equipo) throws SQLException {
        String sql = "INSERT INTO equipo (nombre, ciudad, trofeos) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, equipo.getNombre());
            statement.setString(2, equipo.getCiudad());
            statement.setInt(3, equipo.getTrofeos());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    equipo.setId(keys.getLong(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para el equipo.");
                }
            }
        }
    }

    public Optional<Equipo> buscarPorId(long id) throws SQLException {
        String sql = "SELECT id, nombre, ciudad, trofeos FROM equipo WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapear(resultSet)) : Optional.empty();
            }
        }
    }

    public boolean actualizar(Equipo equipo) throws SQLException {
        String sql = "UPDATE equipo SET nombre = ?, ciudad = ?, trofeos = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, equipo.getNombre());
            statement.setString(2, equipo.getCiudad());
            statement.setInt(3, equipo.getTrofeos());
            statement.setLong(4, equipo.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM equipo WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private Equipo mapear(ResultSet resultSet) throws SQLException {
        return new Equipo(resultSet.getLong("id"), resultSet.getString("nombre"),
                resultSet.getString("ciudad"), resultSet.getInt("trofeos"));
    }
}
