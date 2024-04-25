package com.casob.casob.repository;

import com.casob.casob.model.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PacienteRepositoryTest {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test // Test para encontrar un paciente por su ID
    public void testEncontrarPorId() {

        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        paciente.setRut("12345777-9");
        paciente.setFechaNacimiento(new Date()); // Usar la fecha actual para el test
    
        // Guardar el paciente en la base de datos usando TestEntityManager
        Paciente savedPaciente = entityManager.persistAndFlush(paciente);
    
        // Buscar el paciente por su ID
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(savedPaciente.getId());
    
        // Verificar que el paciente fue encontrado y los datos son correctos
        assertTrue(pacienteEncontrado.isPresent(), "El paciente fue encontrado");
        assertEquals(paciente.getNombre(), pacienteEncontrado.get().getNombre(), "El nombre es correcto");
        assertEquals(paciente.getApellido(), pacienteEncontrado.get().getApellido(), "El apellido es correcto");
        assertEquals(paciente.getRut(), pacienteEncontrado.get().getRut(), "El rut es correcto");
        assertEquals(paciente.getFechaNacimiento(), pacienteEncontrado.get().getFechaNacimiento(), "La fecha de nacimiento es correcta");
    }
    
    @Test // Test para no encontrar un paciente por su ID
    public void testNoEncontrarPorId() {
        // Buscar un paciente que no existe
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(88);
    
        // Verificar que el paciente no fue encontrado
        assertTrue(pacienteEncontrado.isEmpty(), "El paciente no fue encontrado");
    }


}
