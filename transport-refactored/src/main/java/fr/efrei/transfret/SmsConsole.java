package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class SmsConsole implements CanalNotification {

    private static final int LONGUEUR_MAX_SMS = 160;
    private static final String SUITE_TRONQUEE = "...";

    // garde une trace des SMS envoyés (facturés par l'opérateur)
    private final List<String[]> journal = new ArrayList<>();

    // objet = texte du SMS ; corps ignoré
    @Override
    public void envoyer(String numero, String objet, String corps) {
        String message = objet;
        if (message.length() > LONGUEUR_MAX_SMS) {
            message = message.substring(0, LONGUEUR_MAX_SMS - SUITE_TRONQUEE.length()) + SUITE_TRONQUEE;
        }
        System.out.println("[SMS] -> " + numero + " : " + message);
        journal.add(new String[]{numero, message});
    }

    public List<String[]> getJournal() {
        return journal;
    }
}
