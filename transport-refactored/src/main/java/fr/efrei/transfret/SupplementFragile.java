package fr.efrei.transfret;

public class SupplementFragile extends Supplement {
    private static final double SUPPLEMENT_FRAGILE = 1.15;

    public SupplementFragile(Prix suivant) { super(suivant); }

    @Override
    public double calculer(DemandePrix d) {
        double prix = suivant.calculer(d);
        return d.contient(TypeMarchandise.FRAGILE) ? prix * SUPPLEMENT_FRAGILE : prix;
    }
}
