package com.staketickets.sorteo.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.staketickets.sorteo.model.Persona;
import com.staketickets.sorteo.model.PersonaDTO;
import com.staketickets.sorteo.repository.PersonaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
public class PersonaController {

    @Autowired
    private PersonaRepository persoRepo;

    @GetMapping("/persona/traer")
    public List<PersonaDTO> traerPersonas() {
        List<Persona> personas = persoRepo.findAllByOrderByFechaRegistroDesc();
        return personas.stream()
                .map(PersonaDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/persona/traer/{id}")
    public ResponseEntity<PersonaDTO> traerUnaPersona(@PathVariable Long id) {
        Optional<Persona> persona = persoRepo.findById(id);
        return persona.map(value -> ResponseEntity.ok(new PersonaDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/persona/crear")
    public ResponseEntity<?> crearPersona(@RequestBody PersonaDTO dto) {
        try {
            Persona persona = dto.toEntity(); // método en DTO que convierte al modelo
            Persona guardada = persoRepo.save(persona);
            return ResponseEntity.ok(new PersonaDTO(guardada));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al crear persona: " + e.getMessage());
        }
    }

    @DeleteMapping("/persona/borrar/{id}")
    public String borrarUnaPersona(@PathVariable Long id) {
        persoRepo.deleteById(id);
        return "persona eliminada correctamente";
    }

    @PutMapping("/persona/actualizar/{id}")
    public ResponseEntity<Map<String, String>> actualizarUnaPersona(@PathVariable Long id,
            @RequestBody PersonaDTO dto) {
        Optional<Persona> personaOpt = persoRepo.findById(id);
        if (personaOpt.isEmpty())
            return ResponseEntity.notFound().build();

        Persona persona = personaOpt.get();

        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setUsuarioStake(dto.getUsuarioStake());
        persona.setUsuarioInstagram(dto.getUsuarioInstagram());
        persona.setUsuarioKick(dto.getUsuarioKick());
        persona.setTrx(dto.getTrx());
        persona.setNacionalidad(dto.getNacionalidad());
        persona.setTelefono(dto.getTelefono());
        persona.setExtraTickets(dto.getCantidadExtraTickets());
        persona.setMontoApostado(dto.getMontoApostado());

        persoRepo.save(persona);

        return ResponseEntity.ok(Map.of("mensaje", "Persona actualizada correctamente"));
    }

    @GetMapping("/persona/buscar/{nombre}")
    public List<PersonaDTO> buscarPorNombre(@PathVariable String nombre) {
        return persoRepo.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(PersonaDTO::new)
                .toList();
    }

    @GetMapping("/persona/buscarSimilar/{usuario}")
    public List<PersonaDTO> buscarPorUsuarioStake(@PathVariable String usuario) {
        List<Persona> personas = persoRepo.findByUsuarioStakeContainingIgnoreCase(usuario);
        return personas.stream()
                .map(PersonaDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/persona/exportar-tickets")
    public ResponseEntity<byte[]> exportarTickets() {
        List<Persona> personas = persoRepo.findAll();
        StringJoiner contenido = new StringJoiner(",");

        for (Persona persona : personas) {
            int cantidadTickets = persona.getTickets();
            String nombre = persona.getUsuarioInstagram();

            for (int i = 0; i < cantidadTickets; i++) {
                contenido.add(nombre);
            }
        }

        byte[] contenidoBytes = contenido.toString().getBytes(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.attachment().filename("tickets.txt").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(contenidoBytes);
    }

}
