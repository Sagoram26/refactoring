package fr.efrei.transfret;

public class Remise extends Supplement {
    public Remise(Prix suivant) { super(suivant); }

    @Override
    public double calculer(DemandePrix d) {
        return d.typeClient().remise().appliquer(suivant.calculer(d));
    }
}
