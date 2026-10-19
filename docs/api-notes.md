# Notes API – Atelier 5

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| AgenceController | GET /api/agences | findAll() | 200 | — |
| AgenceController | GET /api/agences/{id} | findById(id) | 200 | 404 |
| AgenceController | POST /api/agences | create(agence) | 201 | 400 |
| AgenceController | PUT /api/agences/{id} | update(id, agence) | 200 | 404, 400 |
| AgenceController | DELETE /api/agences/{id} | deleteById(id) | 204 | 404 |
| EmployeController | GET /api/employes | findAll() | 200 | — |
| EmployeController | GET /api/employes/{id} | findById(id) | 200 | 404 |
| EmployeController | POST /api/employes | create(employe) | 201 | 400 |
| EmployeController | PUT /api/employes/{id} | update(id, employe) | 200 | 404, 400 |
| EmployeController | DELETE /api/employes/{id} | deleteById(id) | 204 | 404 |
| VehiculeController | GET /api/vehicules | findAll() | 200 | — |
| VehiculeController | GET /api/vehicules/{id} | findById(id) | 200 | 404 |
| VehiculeController | POST /api/vehicules | create(vehicule) | 201 | 400 |
| VehiculeController | PUT /api/vehicules/{id} | update(id, vehicule) | 200 | 404, 400 |
| VehiculeController | DELETE /api/vehicules/{id} | deleteById(id) | 204 | 404 |
| EquipementController | GET /api/equipements | findAll() | 200 | — |
| EquipementController | GET /api/equipements/{id} | findById(id) | 200 | 404 |
| EquipementController | POST /api/equipements | create(equipement) | 201 | 400 |
| EquipementController | PUT /api/equipements/{id} | update(id, equipement) | 200 | 404, 400 |
| EquipementController | DELETE /api/equipements/{id} | deleteById(id) | 204 | 404 |
| ClientController | GET /api/clients | findAll() | 200 | — |
| ClientController | GET /api/clients/{id} | findById(id) | 200 | 404 |
| ClientController | POST /api/clients | create(client) | 201 | 400 |
| ClientController | PUT /api/clients/{id} | update(id, client) | 200 | 404, 400 |
| ClientController | DELETE /api/clients/{id} | deleteById(id) | 204 | 404 |
| ReservationController | GET /api/reservations | findAll() | 200 | — |
| ReservationController | GET /api/reservations/{id} | findById(id) | 200 | 404 |
| ReservationController | POST /api/reservations | create(reservation) | 201 | 400 |
| ReservationController | PUT /api/reservations/{id} | update(id, reservation) | 200 | 404, 400 |
| ReservationController | DELETE /api/reservations/{id} | deleteById(id) | 204 | 404 |
| ContratController | GET /api/contrats | findAll() | 200 | — |
| ContratController | GET /api/contrats/{id} | findById(id) | 200 | 404 |
| ContratController | POST /api/contrats | create(contrat) | 201 | 400 |
| ContratController | PUT /api/contrats/{id} | update(id, contrat) | 200 | 404, 400 |
| ContratController | DELETE /api/contrats/{id} | deleteById(id) | 204 | 404 |
| PaiementController | GET /api/paiements | findAll() | 200 | — |
| PaiementController | GET /api/paiements/{id} | findById(id) | 200 | 404 |
| PaiementController | POST /api/paiements | (aucun) | — | 405 |
| MaintenanceController | GET /api/maintenances | findAll() | 200 | — |
| MaintenanceController | GET /api/maintenances/{id} | findById(id) | 200 | 404 |
| MaintenanceController | POST /api/maintenances | create(maintenance) | 201 | 400 |
| MaintenanceController | PUT /api/maintenances/{id} | update(id, maintenance) | 200 | 404, 400 |
| MaintenanceController | DELETE /api/maintenances/{id} | deleteById(id) | 204 | 404 |

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
|---|---|---|
| Contrat.paiements / Paiement.contrat | @JsonManagedReference / @JsonBackReference ("contrat-paiements") | Composition (cascade = ALL) : les paiements sont créés dans le JSON de leur contrat. Le lien paiement → contrat est rétabli à la lecture. |
| Agence.vehicules | @JsonIgnore | Liste inverse : le véhicule est créé seul et désigne son agence par identifiant. |
| Client.reservations | @JsonIgnore | Même raison : la réservation désigne son client par identifiant. |
| Equipement.vehicules | @JsonIgnore | Côté inverse (mappedBy) d'une association many-to-many. |
| Vehicule.maintenances | @JsonIgnore | La maintenance désigne son véhicule par identifiant. |
| Reservation.contrat | @JsonIgnore | Côté inverse du one-to-one ; le contrat désigne sa réservation. |

