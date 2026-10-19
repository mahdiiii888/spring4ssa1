package org.example.spring4ssa1.service;

import lombok.RequiredArgsConstructor;
import org.example.spring4ssa1.domain.Vehicule;
import org.example.spring4ssa1.exception.ResourceNotFoundException;
import org.example.spring4ssa1.repository.VehiculeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository repository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Le nouveau véhicule ne doit pas contenir d'identifiant");
        }
        validateVehicule(vehicule);
        return repository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return repository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existing = findById(id);
        validateVehicule(vehicule);
        // Copier les champs modifiables (scalars et agence). Ne pas remplacer les collections (equipements, maintenances)
        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setCategorie(vehicule.getCategorie());
        existing.setTarifJournalier(vehicule.getTarifJournalier());
        existing.setStatut(vehicule.getStatut());
        existing.setAgence(vehicule.getAgence());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        repository.deleteById(id);
    }

    private void validateVehicule(Vehicule vehicule) {
        if (vehicule.getImmatriculation() == null || vehicule.getImmatriculation().isBlank()) {
            throw new IllegalArgumentException("L'immatriculation est requise");
        }
        if (vehicule.getMarque() == null || vehicule.getMarque().isBlank()) {
            throw new IllegalArgumentException("La marque est requise");
        }
        if (vehicule.getModele() == null || vehicule.getModele().isBlank()) {
            throw new IllegalArgumentException("Le modele est requis");
        }
        if (vehicule.getCategorie() == null) {
            throw new IllegalArgumentException("La categorie du vehicule est requise");
        }
        if (vehicule.getStatut() == null) {
            throw new IllegalArgumentException("Le statut du vehicule est requis");
        }
        if (vehicule.getTarifJournalier() == null || vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier doit etre positif ou nul");
        }
    }
}

