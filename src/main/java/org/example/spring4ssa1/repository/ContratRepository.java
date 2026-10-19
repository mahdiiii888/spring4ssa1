package org.example.spring4ssa1.repository;

import org.example.spring4ssa1.domain.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratRepository extends JpaRepository<Contrat, Long> {

}
