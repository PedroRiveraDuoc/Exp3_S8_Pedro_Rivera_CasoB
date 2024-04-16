package com.casob.casob.controller;

import com.casob.casob.model.Paciente;
import com.casob.casob.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // Endpoint para obtener todos los pacientes registrados.
    @GetMapping
    public ResponseEntity<List<Paciente>> getAllPacientes() {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    // Endpoint para obtener un paciente específico por ID.
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable int id) {
        Optional<Paciente> paciente = pacienteService.getPacienteById(id);
        return paciente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint para agregar un nuevo paciente.
    @PostMapping
    public ResponseEntity<Paciente> addPaciente(@Valid @RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.addPaciente(paciente);
        return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
    }

    // Endpoint para actualizar un paciente existente por ID.
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable int id, @Valid @RequestBody Paciente pacienteActualizado) {
        return pacienteService.getPacienteById(id)
                .map(paciente -> {
                    Paciente pacienteActualizadoResult = pacienteService.updatePaciente(id, pacienteActualizado);
                    return new ResponseEntity<>(pacienteActualizadoResult, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint para eliminar un paciente existente por ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable int id) {
        return pacienteService.getPacienteById(id)
                .map(paciente -> {
                    pacienteService.eliminarPaciente(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
