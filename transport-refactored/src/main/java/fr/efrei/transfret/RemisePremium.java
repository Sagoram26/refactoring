package fr.efrei.transfret;

public class RemisePremium implements PolitiqueRemise {

    private static final double COEF_REMISE_PREMIUM = 0.80;

    @Override
    public double appliquer(double prix) {
        return prix * COEF_REMISE_PREMIUM;
    }
}
