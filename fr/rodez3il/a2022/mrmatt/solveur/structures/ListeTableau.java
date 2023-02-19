package fr.rodez3il.a2022.mrmatt.solveur.structures;

public class ListeTableau<E> implements Liste<E> {

    private E[] tab;

    private int taille;

    /**
     * Constructeur d'une ListeTableau
     */
    public ListeTableau() {
        this.tab = (E[]) (new Object[5]);
        this.taille = 0;
    }

    /**
     * Ajouter un element a la liste tableau
     * @param element l'élément à ajouter
     */
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


    /**
     * Verifier si la liste tableau est vide
     * @return boolean
     */
    @Override
    public boolean estVide() {
        return taille == 0;
    }

    /**
     * Véérifier si la liste de tableau est pleine
     * @return boolean
     */
    private boolean estPlein(){
        return this.taille == tab.length;
    }

    /**
     * Renvoi la taille de la listeTableau
     * @return int
     */
    @Override
    public int taille() {
        return this.taille;
    }


    /**
     * Enlever un element d'une listeTableau
     * @param i la position de l'élément.
     * @return element
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

    /**
     * renvoie l'element qui se situe a la position mis en param
     * @param i
     * @return element
     */
    @Override
    public E element(int i){
        return this.tab[i];
    }


    /**
     * Verifier si la liste de tableau contien l'element passé en param
     * @param e L'élément à comparer.
     * @return boolean
     */
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