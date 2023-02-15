package fr.rodez3il.a2022.mrmatt.solveur.structures;

import java.util.Arrays;


import java.util.Arrays;
import java.util.Objects;

public class ListeTableau<E> implements Liste<E> {
    private E[] tab;
    private int taille;

    public ListeTableau() {
        this.tab = (E[]) new Object[10];
        this.taille = 0;
    }

    private boolean estPlein(){
        return this.taille == tab.length;
    }



    @Override
    public void ajouter(E element) {
        if ( estPlein()) {
            this.taille *= 2;
            Object[] tab2 = new Object[tab.length * 2];
            for (int i = 0; i < tab.length; i++) {
                tab2[i] = tab[i];
            }
            tab2[taille] = element;

        }
        tab[taille] = element;

        this.taille++;

    }

    @Override
    public boolean estVide() {
        return taille == 0;
    }

    @Override
    public int taille() {
        return this.taille;
    }

    @Override
    public E enlever(int i) {
        E res = (E) tab[i];
        for (int j = i; j < taille; j++) {
            tab[j] = tab[j + 1];
        }
        this.taille--;
        return (E) res;
    }

    @Override
    public E element(int i) {
        return (E) tab[i];
    }

    @Override
    public boolean contient(E e) {
        Boolean state = false;
        for (int i = 0; i < tab.length; i++) {
            if (this.tab[i] != null && this.tab[i].equals(e)) {
                state = true;
            }
        }

        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListeTableau<?> that = (ListeTableau<?>) o;
        return taille == that.taille && Arrays.equals(tab, that.tab);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(taille);
        result = 31 * result + Arrays.hashCode(tab);
        return result;
    }
}