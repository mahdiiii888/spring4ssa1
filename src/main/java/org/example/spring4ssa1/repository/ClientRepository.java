package org.example.spring4ssa1.repository;

import org.example.spring4ssa1.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
