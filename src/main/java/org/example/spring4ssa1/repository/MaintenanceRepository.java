package org.example.spring4ssa1.repository;

import org.example.spring4ssa1.domain.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

}
