package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class SmsService {

    // garde une trace des SMS envoyés (facturés par l'opérateur)
    public static List<String[]> journal = new ArrayList<String[]>();

    private static final int LONGUEUR_MAX_SMS = 160;
    private static final String SUITE_TRONQUEE = "...";

    public void envoyerSms(String numero, String message) {
        if (message.length() > LONGUEUR_MAX_SMS) {
            message = message.substring(0, LONGUEUR_MAX_SMS - SUITE_TRONQUEE.length()) + SUITE_TRONQUEE;
        }
        System.out.println("[SMS] -> " + numero + " : " + message);
        journal.add(new String[]{numero, message});
    }
}
