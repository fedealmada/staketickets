package com.staketickets.sorteo.model;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class PersonaDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String usuarioStake;
    private String usuarioInstagram;
    private String usuarioKick;
    private String trx;
    private LocalDateTime fechaRegistro;
    private String nacionalidad;
    private String telefono;
    private BigDecimal montoApostado;

    @JsonProperty("tickets")
    private int cantidadTickets;

    @JsonProperty("extraTickets")
    private int cantidadExtraTickets;

    public PersonaDTO() {
    }

    public PersonaDTO(Persona p) {
        this.id = p.getIdPersona();
        this.nombre = p.getNombre();
        this.apellido = p.getApellido();
        this.usuarioStake = p.getUsuarioStake();
        this.usuarioInstagram = p.getUsuarioInstagram();
        this.usuarioKick = p.getUsuarioKick();
        this.trx = p.getTrx();
        this.fechaRegistro = p.getFechaRegistro();
        this.nacionalidad = p.getNacionalidad();
        this.telefono = p.getTelefono();
        this.montoApostado = p.getMontoApostado();

        this.cantidadExtraTickets = p.getExtraTickets();
        this.cantidadTickets = p.getTickets() + p.getExtraTickets();
    }

    public Persona toEntity() {
        Persona persona = new Persona();
        persona.setIdPersona(this.id);
        persona.setNombre(this.nombre);
        persona.setApellido(this.apellido);
        persona.setUsuarioStake(this.usuarioStake);
        persona.setUsuarioInstagram(this.usuarioInstagram);
        persona.setUsuarioKick(this.usuarioKick);
        persona.setTrx(this.trx);
        persona.setFechaRegistro(this.fechaRegistro);
        persona.setNacionalidad(this.nacionalidad);
        persona.setTelefono(this.telefono);
        persona.setExtraTickets(this.cantidadExtraTickets);
        persona.setMontoApostado(this.montoApostado != null ? this.montoApostado : BigDecimal.ZERO);
        return persona;
    }
}