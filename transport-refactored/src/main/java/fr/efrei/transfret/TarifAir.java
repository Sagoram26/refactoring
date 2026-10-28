package fr.efrei.transfret;

public class TarifAir implements TarifTransport {

    private static final double TARIF_KM_AIR = 0.6;
    private static final double TARIF_KG_AIR = 4.5;
    private static final double FACTEUR_VOLUMETRIQUE = 167; // kg taxables par m3

    @Override
    public double prixBase(int distance, double poids, double volume) {
        double poidsTaxable = poids;
        if (volume * FACTEUR_VOLUMETRIQUE > poids) {
            poidsTaxable = volume * FACTEUR_VOLUMETRIQUE;
        }
        return distance * TARIF_KM_AIR + poidsTaxable * TARIF_KG_AIR;
    }
}
