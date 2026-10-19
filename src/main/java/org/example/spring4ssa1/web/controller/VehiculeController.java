package org.example.spring4ssa1.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.spring4ssa1.domain.Vehicule;
import org.example.spring4ssa1.service.IVehiculeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister les véhicules")
    @GetMapping
    public List<Vehicule> findAll() { return vehiculeService.findAll(); }

    @Operation(summary = "Consulter un véhicule")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Véhicule trouvé"),
                    @ApiResponse(responseCode = "404", description = "Véhicule introuvable") })
    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable Long id) { return vehiculeService.findById(id); }

    @Operation(summary = "Créer un véhicule")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Véhicule créé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) { return vehiculeService.create(vehicule); }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Véhicule modifié"),
                    @ApiResponse(responseCode = "404", description = "Véhicule introuvable"),
                    @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PutMapping("/{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Véhicule supprimé"),
                    @ApiResponse(responseCode = "404", description = "Véhicule introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) { vehiculeService.deleteById(id); }
}
