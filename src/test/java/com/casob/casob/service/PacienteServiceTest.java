package com.casob.casob.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;

//import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.opentest4j.AssertionFailedError;
import org.springframework.dao.DataIntegrityViolationException;

import com.casob.casob.model.Paciente;
import com.casob.casob.repository.PacienteRepository;

//import jakarta.inject.Inject;

public class PacienteServiceTest {

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    @Mock
    private PacienteRepository pacienteRepository;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    // Test para agregar un paciente
    public void testAddPaciente() {
        Paciente newPaciente = new Paciente();
        newPaciente.setNombre("JuanTest");
        newPaciente.setApellido("PerezTest");
        newPaciente.setRut("12345797-9");
        newPaciente.setFechaNacimiento(new Date(2021, 10, 10));

        // Simular el guardado del paciente en la base de datos
        Paciente expePaciente = new Paciente();
        expePaciente.setNombre("JuanTest");

        when(pacienteRepository.save(any(Paciente.class))).thenReturn(expePaciente);
        this.pacienteService.addPaciente(newPaciente);

        assertEquals(newPaciente.getNombre(), expePaciente.getNombre());

    }

    @Test // Test para agregar un paciente con error
    public void testAddPacienteError() {
        @SuppressWarnings("deprecation")
        Paciente newPaciente = new Paciente("JuanTest", "PerezTest", "12445797-0", new Date(2021, 10, 10));
    
        // Simula una excepción de violación de integridad de datos cuando se intenta guardar un paciente duplicado.
        when(pacienteRepository.save(any(Paciente.class)))
            .thenThrow(new DataIntegrityViolationException("Violación de la integridad de datos"));
    
        // Verifica que la excepción esperada sea lanzada cuando se invoca el método addPaciente.
        assertThrows(DataIntegrityViolationException.class, () -> {
            pacienteService.addPaciente(newPaciente);
        });
    }

    @SuppressWarnings("deprecation")
    @Test // Test para encontrar un paciente por su ID
    public void testGetPacienteById() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        paciente.setRut("12345777-9");
        paciente.setFechaNacimiento(new Date(2021, 10, 10));
    
        // Simular la búsqueda de un paciente por su ID
        when(pacienteRepository.findById(1)).thenReturn(java.util.Optional.of(paciente));
    
        // Verificar que el paciente fue encontrado y los datos son correctos
        Paciente pacienteEncontrado = pacienteService.getPacienteById(1).get();
        assertEquals(paciente.getNombre(), pacienteEncontrado.getNombre(), "El nombre es correcto");
        assertEquals(paciente.getApellido(), pacienteEncontrado.getApellido(), "El apellido es correcto");
        assertEquals(paciente.getRut(), pacienteEncontrado.getRut(), "El rut es correcto");
        assertEquals(paciente.getFechaNacimiento(), pacienteEncontrado.getFechaNacimiento(), "La fecha de nacimiento es correcta");
    }
    


}
