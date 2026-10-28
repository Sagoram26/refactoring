package fr.efrei.transfret;

public abstract class Supplement implements Prix {
    protected final Prix suivant;

    protected Supplement(Prix suivant) {
        this.suivant = suivant;
    }
}
