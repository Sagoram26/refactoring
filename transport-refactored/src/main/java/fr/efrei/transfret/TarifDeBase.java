package fr.efrei.transfret;

public class TarifDeBase implements Prix {
    @Override
    public double calculer(DemandePrix d) {
        return d.mode().tarif().prixBase(d.distance(), d.poids(), d.volume());
    }
}
