package com.staketickets.sorteo.service;


import com.staketickets.sorteo.model.Persona;
import com.staketickets.sorteo.model.PersonaDTO;
import com.staketickets.sorteo.repository.PersonaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

public List<Persona> buscarPorNombre(String nombre) {
    return personaRepository.findByNombreContainingIgnoreCase(nombre);
}

    public List<Persona> buscarPersonasPorNombreSimilar(String nombre) {
        return personaRepository.findByNombreLike(nombre);
    }
public List<PersonaDTO> traerPersonas() {
    return personaRepository.findAllByOrderByFechaRegistroDesc()
            .stream()
            .map(PersonaDTO::new)
            .toList();
}
 
}
