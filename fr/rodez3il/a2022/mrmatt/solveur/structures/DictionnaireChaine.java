package fr.rodez3il.a2022.mrmatt.solveur.structures;

import java.util.Objects;

public class DictionnaireChaine<C, V> implements Dictionnaire<C, V> {

    public int taille() {
       return this.taille ;
    }

    private class Entree<C, V> {
        private C cle;
        private V val;

        public Entree(C cle, V val){
            this.val = val;
            this.cle = cle;
        }

        public C getCle() {
            return cle;
        }

        public void setCle(C cle) {
            this.cle = cle;
        }

        public V getVal() {
            return val;
        }

        public void setVal(V val) {
            this.val = val;
        }
    }

    public DictionnaireChaine() {
    }

    private ListeChainee<Entree> monDico = new ListeChainee<Entree>();
    private int taille = 0;


    /**
     * Permet l'insertion d'une entrée dans le dictionaire chainé
     * @param cle
     * @param valeur
     */
    @Override
    public void inserer(C cle, V valeur) {
        Entree monEntree = new Entree<>(cle, valeur);

        boolean alreadyHere = false;

        if (monDico.estVide())
            monDico.ajouter(monEntree);

        for(int i = 0; i<taille; i++){
            monEntree = monDico.element(i);
            if(monEntree.cle.equals(cle))
                monEntree.val = valeur;
        }

        if(!alreadyHere){
            monDico.ajouter(monEntree);
        }

        taille ++;

    }

    /**
     * Permet de verifier si dictionaire contient la clé passé en parametre
     * @param cle
     * @return un Boolean pour savoir si le dico contient bien la clé
     */
    @Override
    public boolean contient(C cle) {
        boolean result = false;
        Entree<C, V> element;

        for(int i = 0; i<taille; i++) {
            element = monDico.element(i);
            if(element.cle.equals(cle)){
                result = true;
            }

        }
        return result;
    }

    /**
     * Donne la valeur qui correspond a la clé passé en param
     * @param cle
     * @return Valeur
     */
    @Override
    public V valeur(C cle) {
        V val = null;

        if (monDico.estVide()) {
            val = null;
        } else if (!monDico.estVide()){
            for(int i = 0; i<taille ;i++) {
                Entree<C, V> element = monDico.element(i);
                if(element.cle.equals(cle))
                    val = element.val;
            }
        }
        return val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionnaireChaine<?, ?> that = (DictionnaireChaine<?, ?>) o;
        return taille == that.taille && monDico.equals(that.monDico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monDico, taille);
    }
}