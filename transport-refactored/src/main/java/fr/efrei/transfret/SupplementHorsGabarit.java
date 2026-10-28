package fr.efrei.transfret;

public class SupplementHorsGabarit extends Supplement {
    private static final double SUPPLEMENT_HORS_GABARIT = 1.10;
    private static final double DIMENSION_MAX_CM = 200;

    public SupplementHorsGabarit(Prix suivant) { super(suivant); }

    @Override
    public double calculer(DemandePrix d) {
        double prix = suivant.calculer(d);
        boolean horsGabarit = d.colis().stream().anyMatch(c -> c.getLongueur() > DIMENSION_MAX_CM
                || c.getLargeur() > DIMENSION_MAX_CM || c.getHauteur() > DIMENSION_MAX_CM);
        return horsGabarit ? prix * SUPPLEMENT_HORS_GABARIT : prix;
    }
}
