package fr.efrei.transfret;

import java.util.HashMap;
import java.util.Map;

public class ClientRepositoryMemoire implements ClientRepository {

    private final Map<String, Client> clients = new HashMap<>();

    @Override
    public void sauvegarder(Client c) {
        clients.put(c.getEmail(), c);
    }

    @Override
    public Client trouver(String email) {
        return clients.get(email);
    }
}
