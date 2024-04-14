package com.casob.casob.service;

import com.casob.casob.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    Paciente addPaciente(Paciente paciente);
    List<Paciente> listarPacientes();
    Optional<Paciente> buscarPacientePorId(int id);
    Paciente guardarPaciente(Paciente paciente);
    void eliminarPaciente(int id);
    List<Paciente> getAllPaciente(); // Retorna todos los pacientes
    Optional<Paciente> getPacienteById(int id); // Busca un paciente por ID
    Paciente updatePaciente(int id, Paciente pacienteActualizado); // Actualiza un paciente existente
}