**Différence entre @JsonBackReference et @JsonIgnore.**
- `@JsonBackReference` s'utilise toujours avec un `@JsonManagedReference` du même nom.
  Le côté enfant n'est pas écrit dans le JSON, mais à la désérialisation Jackson
  réaffecte le parent à chaque enfant. Le POST d'un contrat avec ses paiements
  garde donc le lien `paiement.contrat`.
- `@JsonIgnore` supprime la propriété dans les deux sens (lecture et écriture) : elle
  n'est jamais ni envoyée ni reçue. Il convient aux listes inverses sans composition.

## C. Postman et Swagger

- **Postman** sert à tester : requêtes rejouables, scripts qui vérifient les statuts et mémorisent
  des valeurs. Exemple rencontré : `GET /api/contrats/9999` doit renvoyer 404 avec un corps
  d'erreur JSON, et le script `pm.environment.set("contratId", ...)` réutilise l'identifiant
  du contrat créé pour le PUT et le DELETE.
- **Swagger UI** sert à documenter et explorer : le contrat de l'API est généré à partir du code
  des contrôleurs. Exemple rencontré : la section Schemas décrit `Contrat` à partir de l'entité,
  et « Try it out » permet d'envoyer un POST sans outil externe.
- Ils sont complémentaires : Postman vérifie le comportement, Swagger documente le contrat.

## D. Anomalies SonarQube

| Règle | Explication | Correction |
|---|---|---|
| `java:S4684` – « Replace this persistent entity with a simple POJO or DTO object » (`AgenceController`, ligne 22, `create(@RequestBody Agence agence)`) | Une entité JPA est utilisée directement comme corps de requête : un client peut renseigner des champs internes (identifiant, listes liées) et provoquer du *mass assignment*. L'API est aussi couplée au schéma de la base. | Court terme : validation dans le service (identifiant interdit à la création) et `@JsonIgnore` sur les listes inverses. Correction définitive : un DTO `AgenceRequest` dans `web.dto` (Chapitre 10). |
| `java:S4684` – même règle (`AgenceController`, ligne 42, `update(@PathVariable Long id, @RequestBody Agence agence)`) | Même risque en modification : le corps pourrait écraser des champs non modifiables. | Même correction : DTO d'entrée ne contenant que les attributs modifiables (`nom`, `ville`, `adresse`, `telephone`). Le service les recopie dans l'entité existante. |

## E. Questions

1. **Quel composant reçoit les requêtes ?** La `DispatcherServlet`, point d'entrée unique. Le
   `HandlerMapping` retrouve la méthode du contrôleur à appeler à partir du verbe HTTP et de l'URL.
2. **Pourquoi dépendre de `IContratService` ?** Pour respecter les couches : le contrôleur ne
   connaît que le contrat du service. Il ne touche ni au repository (accès aux données) ni à
   l'implémentation, ce qui garde le contrôleur mince et permet de changer l'implémentation
   sans le modifier.
3. **201, 204, 404 :** 201 signifie qu'une ressource a été créée par le POST. 204 signifie que la
   suppression a réussi sans corps à renvoyer. Une ressource absente lève
   `ResourceNotFoundException`, traduite en 404 par le `GlobalExceptionHandler` ; sans cela
   l'exception non gérée donnerait une erreur serveur 500.
4. **Pourquoi Paiement en lecture seule ?** Les paiements sont portés par le contrat
   (`cascade = ALL`) et `IPaiementService` n'a que `findAll` et `findById`. Il n'y a donc aucun
   `@PostMapping`, et `POST /api/paiements` reçoit **405 Method Not Allowed**.
5. **Risques de l'exposition des entités et choix des annotations :** les risques sont le
   couplage entre API et base de données, des champs internes visibles, les boucles de
   sérialisation et les proxys LAZY non sérialisables. `Contrat.paiements` est une composition :
   les paiements naissent avec le contrat, on les garde dans la réponse et on les recrée à la
   lecture, d'où la paire `@JsonManagedReference` / `@JsonBackReference`. `Agence.vehicules` est
   une simple liste inverse : le véhicule est créé seul avec `{"agence": {"idAgence": 1}}`,
   donc `@JsonIgnore` suffit.
