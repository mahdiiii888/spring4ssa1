package org.example.spring4ssa1.service;

import lombok.RequiredArgsConstructor;
import org.example.spring4ssa1.domain.Paiement;
import org.example.spring4ssa1.exception.ResourceNotFoundException;
import org.example.spring4ssa1.repository.PaiementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final PaiementRepository repository;

    @Override
    public Paiement findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return repository.findAll();
    }
}

