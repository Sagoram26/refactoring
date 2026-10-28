package fr.efrei.transfret;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class DecorateursTest {

    private final Prix cent = d -> 100;

    private static DemandePrix demande(TypeMarchandise type, double valeur, boolean urgent, boolean assurance, TypeClient client) {
        return new DemandePrix(List.of(new Colis("x", 10, 50, 40, 30, type, valeur)), 100, ModeTransport.ROUTE, urgent, assurance, client);
    }

    private static DemandePrix demande(TypeMarchandise type) {
        return demande(type, 0, false, false, TypeClient.PARTICULIER);
    }

    @Test void fragile() {
        assertEquals(115, new SupplementFragile(cent).calculer(demande(TypeMarchandise.FRAGILE)), 0.001);
        assertEquals(100, new SupplementFragile(cent).calculer(demande(TypeMarchandise.STANDARD)), 0.001);
    }

    @Test void dangereux() {
        assertEquals(130, new SupplementDangereux(cent).calculer(demande(TypeMarchandise.DANGEREUX)), 0.001);
        assertEquals(100, new SupplementDangereux(cent).calculer(demande(TypeMarchandise.STANDARD)), 0.001);
    }

    @Test void refrigere() {
        assertEquals(120, new SupplementRefrigere(cent).calculer(demande(TypeMarchandise.REFRIGERE)), 0.001);
        assertEquals(100, new SupplementRefrigere(cent).calculer(demande(TypeMarchandise.STANDARD)), 0.001);
    }

    @Test void urgence() {
        assertEquals(150, new SupplementUrgence(cent).calculer(demande(TypeMarchandise.STANDARD, 0, true, false, TypeClient.PARTICULIER)), 0.001);
        assertEquals(100, new SupplementUrgence(cent).calculer(demande(TypeMarchandise.STANDARD)), 0.001);
    }

    @Test void remise() {
        assertEquals(80, new Remise(cent).calculer(demande(TypeMarchandise.STANDARD, 0, false, false, TypeClient.PREMIUM)), 0.001);
        assertEquals(100, new Remise(cent).calculer(demande(TypeMarchandise.STANDARD)), 0.001);
    }

    @Test void assurance() {
        assertEquals(115, new Assurance(cent).calculer(demande(TypeMarchandise.STANDARD, 100, false, true, TypeClient.PARTICULIER)), 0.001);
        assertEquals(300, new Assurance(cent).calculer(demande(TypeMarchandise.STANDARD, 10000, false, true, TypeClient.PARTICULIER)), 0.001);
        assertEquals(100, new Assurance(cent).calculer(demande(TypeMarchandise.STANDARD, 10000, false, false, TypeClient.PARTICULIER)), 0.001);
    }

    @Test void tarifDeBase() {
        assertEquals(121, new TarifDeBase().calculer(demande(TypeMarchandise.STANDARD)), 0.001);
    }
}
