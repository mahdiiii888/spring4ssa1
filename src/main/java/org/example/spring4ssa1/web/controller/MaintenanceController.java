package org.example.spring4ssa1.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.spring4ssa1.domain.Maintenance;
import org.example.spring4ssa1.service.IMaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Lister les maintenances")
    @GetMapping
    public List<Maintenance> findAll() { return maintenanceService.findAll(); }

    @Operation(summary = "Consulter une maintenance")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Maintenance trouvée"),
                    @ApiResponse(responseCode = "404", description = "Maintenance introuvable") })
    @GetMapping("/{id}")
    public Maintenance findById(@PathVariable Long id) { return maintenanceService.findById(id); }

    @Operation(summary = "Créer une maintenance")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Maintenance créée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maintenance create(@RequestBody Maintenance maintenance) { return maintenanceService.create(maintenance); }

    @Operation(summary = "Modifier une maintenance")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Maintenance modifiée"),
                    @ApiResponse(responseCode = "404", description = "Maintenance introuvable"),
                    @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PutMapping("/{id}")
    public Maintenance update(@PathVariable Long id, @RequestBody Maintenance maintenance) {
        return maintenanceService.update(id, maintenance);
    }

    @Operation(summary = "Supprimer une maintenance")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Maintenance supprimée"),
                    @ApiResponse(responseCode = "404", description = "Maintenance introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) { maintenanceService.deleteById(id); }
}
