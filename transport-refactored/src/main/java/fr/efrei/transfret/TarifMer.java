package fr.efrei.transfret;

public class TarifMer implements TarifTransport {

    private static final double TARIF_KM_MER = 0.5;
    private static final double TARIF_KG_MER = 0.03;
    private static final double MINIMUM_MER = 500;

    @Override
    public double prixBase(int distance, double poids, double volume) {
        double prix = distance * TARIF_KM_MER + poids * TARIF_KG_MER;
        if (prix < MINIMUM_MER) prix = MINIMUM_MER;
        return prix;
    }
}
