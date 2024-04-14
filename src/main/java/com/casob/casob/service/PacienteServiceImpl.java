package com.casob.casob.service;

import java.util.List;
import java.util.Optional;

import com.casob.casob.model.Paciente;
import com.casob.casob.repository.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente addPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> buscarPacientePorId(int id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(int id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public List<Paciente> getAllPaciente() {
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> getPacienteById(int id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Paciente updatePaciente(int id, Paciente pacienteActualizado) {
        return pacienteRepository.findById(id)
            .map(paciente -> {
                paciente.setNombre(pacienteActualizado.getNombre());
                paciente.setApellido(pacienteActualizado.getApellido());
                paciente.setRut(pacienteActualizado.getRut());
                paciente.setFechaNacimiento(pacienteActualizado.getFechaNacimiento());
                return pacienteRepository.save(paciente);
            })
            .orElseThrow(() -> new IllegalArgumentException("No se pudo encontrar el paciente con ID: " + id));
    }
}
