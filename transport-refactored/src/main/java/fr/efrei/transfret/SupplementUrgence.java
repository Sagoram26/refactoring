package fr.efrei.transfret;

public class SupplementUrgence extends Supplement {
    private static final double SUPPLEMENT_URGENT = 1.5;

    public SupplementUrgence(Prix suivant) { super(suivant); }

    @Override
    public double calculer(DemandePrix d) {
        double prix = suivant.calculer(d);
        return d.urgent() ? prix * SUPPLEMENT_URGENT : prix;
    }
}
