package org.example.spring4ssa1.repository;

import org.example.spring4ssa1.domain.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

}
