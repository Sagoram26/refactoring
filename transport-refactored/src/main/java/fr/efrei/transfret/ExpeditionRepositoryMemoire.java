package fr.efrei.transfret;

import java.util.HashMap;
import java.util.Map;

public class ExpeditionRepositoryMemoire implements ExpeditionRepository {

    private final Map<String, Expedition> expeditions = new HashMap<>();

    @Override
    public void sauvegarder(Expedition e) {
        expeditions.put(e.getId(), e);
    }

    @Override
    public Expedition trouver(String id) {
        return expeditions.get(id);
    }
}
