package fr.efrei.transfret;

public class Assurance extends Supplement {
    private static final double TAUX_ASSURANCE = 0.02;
    private static final double ASSURANCE_MINIMUM = 15;

    public Assurance(Prix suivant) { super(suivant); }

    @Override
    public double calculer(DemandePrix d) {
        double prix = suivant.calculer(d);
        if (!d.assurance()) return prix;
        double montant = d.valeur() * TAUX_ASSURANCE;
        if (montant < ASSURANCE_MINIMUM) montant = ASSURANCE_MINIMUM;
        return prix + montant;
    }
}
