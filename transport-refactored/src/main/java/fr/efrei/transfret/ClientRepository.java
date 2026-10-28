package fr.efrei.transfret;

public interface ClientRepository {
    void sauvegarder(Client c);
    Client trouver(String email);
}
