package fr.rodez3il.a2022.mrmatt.solveur.structures;

import java.util.Objects;

public class ListeChainee<T> implements Liste<T> {


    /**
     *  Les maillons composent la ListeChainee
     */
    class Maillon {
        private T val;

        private Maillon next;

        public Maillon(T val) {
            this.val = val;
            this.next = null;
        }


        public void setNext(Maillon next){
            this.next = next;
        }

        public T getVal() {
            return this.val;
        }


        public Maillon getNext() {
            return this.next;
        }
    }

    private Maillon head;

    private int taille;


    public ListeChainee() {
        this.head = null;
        this.taille = 0;
    }


    /**
     * Ajouter un maillon a la liste chainée
     * @param e l'élément à ajouter
     */
    @Override
    public void ajouter(T e){

        Maillon newMaillon = new Maillon(e);
        if (head == null) {
            head = newMaillon;
        } else {
            Maillon dernierMaillon = getMaillon(taille - 1);
            dernierMaillon.setNext(newMaillon);
        }

        taille++;
    }

    /**
     * Accesseur des maillon
     * @param i
     * @return un maillon
     */
    private Maillon getMaillon(int i){

        Maillon maillon = head;
        for (int j = 0; j < i; j++) {
            maillon = maillon.getNext();
        }
        return maillon;
    }

    @Override
    public T element(int i){
        Maillon maillon = getMaillon(i);
        return maillon.getVal();
    }

    /**
     * Permet d'enlever un maillon d'une liste chainé
     * @param i la position de l'élément.
     * @return
     */
    @Override
    public T enlever(int i){

        Maillon sup = getMaillon(i);

        if (i == 0) {

            head.getNext();
        } else if(i == taille - 1) {

            Maillon maillonPrecedent = getMaillon(i-1);
            maillonPrecedent.setNext(null);
        } else {
            Maillon maillonPrecedent = getMaillon(i - 1);
            maillonPrecedent.setNext(getMaillon(i+1));
        }

        taille--;

        return sup.getVal();
    }


    /**
     * Accesseur de la taille
     * @return
     */
    @Override
    public int taille(){
        return this.taille;
    }

    /**
     * Verifier si la liste est vide
     * @return boolean
     */
    @Override
    public boolean estVide(){
        return taille == 0;
    }


    /**
     * Verifier si la liste contiens bien l'element passer en parametre
     * @param e L'élément à comparer.
     * @return boolean
     */
    @Override
    public boolean contient(T e) {
        boolean res = false;
        Maillon maillon = head;
        while (maillon != null) {
            if (maillon.getVal().equals(e)) {
                res = true;
            }
            maillon = maillon.getNext();
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListeChainee<?> that = (ListeChainee<?>) o;
        return taille == that.taille && Objects.equals(head, that.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, taille);
    }
}