CREATE DATABASE IF NOT EXISTS gestion_deportiva
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestion_deportiva;

CREATE TABLE IF NOT EXISTS equipo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    trofeos INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT chk_equipo_trofeos CHECK (trofeos >= 0)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS jugador (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    posicion VARCHAR(50) NOT NULL,
    goles INT NOT NULL DEFAULT 0,
    asistencias INT NOT NULL DEFAULT 0,
    mvps INT NOT NULL DEFAULT 0,
    equipo_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_jugador_equipo_goles (equipo_id, goles),
    INDEX idx_jugador_goles (goles),
    INDEX idx_jugador_asistencias (asistencias),
    CONSTRAINT chk_jugador_goles CHECK (goles >= 0),
    CONSTRAINT chk_jugador_asistencias CHECK (asistencias >= 0),
    CONSTRAINT chk_jugador_mvps CHECK (mvps >= 0),
    CONSTRAINT fk_jugador_equipo FOREIGN KEY (equipo_id)
        REFERENCES equipo (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB;
