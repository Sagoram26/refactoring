package fr.efrei.transfret;

public class SupplementDangereux extends Supplement {
    private static final double SUPPLEMENT_DANGEREUX = 1.30;

    public SupplementDangereux(Prix suivant) { super(suivant); }

    @Override
    public double calculer(DemandePrix d) {
        double prix = suivant.calculer(d);
        return d.contient(TypeMarchandise.DANGEREUX) ? prix * SUPPLEMENT_DANGEREUX : prix;
    }
}
