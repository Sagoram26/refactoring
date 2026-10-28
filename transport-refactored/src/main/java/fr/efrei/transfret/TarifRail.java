package fr.efrei.transfret;

public class TarifRail implements TarifTransport {

    private static final double TARIF_KM_RAIL = 0.8;
    private static final double TARIF_KG_RAIL = 0.07;
    private static final double MINIMUM_RAIL = 150;

    @Override
    public double prixBase(int distance, double poids, double volume) {
        double prix = distance * TARIF_KM_RAIL + poids * TARIF_KG_RAIL;
        if (prix < MINIMUM_RAIL) prix = MINIMUM_RAIL;
        return prix;
    }
}
