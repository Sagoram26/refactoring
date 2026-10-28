package fr.efrei.transfret;

import java.util.Map;

public class TableDistances {

    public static final int INCONNUE = -1;

    private static final Map<String, Integer> DISTANCES = Map.of(
            cle("PARIS", "LYON"), 465,
            cle("PARIS", "MARSEILLE"), 775,
            cle("PARIS", "LILLE"), 225,
            cle("PARIS", "BORDEAUX"), 585,
            cle("LYON", "MARSEILLE"), 315,
            cle("LE HAVRE", "NEW YORK"), 5850,
            cle("MARSEILLE", "ALGER"), 760,
            cle("PARIS", "NEW YORK"), 5840);

    // retourne la distance en km entre deux villes, INCONNUE si le trajet n'est pas référencé
    public static int distance(String villeA, String villeB) {
        return DISTANCES.getOrDefault(cle(villeA, villeB), INCONNUE);
    }

    // clé indépendante de la casse et du sens du trajet
    private static String cle(String a, String b) {
        String x = a.toUpperCase();
        String y = b.toUpperCase();
        return x.compareTo(y) <= 0 ? x + "|" + y : y + "|" + x;
    }
}
