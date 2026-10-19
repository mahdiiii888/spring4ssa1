# Atelier 4 - Notes de service

## A) Injection par constructeur vs injection par champ

- **Injection par constructeur**: les dependances sont `final`, obligatoires des la creation de l'objet, testables facilement (mock simple via constructeur), et compatibles avec les regles qualite (Sonar).
- **Injection par champ (`@Autowired` sur attribut)**: cache les dependances, rend les tests unitaires plus difficiles sans reflexion, et favorise des objets partiellement initialises.
- **Choix retenu ici**: injection par constructeur avec Lombok `@RequiredArgsConstructor` sur tous les services.

## B) Tableau des 9 services

| Service | Dependance injectee | Methode d'injection | Justification |
|---|---|---|---|
| `AgenceServiceImpl` | `AgenceRepository` | Constructeur (`@RequiredArgsConstructor`) | CRUD de `Agence`, decouplage via interface Spring Data |
| `EmployeServiceImpl` | `EmployeRepository` | Constructeur (`@RequiredArgsConstructor`) | CRUD de `Employe`, validation metier centralisee |
| `VehiculeServiceImpl` | `VehiculeRepository` | Constructeur (`@RequiredArgsConstructor`) | CRUD de `Vehicule`, controles sur champs numeriques |
| `EquipementServiceImpl` | `EquipementRepository` | Constructeur (`@RequiredArgsConstructor`) | CRUD de `Equipement`, evite l'ecrasement de collection |
| `ClientServiceImpl` | `ClientRepository` | Constructeur (`@RequiredArgsConstructor`) | CRUD de `Client`, validation de champs d'identification |
| `ReservationServiceImpl` | `ReservationRepository` | Constructeur (`@RequiredArgsConstructor`) | CRUD de `Reservation`, regle de coherence des dates |
| `ContratServiceImpl` | `ContratRepository` | Constructeur (`@RequiredArgsConstructor`) | CRUD de `Contrat`, preservation de la relation paiements |
| `PaiementServiceImpl` | `PaiementRepository` | Constructeur (`@RequiredArgsConstructor`) | Service en lecture seule (gestion via `Contrat`) |
| `MaintenanceServiceImpl` | `MaintenanceRepository` | Constructeur (`@RequiredArgsConstructor`) | CRUD de `Maintenance`, coherence des dates |

## C) Diagnostic/correction de 3 erreurs d'injection de dependances

1. **Injection par champ (`@Autowired` sur attribut mutable)**
   - Probleme: dependance implicite, testabilite reduite.
   - Correction: remplacer par un attribut `final` + injection constructeur (`@RequiredArgsConstructor`).

2. **Instantiation manuelle (`new ...Repository()` ou `new ...Service()`)**
   - Probleme: contourne le conteneur IoC, cycle de vie non gere par Spring.
   - Correction: injecter le bean gere par Spring via constructeur.

3. **Dependance sur implementation concrete au lieu d'interface Spring Data**
   - Probleme: couplage fort et moindre flexibilite.
   - Correction: injecter les interfaces `...Repository`/`I...Service` selon le besoin.

## D) Exemples de constats SonarQube for IDE (sans execution reelle ici)

> Remarque: aucune analyse SonarQube for IDE n'a ete executee dans cet environnement. Les points ci-dessous sont des constats types alignes avec les regles usuelles.

1. **Field injection should be avoided**
   - Pourquoi: rend la classe plus difficile a tester et cacherait les dependances.
   - Correction appliquee: injection par constructeur avec `final` et `@RequiredArgsConstructor`.

2. **Generic exceptions should not be thrown**
   - Pourquoi: une exception trop generique degrade la lisibilite et la gestion d'erreur.
   - Correction appliquee: `ResourceNotFoundException` pour les IDs inexistants, `IllegalArgumentException` avec messages explicites pour les donnees invalides.

3. **Methods should not have too many responsibilities**
   - Pourquoi: lisibilite et maintenance plus difficiles quand validation + persistence + mapping sont melanges.
   - Correction appliquee: extraction de validations dans des methodes privees (`validateClient`, `validateContrat`, etc.).

## E) Reponses aux 5 questions conceptuelles (Atelier 4 - IoC/DI/Service)

1. **Quel est le role du conteneur IoC ?**
   - Il cree, configure et injecte les beans; il gere leur cycle de vie et diminue le couplage entre classes.

2. **Pourquoi preferer l'injection par constructeur ?**
   - Elle impose les dependances obligatoires, facilite les tests unitaires et supporte l'immutabilite (`final`).

3. **Pourquoi separer la couche Service de Repository ?**
   - `Repository` gere l'acces aux donnees; `Service` centralise les regles metier et la coherence applicative.

4. **Pourquoi ne pas remplacer l'entite entiere pendant un update ?**
   - Pour eviter d'ecraser ID/associations/collections et conserver l'etat JPA gere (relations, orphanRemoval, cascade).

5. **Pourquoi un service Paiement en lecture seule ici ?**
   - Parce que les paiements sont rattaches au cycle de vie du `Contrat`; cela preserve la coherence de l'agregat metier.

## Limites/ambiguite du modele actuel

- `Employe`: pas de champ "poste"; la regle est appliquee sur `role`.
- `Vehicule`: pas de champ kilometrage; la regle est appliquee sur `tarifJournalier`.
- `Equipement`: pas de champ quantite; seule la validation de `libelle` est possible.
- `Maintenance`: pas de champ cout; seule la validation des dates est possible.

