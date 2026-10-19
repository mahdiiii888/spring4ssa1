package org.example.spring4ssa1.service;

import org.example.spring4ssa1.domain.Employe;
import java.util.List;

public interface IEmployeService {
    Employe create(Employe employe);
    Employe findById(Long id);
    List<Employe> findAll();
    Employe update(Long id, Employe employe);
    void deleteById(Long id);
}
