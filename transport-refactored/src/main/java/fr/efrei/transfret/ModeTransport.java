package fr.efrei.transfret;

public enum ModeTransport {
    ROUTE(new TarifRoute(), true, true, true) {
        @Override
        public int delai(int distance, boolean urgent) {
            double heures = distance / VITESSE_ROUTE_KMH;
            return (int) Math.ceil(heures / (urgent ? HEURES_CONDUITE_URGENT : HEURES_CONDUITE_JOUR));
        }
    },
    RAIL(new TarifRail(), true, false, true) {
        @Override
        public int delai(int distance, boolean urgent) {
            int jours = (int) Math.ceil(distance / KM_JOUR_RAIL) + JOURS_MANUTENTION_RAIL;
            return urgent ? jours - 1 : jours;
        }
    },
    MER(new TarifMer(), true, true, false) {
        @Override
        public int delai(int distance, boolean urgent) {
            return (int) Math.ceil(distance / KM_JOUR_MER) + JOURS_MANUTENTION_MER;
        }
    },
    AIR(new TarifAir(), false, false, true) {
        @Override
        public int delai(int distance, boolean urgent) {
            if (urgent) return DELAI_AIR;
            return distance > SEUIL_LONG_COURRIER ? DELAI_AIR_LONG_COURRIER : DELAI_AIR;
        }
    };

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

    private final TarifTransport tarif;
    private final boolean accepteDangereux;
    private final boolean accepteRefrigere;
    private final boolean accepteUrgence;

    ModeTransport(TarifTransport tarif, boolean accepteDangereux, boolean accepteRefrigere, boolean accepteUrgence) {
        this.tarif = tarif;
        this.accepteDangereux = accepteDangereux;
        this.accepteRefrigere = accepteRefrigere;
        this.accepteUrgence = accepteUrgence;
    }

    public TarifTransport tarif() { return tarif; }
    public boolean accepteDangereux() { return accepteDangereux; }
    public boolean accepteRefrigere() { return accepteRefrigere; }
    public boolean accepteUrgence() { return accepteUrgence; }

    // délai brut en jours, avant application du minimum
    public abstract int delai(int distance, boolean urgent);
}
