package com.casob.casob.controller;

import com.casob.casob.model.Paciente;
import com.casob.casob.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // Agregar HATEOAS
    // Endpoint para obtener todos los pacientes registrados.
    @GetMapping
    public CollectionModel<EntityModel<Paciente>> getAllPacientes() {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        List<EntityModel<Paciente>> pacientesResources = pacientes.stream()
                .map(paciente -> EntityModel.of(paciente,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteById(paciente.getId())).withSelfRel()
                ))
                .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPacientes());
        CollectionModel<EntityModel<Paciente>> resource = CollectionModel.of(pacientesResources, linkTo.withRel("pacientes"));
        return resource;
    }                  
/*
    public ResponseEntity<List<Paciente>> getAllPacientes() {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }
*/
    // Endpoint para obtener un paciente específico por ID.
    @GetMapping("/{id}")
    public EntityModel<Paciente> getPacienteById(@PathVariable @Min(1) int id) {
        Paciente paciente = pacienteService.getPacienteById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id : " + id));
        return EntityModel.of(paciente,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPacientes()).withRel("all-pacientes")
        );
    }

/*
    public ResponseEntity<Paciente> getPacienteById(@PathVariable int id) {
        Optional<Paciente> paciente = pacienteService.getPacienteById(id);
        return paciente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
*/
    // Endpoint para agregar un nuevo paciente.
    @PostMapping
    public EntityModel<Paciente> addPaciente(@Validated @RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.addPaciente(paciente);
        return EntityModel.of(nuevoPaciente,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteById(nuevoPaciente.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPacientes()).withRel("all-pacientes")
        );
    }
/*
    public ResponseEntity<Paciente> addPaciente(@Valid @RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.addPaciente(paciente);
        return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
    }
*/
    // Endpoint para actualizar un paciente existente por ID.
    @PutMapping("/{id}")
    public EntityModel<Paciente> updatePaciente(@PathVariable int id, @Valid @RequestBody Paciente pacienteActualizado) {
        Paciente pacienteActualizadoResult = pacienteService.updatePaciente(id, pacienteActualizado);
        return EntityModel.of(pacienteActualizadoResult,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPacientes()).withRel("all-pacientes")
        );
    }
/*
    public ResponseEntity<Paciente> updatePaciente(@PathVariable int id, @Valid @RequestBody Paciente pacienteActualizado) {
        return pacienteService.getPacienteById(id)
                .map(paciente -> {
                    Paciente pacienteActualizadoResult = pacienteService.updatePaciente(id, pacienteActualizado);
                    return new ResponseEntity<>(pacienteActualizadoResult, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
*/
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
