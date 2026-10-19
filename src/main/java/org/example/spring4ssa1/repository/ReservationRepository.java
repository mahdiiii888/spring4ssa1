package org.example.spring4ssa1.repository;

import org.example.spring4ssa1.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
