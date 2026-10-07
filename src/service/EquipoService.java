package service;

import dao.EquipoDAO;
import model.Equipo;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class EquipoService {
    private final EquipoDAO equipoDAO;

    public EquipoService(EquipoDAO equipoDAO) {
        this.equipoDAO = Objects.requireNonNull(equipoDAO);
    }

    public void guardar(Equipo equipo) throws SQLException {
        Objects.requireNonNull(equipo, "El equipo es obligatorio.");
        validarTexto(equipo.getNombre(), "El nombre del equipo es obligatorio.");
        validarTexto(equipo.getCiudad(), "La ciudad del equipo es obligatoria.");
        if (equipo.getTrofeos() < 0) {
            throw new IllegalArgumentException("El número de trofeos no puede ser negativo.");
        }
        equipoDAO.insertar(equipo);
    }

    public Optional<Equipo> buscarPorId(long id) throws SQLException {
        validarId(id);
        return equipoDAO.buscarPorId(id);
    }

    public boolean actualizar(Equipo equipo) throws SQLException {
        Objects.requireNonNull(equipo, "El equipo es obligatorio.");
        validarId(equipo.getId());
        validarTexto(equipo.getNombre(), "El nombre del equipo es obligatorio.");
        validarTexto(equipo.getCiudad(), "La ciudad del equipo es obligatoria.");
        if (equipo.getTrofeos() < 0) {
            throw new IllegalArgumentException("El número de trofeos no puede ser negativo.");
        }
        return equipoDAO.actualizar(equipo);
    }

    public boolean eliminar(long id) throws SQLException {
        validarId(id);
        return equipoDAO.eliminar(id);
    }

    private void validarTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private void validarId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo.");
        }
    }
}
