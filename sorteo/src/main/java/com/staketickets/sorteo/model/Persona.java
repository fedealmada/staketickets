package com.staketickets.sorteo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data

@Table(name = "personas")

public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;

    @Column(name = "nombre", nullable = true)
    private String nombre;

    @Column(name = "apellido", nullable = true)
    private String apellido;

    @Column(name = "usuario_stake", nullable = true)
    private String usuarioStake;

    @Column(name = "usuario_instagram", nullable = true)
    private String usuarioInstagram;

    @Column(name = "usuario_kick", nullable = true)
    private String usuarioKick;

    @Column(name = "trx", nullable = true)
    private String trx;

    @Column(name = "fecha_registro", nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaRegistro;

    @Column(name = "nacionalidad", nullable = true)
    private String nacionalidad;

    @Column(name = "telefono", nullable = true)
    private String telefono;

    @Column(name = "extra_tickets", nullable = false)
    private int extraTickets = 0;

    @Column(name = "monto_apostado", nullable = false)
    private BigDecimal montoApostado = BigDecimal.ZERO;

    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }

    public int getTickets() {
        if (montoApostado == null)
            return 0;

        return montoApostado
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.DOWN) // división entera
                .add(BigDecimal.ONE) // sumamos 1 al resultado
                .add(BigDecimal.valueOf(extraTickets))
                .intValue();
    }

}
