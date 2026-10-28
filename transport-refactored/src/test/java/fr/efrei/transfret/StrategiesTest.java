package fr.efrei.transfret;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StrategiesTest {

    @Test void tarifRoute() { assertEquals(220, new TarifRoute().prixBase(100, 1000, 0), 0.001); }
    @Test void tarifRailMinimum() { assertEquals(150, new TarifRail().prixBase(100, 100, 0), 0.001); }
    @Test void tarifRail() { assertEquals(870, new TarifRail().prixBase(1000, 1000, 0), 0.001); }
    @Test void tarifMerMinimum() { assertEquals(500, new TarifMer().prixBase(100, 100, 0), 0.001); }
    @Test void tarifMer() { assertEquals(1300, new TarifMer().prixBase(2000, 10000, 0), 0.001); }
    @Test void tarifAirPoidsVolumetrique() { assertEquals(1351.5, new TarifAir().prixBase(1000, 10, 1.0), 0.001); }
    @Test void tarifAirPoidsReel() { assertEquals(1500, new TarifAir().prixBase(1000, 200, 1.0), 0.001); }

    @Test void sansRemise() { assertEquals(100, new SansRemise().appliquer(100), 0.001); }
    @Test void remiseEntreprise() { assertEquals(900, new RemiseEntreprise().appliquer(1000), 0.001); }
    @Test void remiseEntrepriseGrosMontant() { assertEquals(5100, new RemiseEntreprise().appliquer(6000), 0.001); }
    @Test void remisePremium() { assertEquals(80, new RemisePremium().appliquer(100), 0.001); }

    @Test void delaiRoute() { assertEquals(2, ModeTransport.ROUTE.delai(775, false)); }
    @Test void delaiRailUrgent() { assertEquals(1, ModeTransport.RAIL.delai(465, true)); }
    @Test void delaiMer() { assertEquals(11, ModeTransport.MER.delai(5850, false)); }
    @Test void delaiAirLongCourrier() { assertEquals(2, ModeTransport.AIR.delai(5840, false)); }
}
