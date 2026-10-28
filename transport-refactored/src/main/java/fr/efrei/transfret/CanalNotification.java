package fr.efrei.transfret;

public interface CanalNotification {
    void envoyer(String destinataire, String objet, String corps);
}
