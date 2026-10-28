package fr.efrei.transfret;

public class EstimateurDelai {

    private static final double VITESSE_ROUTE_KMH = 70.0;
    private static final int HEURES_CONDUITE_JOUR = 9;
    private static final int HEURES_CONDUITE_URGENT = 18;
    private static final double KM_JOUR_RAIL = 1440.0;
    private static final int JOURS_MANUTENTION_RAIL = 1;
    private static final double KM_JOUR_MER = 720.0;
    private static final int JOURS_MANUTENTION_MER = 2;
    private static final int DELAI_AIR = 1;
    private static final int DELAI_AIR_LONG_COURRIER = 2;
    private static final int SEUIL_LONG_COURRIER = 3000;
    private static final int DELAI_MINIMUM = 1;

    // retourne le délai de livraison en jours
    public int calculer(int distance, ModeTransport mode, boolean urgent) {
        int jours = 0;
        switch (mode) {
            case ROUTE:
                double heures = distance / VITESSE_ROUTE_KMH;
                if (urgent) {
                    jours = (int) Math.ceil(heures / HEURES_CONDUITE_URGENT);
                } else {
                    jours = (int) Math.ceil(heures / HEURES_CONDUITE_JOUR);
                }
                break;
            case RAIL:
                jours = (int) Math.ceil(distance / KM_JOUR_RAIL) + JOURS_MANUTENTION_RAIL;
                if (urgent) jours = jours - 1;
                break;
            case MER:
                jours = (int) Math.ceil(distance / KM_JOUR_MER) + JOURS_MANUTENTION_MER;
                break;
            case AIR:
                jours = DELAI_AIR;
                if (distance > SEUIL_LONG_COURRIER) jours = DELAI_AIR_LONG_COURRIER;
                if (urgent) jours = DELAI_AIR;
                break;
        }
        if (jours < DELAI_MINIMUM) jours = DELAI_MINIMUM;
        return jours;
    }
}
