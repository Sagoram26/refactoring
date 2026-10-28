package fr.efrei.transfret;

import java.util.List;

public class Camion extends Vehicule {

    protected boolean adr; // certification matières dangereuses

    public Camion(String immatriculation, double capaciteMax, boolean adr) {
        super(immatriculation, capaciteMax, ModeTransport.ROUTE);
        this.adr = adr;
    }

    @Override
    public boolean accepte(List<Colis> colis) {
        return !contient(colis, TypeMarchandise.REFRIGERE) && (adr || !contient(colis, TypeMarchandise.DANGEREUX));
    }

    @Override
    public void partir(int distance) {
        System.out.println("Le camion " + immatriculation + " roule sur " + distance + " km");
    }
}
