package com.casob.casob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import com.casob.casob.model.Paciente;
//import java.util.List;
//import java.util.Optional;


public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
}
