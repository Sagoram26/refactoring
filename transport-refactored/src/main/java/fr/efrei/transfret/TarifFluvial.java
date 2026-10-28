package fr.efrei.transfret;

public class TarifFluvial implements TarifTransport {

    private static final double TARIF_KM_FLUVIAL = 0.40;
    private static final double TARIF_KG_FLUVIAL = 0.02;
    private static final double MINIMUM_FLUVIAL = 300;

    @Override
    public double prixBase(int distance, double poids, double volume) {
        double prix = distance * TARIF_KM_FLUVIAL + poids * TARIF_KG_FLUVIAL;
        if (prix < MINIMUM_FLUVIAL) prix = MINIMUM_FLUVIAL;
        return prix;
    }
}
