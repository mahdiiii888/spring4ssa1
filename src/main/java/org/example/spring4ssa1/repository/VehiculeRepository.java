package org.example.spring4ssa1.repository;

import org.example.spring4ssa1.domain.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

}
