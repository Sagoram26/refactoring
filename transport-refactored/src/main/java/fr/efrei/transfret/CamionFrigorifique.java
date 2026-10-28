package fr.efrei.transfret;

import java.util.List;

public class CamionFrigorifique extends Vehicule {

    private static final double TEMPERATURE_REFRIGERATION = 4;

    public CamionFrigorifique(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, ModeTransport.ROUTE);
    }

    @Override
    public boolean accepte(List<Colis> colis) {
        return colis.stream().allMatch(c -> c.getType() == TypeMarchandise.REFRIGERE);
    }

    @Override
    public void partir(int distance) {
        System.out.println("Le camion " + immatriculation + " roule sur " + distance + " km");
        System.out.println("Réfrigération activée à " + TEMPERATURE_REFRIGERATION + "°C sur " + immatriculation);
    }
}
