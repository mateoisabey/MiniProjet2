package fr.rodez3il.a2022.mrmatt.solveur.structures;

public class ListeChainee<T> implements Liste<T> {

    /**
     * La classe Maillon représente un élement d'une liste chainée
     */

    class Maillon {
        // l'element strockée dans le maillon
        private T donnee;
        //Le maillon suivant dans la liste
        private Maillon suivant;

        /**
         * Constructeur qui permet de créer un nouveau maillon
         * @param donnee : la donnée à stocker dans le maillon
         * @return retourne un nouveau Maillon
         */
        public Maillon(T donnee) {
            this.donnee = donnee;
            this.suivant = null;
        }

        /**
         * Permet de définir la donnée stockée dans le maillon
         * @param donnee : la nouvelle donnée à stocker dans le maillon
         */
        public void setDonne(T donnee) {
            this.donnee = donnee;
        }

        /**
         * Permet de définir le maillon suivant dans la liste chainée
         * @param suivant : le nouveau maillon suivant
         */
        public void setSuivant(Maillon suivant){
            this.suivant = suivant;
        }

        /**
         * Récupère la donnée stockée dans le maillon
         * @return T la donnée dans le maillon
         */
        public T getDonnee() {
            return this.donnee;
        }

        /**
         * Récupère le maillon suivant dans la liste
         * @return Maillon le maillon suivant dans la liste
         */
        public Maillon getSuivant() {
            return this.suivant;
        }
    }

    //Le maillon qui représenta la tête de la liste chainée
    private Maillon tete;
    //La taille de la liste chainée
    private int taille;

    /**
     * Constructeur qui créer une nouvelle liste chainée vide
     * @return : une liste chainée vide
     */
    public ListeChainee() {
        this.tete = null;
        this.taille = 0;
    }

    /**
     * Ajoute un élément à la fin de la liste
     * @param element : l'élément à ajouter
     */
    @Override
    public void ajouter(T element){
        //Créer un maillon pour l'élément à ajouter à la liste
        Maillon nouveauMaillon = new Maillon(element);
        //si la liste a pas de tête, alors le maillon deviant la tête
        if (tete == null) {
            tete = nouveauMaillon;
        } else {
            Maillon dernierMaillon = getMaillon(taille - 1);
            dernierMaillon.setSuivant(nouveauMaillon);
        }
        //on augmente le compte d'élément de la liste
        taille++;
    }

    /**
     * Retourne le maille à l'index spécifié
     * @param i : l'index du maillon à retourner
     * @return : Le maillon correspondant à l'index spécifié
     */
    private Maillon getMaillon(int i){
        //on initialise le maillon au premier élément de la liste.
        Maillon maillon = tete;
        //on boucle pour trouver le maillon correspondant à l'index spécifié
        for (int j = 0; j < i; j++) {
            maillon = maillon.getSuivant();
        }
        return maillon;
    }

    /**
     * Renvoie l'élément à l'index spécifié à la liste chainée
     * @param i : index de l'élément à renvoyer
     * return element : l'élément de la liste à l'index spécifié
     */
    @Override
    public T element(int i) throws IndexOutOfBoundsException{
        //vérifie si l'index est valide
        if (i < 0 ||i >= this.taille)
            throw new IndexOutOfBoundsException();

        //on récupère le maillon
        Maillon maillon = getMaillon(i);

        return maillon.getDonnee();
    }

    /**
     * Enleve et renvoie l'élément à l'index spécifié dans la liste
     * @param i : l'index de l'élément à enlever
     * @return T : l'élément à l'index spécifié
     */
    @Override
    public T enlever(int i) throws IndexOutOfBoundsException {
        //on vérifie si l'index est valide
        if (i<0 ||i >= this.taille)
            throw new IndexOutOfBoundsException();

        Maillon maillonSupprimer = getMaillon(i);

        //on met à jour les liens entre les maillons selon l'index
        if (i == 0) {
            //si le premier maillon est enlever, on déplace la reférence de la tête sur le maillon suivant
            tete.getSuivant();
        } else if(i == taille - 1) {
            //Si on enlève le dernier maillon, on enlève le lien entre le dernier maillon et le maillon le précédent
            Maillon maillonPrecedent = getMaillon(i-1);
            maillonPrecedent.setSuivant(null);
        } else {
            //Sinon, si le maillon à enlever et au milieu de la liste, on reconnecte les maillons correctements entre eux
            Maillon maillonPrecedent = getMaillon(i - 1);
            maillonPrecedent.setSuivant(getMaillon(i+1));
        }
        //on enlève un élément à la taille de la liste
        taille--;

        return maillonSupprimer.getDonnee();
    }

    /**
     * Retourne la taille de la liste
     * @return this.taille : la taille de la liste
     */
    @Override
    public int taille(){
        return this.taille;
    }

    /**
     * Indique si la liste est vide ou non
     * @return true ssi la liste est vide
     */
    @Override
    public boolean estVide(){
        boolean etat;
        if (this.taille == 0) {
            etat = true;
        } else {
            etat = false;
        }
        return etat;
    }

    /**
     * Vérifie si un élément est contenu dans la liste
     * @param element : l'élément à chercher
     * @return true ssi l'élément est contenu dans la liste
     */
    @Override
    public boolean contient(T element) {
        boolean resultat = false;
        Maillon maillon = tete;
        //on parcours tous les éléments de la liste
        while (maillon != null) {
            if (maillon.getDonnee().equals(element)) {
                resultat = true;
            }
            maillon = maillon.getSuivant();
        }
        return resultat;
    }


}