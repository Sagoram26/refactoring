package fr.efrei.transfret;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ServiceExpedition {

    private static final int ID_ALEATOIRE_MIN = 1000;
    private static final int ID_ALEATOIRE_PLAGE = 9000;
    private static final double TAUX_TTC = 1.2;
    private static final double TAUX_FRAIS_ANNULATION = 0.5;

    private final ExpeditionRepository expeditions;
    private final CalculateurPrix calculateur;
    private final AffectationVehicule affectation;
    private final ValidateurExpedition validateur;
    private final EstimateurDelai estimateurDelai;
    private final Notificateur notificateur;
    private int compteur = 0;

    public ServiceExpedition(ExpeditionRepository expeditions, CalculateurPrix calculateur, AffectationVehicule affectation,
                             ValidateurExpedition validateur, EstimateurDelai estimateurDelai, Notificateur notificateur) {
        this.expeditions = expeditions;
        this.calculateur = calculateur;
        this.affectation = affectation;
        this.validateur = validateur;
        this.estimateurDelai = estimateurDelai;
        this.notificateur = notificateur;
    }

    public Expedition creerExpedition(Client client, List<Colis> colis, String villeDepart, String villeArrivee, ModeTransport mode, boolean urgent, boolean assurance) {
        if (!validateur.valider(client, colis, mode, urgent)) {
            return null;
        }
        int distance = TableDistances.distance(villeDepart, villeArrivee);
        if (distance == TableDistances.INCONNUE) {
            System.out.println("ERREUR : trajet inconnu " + villeDepart + " -> " + villeArrivee);
            return null;
        }

        Vehicule v = affectation.trouverVehicule(colis, mode);
        if (v == null) {
            System.out.println("ERREUR : aucun véhicule disponible pour cette expédition");
            return null;
        }
        for (Colis c : colis) {
            v.charger(c);
        }

        Expedition e = new Expedition();
        compteur++;
        e.setId("EXP-" + compteur + "-" + (ID_ALEATOIRE_MIN + new Random().nextInt(ID_ALEATOIRE_PLAGE)));
        e.setClient(client);
        e.setColis(colis);
        e.setVilleDepart(villeDepart);
        e.setVilleArrivee(villeArrivee);
        e.setDistance(distance);
        e.setMode(mode);
        e.setUrgent(urgent);
        e.setAssurance(assurance);
        e.setStatut(StatutExpedition.CREEE);
        e.setVehicule(v);
        e.setPrixHT(calculateur.calculerPrixHT(new DemandePrix(colis, distance, mode, urgent, assurance, client.getType())));
        e.setPrixTTC(Utils.arrondir(e.getPrixHT() * TAUX_TTC));
        e.setDateCreation(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(e.getDateCreation());
        cal.add(Calendar.DAY_OF_MONTH, estimateurDelai.calculer(distance, mode, urgent));
        e.setDateLivraisonPrevue(cal.getTime());

        expeditions.sauvegarder(e);
        notificateur.notifier(client, "Confirmation de votre expédition " + e.getId(),
                "Votre expédition " + villeDepart + " -> " + villeArrivee + " (" + mode + ") a été enregistrée. Montant : " + e.getPrixTTC() + " € TTC.",
                "TransFret : expédition " + e.getId() + " créée, livraison prévue le " + Utils.formaterDate(e.getDateLivraisonPrevue()));
        return e;
    }

    // retourne le prix TTC, -1 si le trajet est inconnu
    public double calculerDevis(List<Colis> colis, String villeDepart, String villeArrivee, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        int distance = TableDistances.distance(villeDepart, villeArrivee);
        if (distance == TableDistances.INCONNUE) {
            return -1;
        }
        double ht = calculateur.calculerPrixHT(new DemandePrix(colis, distance, mode, urgent, assurance, typeClient));
        return Utils.arrondir(ht * TAUX_TTC);
    }

    public boolean demarrerExpedition(String id) {
        Expedition e = expeditions.trouver(id);
        if (e == null || e.getStatut() != StatutExpedition.CREEE) {
            System.out.println("ERREUR : impossible de démarrer l'expédition " + id);
            return false;
        }
        e.setStatut(StatutExpedition.EN_COURS);
        Vehicule v = e.getVehicule();
        v.setDisponible(false);
        v.partir(e.getDistance());
        notificateur.notifier(e.getClient(), "Votre expédition " + e.getId() + " est en route",
                "Votre expédition a quitté " + e.getVilleDepart() + ".",
                "TransFret : expédition " + e.getId() + " partie de " + e.getVilleDepart());
        return true;
    }

    public boolean livrerExpedition(String id) {
        Expedition e = expeditions.trouver(id);
        if (e == null || e.getStatut() != StatutExpedition.EN_COURS) {
            System.out.println("ERREUR : impossible de livrer l'expédition " + id);
            return false;
        }
        e.setStatut(StatutExpedition.LIVREE);
        libererVehicule(e);
        notificateur.notifier(e.getClient(), "Votre expédition " + e.getId() + " est livrée",
                "Votre expédition est arrivée à " + e.getVilleArrivee() + ". Merci de votre confiance.",
                "TransFret : expédition " + e.getId() + " livrée à " + e.getVilleArrivee());
        return true;
    }

    public boolean annulerExpedition(String id) {
        Expedition e = expeditions.trouver(id);
        if (e == null || e.getStatut() == StatutExpedition.LIVREE || e.getStatut() == StatutExpedition.ANNULEE) {
            System.out.println("ERREUR : impossible d'annuler l'expédition " + id);
            return false;
        }
        if (e.getStatut() == StatutExpedition.EN_COURS) {
            // 50% de frais si déjà partie
            e.setFraisAnnulation(Utils.arrondir(e.getPrixTTC() * TAUX_FRAIS_ANNULATION));
        }
        e.setStatut(StatutExpedition.ANNULEE);
        libererVehicule(e);
        notificateur.notifier(e.getClient(), "Annulation de votre expédition " + e.getId(),
                "Votre expédition a été annulée. Frais d'annulation : " + e.getFraisAnnulation() + " €",
                "TransFret : expédition " + e.getId() + " annulée");
        return true;
    }

    private void libererVehicule(Expedition e) {
        for (Colis c : e.getColis()) {
            e.getVehicule().decharger(c);
        }
        e.getVehicule().setDisponible(true);
    }
}
