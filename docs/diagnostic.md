# Diagnostic du code historique (étape 1)

## Rôle et dépendances

| Classe | Rôle | Dépend de |
|---|---|---|
| `ServiceClient` | valide, crée et enregistre un client, envoie l'email de bienvenue | `Client`, `Database` (singleton), `EmailService` (statique) |
| `ServiceExpedition` | validation, distance, affectation, prix, délai, cycle de vie, notifications | `Database`, `CalculateurPrix`, `AffectationVehicule`, `Utils`, `EmailService`, `SmsService`, `Expedition`, `Vehicule` |
| `CalculateurPrix` | prix HT (tarif, suppléments, urgence, remise, assurance) | `Colis` |
| `AffectationVehicule` | choisit le premier véhicule compatible et disponible | `Flotte` (statique), `Colis`, sous-classes de `Vehicule` (`instanceof`) |
| `Flotte` | liste statique des véhicules | `Camion`, `CamionFrigorifique`, `Train`, `Bateau`, `Avion` |
| `IVehicule` | interface unique de tous les véhicules | `Colis` |
| `Vehicule` | base abstraite : capacité, chargement, disponibilité, plein | `IVehicule`, `Colis` |
| `Camion`, `CamionFrigorifique`, `Train`, `Bateau`, `Avion` | véhicules concrets | `Vehicule` (`CamionFrigorifique` → `Camion`) |
| `Client` | modèle client (type en `String`) | aucune |
| `Colis` | modèle colis (type en `int`), volume, libellé | aucune |
| `Expedition` | modèle expédition + totaux + facture texte | `Client`, `Colis`, `Vehicule`, `Utils` |
| `Database` | persistance simulée (singleton + maps statiques) | `Client`, `Expedition` |
| `EmailService` | envoi d'email simulé, journal statique | aucune |
| `SmsService` | envoi de SMS simulé, journal statique | aucune |
| `Utils` | arrondi, dates, prix, email, table des distances | aucune |
| `Main` | démo | `Flotte`, `ServiceClient`, `ServiceExpedition`, `Colis` |

## Défauts

