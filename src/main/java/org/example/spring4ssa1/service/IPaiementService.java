package org.example.spring4ssa1.service;

import org.example.spring4ssa1.domain.Paiement;
import java.util.List;

public interface IPaiementService {
    Paiement findById(Long id);
    List<Paiement> findAll();
}
