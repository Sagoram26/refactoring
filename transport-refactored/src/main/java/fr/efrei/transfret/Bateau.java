package fr.efrei.transfret;

import java.util.List;

public class Bateau extends Vehicule {

    private static final double KM_PAR_MILLE_NAUTIQUE = 1.852;

    private boolean conteneursRefrigeres;

    public Bateau(String immatriculation, double capaciteMax, boolean conteneursRefrigeres) {
        super(immatriculation, capaciteMax, ModeTransport.MER);
        this.conteneursRefrigeres = conteneursRefrigeres;
    }

    @Override
    public boolean accepte(List<Colis> colis) {
        return conteneursRefrigeres || !contient(colis, TypeMarchandise.REFRIGERE);
    }

    @Override
    public void partir(int distance) {
        System.out.println("Le navire " + immatriculation + " navigue sur " + (int) (distance / KM_PAR_MILLE_NAUTIQUE) + " milles nautiques");
    }
}