| Défaut | Emplacement (Classe.méthode) | Principe violé |
|---|---|---|
| Une seule méthode fait validation, distance, affectation, chargement, prix, délai, persistance et notification | `ServiceExpedition.creerExpedition` | S |
| Classe qui cumule devis, délais, cycle de vie (démarrer/livrer/annuler) et notifications | `ServiceExpedition` | S |
| Mise en forme de la facture dans l'entité | `Expedition.genererFacture` | S |
| Fourre-tout : dates, prix, email et table des distances | `Utils` (`formaterDate`, `getDistance`…) | S |
| Chaîne `if/else` sur le mode pour le tarif | `CalculateurPrix.calculerPrixHT` | O |
| Chaîne `if/else` sur le type de client pour la remise | `CalculateurPrix.calculerPrixHT` | O |
| `switch` sur le mode pour le délai | `ServiceExpedition.calculerDelai` | O |
| `if/else` sur le mode pour le déplacement | `ServiceExpedition.demarrerExpedition` | O |
| Cascade `instanceof` par mode/véhicule | `AffectationVehicule.trouverVehicule` | O |
| Règles d'acceptation codées en dur par mode (`"AIR"`, `"ROUTE"`/`"MER"`) | `ServiceExpedition.creerExpedition` | O |
| `switch` sur le code de type | `Colis.getLibelleType` | O |
| Refuse les colis non réfrigérés (précondition renforcée) | `CamionFrigorifique.charger` | L |
| Lève `UnsupportedOperationException` alors que `Vehicule.faireLePlein` réussit | `Train.faireLePlein` | L |
| L'exception est masquée par un `catch` vide | `ServiceExpedition.demarrerExpedition` | L |
| Interface trop large : `rouler`, `naviguer`, `voler`, `activerRefrigeration` imposées à tous | `IVehicule` | I |
| Implémentations qui lèvent `UnsupportedOperationException` | `Camion`/`Train`/`Bateau`/`Avion` (`naviguer`, `voler`, `rouler`, `activerRefrigeration`) | I |
| Singleton + maps statiques publiques | `Database.getInstance`, `Database.expeditions`/`clients` | D |
| Appel statique à l'email | `ServiceExpedition.notifier`, `ServiceClient.creerClient` → `EmailService.envoyer` | D |
| `new SmsService()` dans la méthode | `ServiceExpedition.notifier` | D |
| `new CalculateurPrix()`, `new AffectationVehicule()`, `Database.getInstance()` en champs | `ServiceExpedition` (champs) | D |
| Flotte globale statique | `AffectationVehicule.trouverVehicule` → `Flotte.vehicules` | D |
| Compteur statique public | `ServiceExpedition.compteur` | D |
| Distances statiques non injectables | `ServiceExpedition.creerExpedition`/`calculerDevis` → `Utils.getDistance` | D |
| Nombres magiques (1.2, 0.10, 150, 500, 167, 1.15, 1.30, 1.20, 1.5, 0.85, 0.90, 0.80, 5000, 0.02, 15) | `CalculateurPrix.calculerPrixHT` | nombres magiques |
| Nombres magiques (70, 9, 18, 1440, 720, 3000), TVA 1.2, frais 0.5, 1.852, 35000, 4 | `ServiceExpedition.calculerDelai`, `creerExpedition`, `annulerExpedition`, `demarrerExpedition` | nombres magiques |
| Type de colis en `int` (`getType() == 2`, `== 3`) | `CalculateurPrix.calculerPrixHT`, `AffectationVehicule.trouverVehicule`, `ServiceExpedition.creerExpedition`/`demarrerExpedition`, `CamionFrigorifique.charger` | type codes |
| Mode, type client, statut en `String` (`"ROUTE"`, `"PREMIUM"`, `"CREEE"`…) | `ServiceExpedition`, `CalculateurPrix`, `ServiceClient.creerClient`, `Expedition` | type codes |
| `Math.round(x * 100.0) / 100.0` dupliqué alors que `Utils.arrondir` existe | `CalculateurPrix.calculerPrixHT`, `ServiceExpedition.creerExpedition`/`calculerDevis`/`annulerExpedition` | DRY |
| Validation email dupliquée (`Utils.emailValide` inutilisé), volume recalculé | `ServiceClient.creerClient`, `Expedition.getVolumeTotal` | DRY |
| `SimpleDateFormat` statique, public, mutable et non thread-safe | `Utils.FORMAT_DATE` | encapsulation |
| Clé API en dur dans le code | `SmsService.apiKey` | sécurité |

## Ajouter le mode FLUVIAL : endroits à modifier (legacy)

1. `CalculateurPrix.calculerPrixHT` : nouvelle branche de tarif (et minimum éventuel).
2. `ServiceExpedition.calculerDelai` : nouveau `case "FLUVIAL"`.
3. `ServiceExpedition.creerExpedition` : contrôle réfrigéré (`ROUTE || MER` seulement) et contrôle urgent (interdit en `MER`) à revoir pour le fluvial.
4. `AffectationVehicule.trouverVehicule` : nouvelle branche `instanceof Peniche`.
5. `ServiceExpedition.demarrerExpedition` : nouveau déplacement selon le mode.
6. `IVehicule` : nouvelle méthode (ex. `naviguerFluvial`) si `naviguer` ne convient pas.
7. Dans ce cas, chaque classe de véhicule doit l'implémenter (`Camion`, `Train`, `Bateau`, `Avion`, souvent par `UnsupportedOperationException`).
8. Nouvelle classe `Peniche` (implémente aussi `rouler`, `voler` et `activerRefrigeration`).
9. `Flotte.initialiser` : ajouter une péniche.
10. `Utils.getDistance` : ajouter un trajet fluvial (ex. Paris - Le Havre).

Total : 10 endroits, dont 4 classes véhicule si `IVehicule` change. Il faudrait aussi toucher l'enum `ModeTransport` du module contrat.

## Ajouter le supplément hors gabarit : endroits à modifier (legacy)

1. `CalculateurPrix.calculerPrixHT` : détection dans la boucle sur les colis (une dimension > 200 cm) + multiplicateur ×1,10 dans le bloc des suppléments.

Total : 1 méthode, avec 2 modifications.

## ServiceClient : une ou deux responsabilités ?

On garde une seule classe qui orchestre la création du client : la validation est une précondition de la création, pas une responsabilité à part. L'envoi de l'email de bienvenue sera délégué à un `Notificateur` à l'étape 3.
