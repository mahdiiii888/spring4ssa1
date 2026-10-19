package org.example.spring4ssa1.repository;

import org.example.spring4ssa1.domain.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipementRepository extends JpaRepository<Equipement, Long> {

}
