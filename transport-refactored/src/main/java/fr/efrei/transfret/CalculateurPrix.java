package fr.efrei.transfret;

public class CalculateurPrix {

    private final Prix chaine;

    public CalculateurPrix(Prix chaine) {
        this.chaine = chaine;
    }

    public double calculerPrixHT(DemandePrix d) {
        return Utils.arrondir(chaine.calculer(d));
    }
}
