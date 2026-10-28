package fr.efrei.transfret;

import java.util.List;

public class Train extends Vehicule {

    public Train(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, ModeTransport.RAIL);
    }

    @Override
    public boolean accepte(List<Colis> colis) {
        return true;
    }

    @Override
    public void partir(int distance) {
        System.out.println("Le train " + immatriculation + " circule sur " + distance + " km de voies ferrées");
    }
}
