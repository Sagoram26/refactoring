package fr.efrei.transfret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

class ExtensionTest {

    private final Prix cent = d -> 100;
    private final Client client = new Client("Client test", "test@mail.fr", "0600000000", "1 rue Test, 75001 Paris", TypeClient.PARTICULIER, null);

    private static List<Colis> colis(TypeMarchandise type) {
        return List.of(new Colis("x", 100, 50, 40, 30, type, 0));
    }

    private static DemandePrix demande(double longueur) {
        return new DemandePrix(List.of(new Colis("x", 10, longueur, 200, 200, TypeMarchandise.STANDARD, 0)), 100, ModeTransport.FLUVIAL, false, false, TypeClient.PARTICULIER);
    }

    @Test void tarifFluvial() {
        assertEquals(600, new TarifFluvial().prixBase(1000, 10000, 0), 0.001);
        assertEquals(300, new TarifFluvial().prixBase(365, 1000, 0), 0.001);
    }

    @Test void delaiFluvial() {
        assertEquals(3, ModeTransport.FLUVIAL.delai(365, false));
    }

    @Test void affectationPeniche() {
        assertEquals("PEN-0001", new AffectationVehicule(new Flotte()).trouverVehicule(colis(TypeMarchandise.STANDARD), ModeTransport.FLUVIAL).getImmatriculation());
    }

    @Test void validationFluvial() {
        assertFalse(new ValidateurExpedition().valider(client, colis(TypeMarchandise.REFRIGERE), ModeTransport.FLUVIAL, false));
        assertFalse(new ValidateurExpedition().valider(client, colis(TypeMarchandise.STANDARD), ModeTransport.FLUVIAL, true));
    }

    @Test void distanceParisLeHavre() {
        assertEquals(365, TableDistances.distance("Paris", "Le Havre"));
    }

    @Test void horsGabarit() {
        assertEquals(110, new SupplementHorsGabarit(cent).calculer(demande(250)), 0.001);
        assertEquals(100, new SupplementHorsGabarit(cent).calculer(demande(200)), 0.001);
    }
}
