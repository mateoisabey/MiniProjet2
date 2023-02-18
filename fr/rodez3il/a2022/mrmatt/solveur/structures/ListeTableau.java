package fr.rodez3il.a2022.mrmatt.solveur.structures;

public class ListeTableau<E> implements Liste<E> {

    private E[] tab;

    private int taille;

    public ListeTableau() {
        this.tab = (E[]) (new Object[5]);
        this.taille = 0;
    }

    @Override
    public void ajouter(E element) {
        if ( estPlein()) {

            Object[] tab2 = new Object[tab.length * 2];
            for (int i = 0; i < tab.length; i++) {
                tab2[i] = tab[i];
            }
            this.tab = (E[]) tab2;

        }
        tab[taille] = element;

        this.taille++;

    }


    @Override
    public boolean estVide() {
        return taille == 0;
    }
    private boolean estPlein(){
        return this.taille == tab.length;
    }

    @Override
    public int taille() {
        return this.taille;
    }

    /**
     * Enlève (et retourne) l'élément à la position
     * i.
     * @param i la position de l'élément.
     * @return L'élément qui a été supprimé.
     */

    @Override
    public E enlever(int i) {
        E res = (E) tab[i];
        for (int j = i; j < taille - 1; j++) {
            tab[j] = tab[j + 1];
        }
        this.taille--;
        return (E) res;
    }
    @Override
    public E element(int i){
        return this.tab[i];
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

}