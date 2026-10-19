package org.example.spring4ssa1.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.spring4ssa1.domain.Employe;
import org.example.spring4ssa1.service.IEmployeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeController {

    private final IEmployeService employeService;

    @Operation(summary = "Lister les employés")
    @GetMapping
    public List<Employe> findAll() { return employeService.findAll(); }

    @Operation(summary = "Consulter un employé")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Employé trouvé"),
                    @ApiResponse(responseCode = "404", description = "Employé introuvable") })
    @GetMapping("/{id}")
    public Employe findById(@PathVariable Long id) { return employeService.findById(id); }

    @Operation(summary = "Créer un employé")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Employé créé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe employe) { return employeService.create(employe); }

    @Operation(summary = "Modifier un employé")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Employé modifié"),
                    @ApiResponse(responseCode = "404", description = "Employé introuvable"),
                    @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PutMapping("/{id}")
    public Employe update(@PathVariable Long id, @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }

    @Operation(summary = "Supprimer un employé")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Employé supprimé"),
                    @ApiResponse(responseCode = "404", description = "Employé introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) { employeService.deleteById(id); }
}
