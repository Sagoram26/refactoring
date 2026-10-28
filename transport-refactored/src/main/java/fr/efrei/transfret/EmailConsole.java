package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class EmailConsole implements CanalNotification {

    private static final String SMTP_HOST = "smtp.transfret.fr";
    private static final String EXPEDITEUR = "noreply@transfret.fr";

    // garde une trace des emails envoyés (demandé par le support client)
    private final List<String[]> journal = new ArrayList<>();

    @Override
    public void envoyer(String destinataire, String objet, String corps) {
        if (destinataire == null || !destinataire.contains("@")) {
            return; // adresse invalide : rien n'est envoyé
        }
        System.out.println("[EMAIL via " + SMTP_HOST + "] " + EXPEDITEUR + " -> " + destinataire + " | " + objet);
        System.out.println("        " + corps);
        journal.add(new String[]{destinataire, objet, corps});
    }

    public List<String[]> getJournal() {
        return journal;
    }
}
