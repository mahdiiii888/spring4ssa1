package org.example.spring4ssa1.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.spring4ssa1.domain.Reservation;
import org.example.spring4ssa1.service.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations", description = "Gestion des réservations")
public class ReservationController {

    private final IReservationService reservationService;

    @Operation(summary = "Lister les réservations")
    @GetMapping
    public List<Reservation> findAll() { return reservationService.findAll(); }

    @Operation(summary = "Consulter une réservation")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Réservation trouvée"),
                    @ApiResponse(responseCode = "404", description = "Réservation introuvable") })
    @GetMapping("/{id}")
    public Reservation findById(@PathVariable Long id) { return reservationService.findById(id); }

    @Operation(summary = "Créer une réservation")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Réservation créée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) { return reservationService.create(reservation); }

    @Operation(summary = "Modifier une réservation")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Réservation modifiée"),
                    @ApiResponse(responseCode = "404", description = "Réservation introuvable"),
                    @ApiResponse(responseCode = "400", description = "Données invalides") })
    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @Operation(summary = "Supprimer une réservation")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Réservation supprimée"),
                    @ApiResponse(responseCode = "404", description = "Réservation introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) { reservationService.deleteById(id); }
}
