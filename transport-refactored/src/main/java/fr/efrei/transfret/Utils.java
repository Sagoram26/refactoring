package fr.efrei.transfret;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static double arrondir(double valeur) {
        return Math.round(valeur * 100.0) / 100.0;
    }

    public static String formaterDate(Date d) {
        if (d == null) {
            return "";
        }
        return d.toInstant().atZone(ZoneId.systemDefault()).format(FORMAT_DATE);
    }

    public static String formaterPrix(double prix) {
        return String.format("%.2f €", prix);
    }

    public static boolean emailValide(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
