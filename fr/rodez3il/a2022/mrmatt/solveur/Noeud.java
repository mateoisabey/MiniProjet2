package fr.rodez3il.a2022.mrmatt.solveur;

import fr.rodez3il.a2022.mrmatt.solveur.structures.DictionnaireChaine;
import fr.rodez3il.a2022.mrmatt.solveur.structures.ListeTableau;
import fr.rodez3il.a2022.mrmatt.sources.Commande;
import fr.rodez3il.a2022.mrmatt.sources.Niveau;

public class Noeud {

    //dictionnaire qui va stocker les différentes configurations connues
    private DictionnaireChaine<String, Noeud> configPossible;
    //référence vers l'état du niveau actuel
    private Niveau niveau;
    //fils du noeud
    private ListeTableau<Noeud> child;
    //représente la succession de commandes
    private String cmd;
    //permet de connaitre l'état de visite du noeud.
    private boolean visited;
    //chaine de caractère de la solution du niveau
    private String solution = null;
    private String listeCmd;
    String valChaine;

    public Noeud(DictionnaireChaine<String, Noeud> configPossible, Niveau niveau, String commandes) {
        this.configPossible = configPossible;
        this.niveau = niveau;
        this.child = new ListeTableau<>();
        this.cmd = commandes;
        this.visited = false;
    }


    public boolean getVisited() {
        return this.visited;
    }

    public ListeTableau<Noeud> getChilds() {
        return this.child;
    }

    public void ajouterFils(Noeud fils) {
        if (fils != null) {
            this.child.ajouter(fils);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Noeud)
            return false;
        Noeud other = (Noeud) obj;
        if (configPossible == null) {
            if (other.configPossible != null)
                return false;
        } else if (!configPossible.equals(other.configPossible))
            return false;
        if (niveau == null) {
            if (other.niveau != null)
                return false;
        } else if (!niveau.equals(other.niveau))
            return false;
        if (child == null) {
            if (other.child != null)
                return false;
        } else if (!child.equals(other.child))
            return false;
        if (cmd == null) {
            if (other.cmd != null)
                return false;
        } else if (!cmd.equals(other.cmd))
            return false;
        return true;
    }

    public String calculerFils() {
        this.visited = true;
        configPossible.inserer(niveau.valeurChaine(), this);
        Commande[] commandesPossible = Commande.values();
        for (int i = 0; i < commandesPossible.length; i++) {
            if (niveau.deplacementPossible(commandesPossible[i]) && niveau.enCours()) {
                listeCmd =this.cmd + " / " + commandesPossible[i] + " /";
                Niveau newNiveau = niveau.copier();
                newNiveau.deplacer(commandesPossible[i]);
                newNiveau.calcule();
                boolean gagnant = newNiveau.estGagnant();
                estGagnant(gagnant, newNiveau);

            }
        }

        return solution;
    }

    private void estGagnant(Boolean gagnant, Niveau nouvelEtat){
        if (gagnant) {
            solution = listeCmd;
        } else {

            valChaine = nouvelEtat.valeurChaine();

            Noeud childNode= configContient(nouvelEtat);

            this.child.ajouter(childNode);
        }
    }

    private Noeud configContient(Niveau newNiveau) {
        Noeud filsNoeud;
        if (configPossible.contient(valChaine)) {

            filsNoeud = configPossible.valeur(valChaine);

        } else {

            filsNoeud = new Noeud(this.configPossible, newNiveau, listeCmd);

            this.configPossible.inserer(valChaine, filsNoeud);
        }
        return filsNoeud;
    }

}
