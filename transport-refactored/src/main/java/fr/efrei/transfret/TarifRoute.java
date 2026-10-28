package fr.efrei.transfret;

public class TarifRoute implements TarifTransport {

    private static final double TARIF_KM_ROUTE = 1.2;
    private static final double TARIF_KG_ROUTE = 0.10;

    @Override
    public double prixBase(int distance, double poids, double volume) {
        return distance * TARIF_KM_ROUTE + poids * TARIF_KG_ROUTE;
    }
}
