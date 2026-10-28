package fr.efrei.transfret;

public class RemiseEntreprise implements PolitiqueRemise {

    private static final double SEUIL_REMISE_ENTREPRISE = 5000;
    private static final double COEF_REMISE_ENTREPRISE_GROS_MONTANT = 0.85;
    private static final double COEF_REMISE_ENTREPRISE = 0.90;

    @Override
    public double appliquer(double prix) {
        if (prix > SEUIL_REMISE_ENTREPRISE) {
            return prix * COEF_REMISE_ENTREPRISE_GROS_MONTANT;
        }
        return prix * COEF_REMISE_ENTREPRISE;
    }
}
