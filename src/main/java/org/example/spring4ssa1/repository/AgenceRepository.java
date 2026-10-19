package org.example.spring4ssa1.repository;

import org.example.spring4ssa1.domain.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenceRepository extends JpaRepository<Agence, Long> {

}
