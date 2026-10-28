package fr.efrei.transfret;

public class SupplementRefrigere extends Supplement {
    private static final double SUPPLEMENT_REFRIGERE = 1.20;

    public SupplementRefrigere(Prix suivant) { super(suivant); }

    @Override
    public double calculer(DemandePrix d) {
        double prix = suivant.calculer(d);
        return d.contient(TypeMarchandise.REFRIGERE) ? prix * SUPPLEMENT_REFRIGERE : prix;
    }
}
