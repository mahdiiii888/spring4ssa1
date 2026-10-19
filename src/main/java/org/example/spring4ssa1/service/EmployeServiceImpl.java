package org.example.spring4ssa1.service;

import lombok.RequiredArgsConstructor;
import org.example.spring4ssa1.domain.Employe;
import org.example.spring4ssa1.exception.ResourceNotFoundException;
import org.example.spring4ssa1.repository.EmployeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final EmployeRepository repository;

    @Override
    public Employe create(Employe employe) {
        if (employe.getIdEmploye() != null) {
            throw new IllegalArgumentException("Le nouvel employé ne doit pas contenir d'identifiant");
        }
        validateEmploye(employe);
        return repository.save(employe);
    }

    @Override
    public Employe findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return repository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {
        Employe existing = findById(id);
        validateEmploye(employe);
        existing.setNom(employe.getNom());
        existing.setPrenom(employe.getPrenom());
        existing.setRole(employe.getRole());
        existing.setAgence(employe.getAgence());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        repository.deleteById(id);
    }

    private void validateEmploye(Employe employe) {
        if (employe.getNom() == null || employe.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'employé est requis");
        }
        if (employe.getRole() == null) {
            throw new IllegalArgumentException("Le rôle de l'employé est requis");
        }
    }
}
