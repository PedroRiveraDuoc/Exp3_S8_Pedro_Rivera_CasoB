package com.casob.casob.service;

import com.casob.casob.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    // Método para agregar un nuevo paciente, asumiendo que es el mismo que 'guardarPaciente'
    Paciente addPaciente(Paciente paciente);

    // Método para obtener todos los pacientes, asumiendo que es el mismo que 'listarPacientes'
    List<Paciente> getAllPacientes();

    // Método para obtener un paciente por su ID
    Optional<Paciente> getPacienteById(int id);

    // Método para actualizar un paciente existente
    Paciente updatePaciente(int id, Paciente pacienteActualizado);

    // Método para eliminar un paciente por su ID
    void eliminarPaciente(int id);
}
