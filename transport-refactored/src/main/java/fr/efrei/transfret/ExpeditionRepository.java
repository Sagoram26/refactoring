package fr.efrei.transfret;

public interface ExpeditionRepository {
    void sauvegarder(Expedition e);
    Expedition trouver(String id);
}
