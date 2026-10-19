package org.example.spring4ssa1.repository;

import org.example.spring4ssa1.domain.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

}
