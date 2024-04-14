package com.casob.casob.controller;

import com.casob.casob.model.Paciente;
import com.casob.casob.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;

    // Endpoint para obtener todos los paciente registrados.
    @GetMapping
    public List<Paciente> getAllPaciente() {
        return pacienteService.getAllPaciente();
    }

    // Endpoint para obtener un paciente específico por ID.
    @GetMapping("/{id}")
    public Optional<Paciente> getPaciente(@PathVariable int id) {
        return pacienteService.getPacienteById(id);
    }

    // Endpoint para agregar un nuevo paciente a la lista.
    @PostMapping("/pacientes")
    public Paciente addPaciente(@RequestBody Paciente paciente) {
        return pacienteService.addPaciente(paciente);
    }

    // Endpoint para actualizar un paciente existente por ID.
    @PutMapping("/{id}")
    public Paciente updatePaciente(@PathVariable int id, @RequestBody Paciente pacienteActualizado) {
        return pacienteService.updatePaciente(id, pacienteActualizado);
    }

    // Endpoint para eliminar un paciente existente por ID.
    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable int id) {
        pacienteService.eliminarPaciente(id);
    }
}