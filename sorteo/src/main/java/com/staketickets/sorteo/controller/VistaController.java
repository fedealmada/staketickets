package com.staketickets.sorteo.controller;

import com.staketickets.sorteo.service.PersonaService;
import com.staketickets.sorteo.model.PersonaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VistaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/participantes")
    public String index(Model model) {
        List<PersonaDTO> personas = personaService.traerPersonas();
        model.addAttribute("personas", personas);
        return "participantes";
    }

    @GetMapping("/info")
    public String mostrarInfo() {
        return "info"; 
    }

    @GetMapping("/admin")
    public String entrar() {
        return "admin";
    }

    @GetMapping("/")
    public String home() {
        return "info";
    }

}