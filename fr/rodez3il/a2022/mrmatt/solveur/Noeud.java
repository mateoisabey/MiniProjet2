package fr.rodez3il.a2022.mrmatt.solveur;

import fr.rodez3il.a2022.mrmatt.solveur.structures.DictionnaireChaine;
import fr.rodez3il.a2022.mrmatt.solveur.structures.ListeTableau;
import fr.rodez3il.a2022.mrmatt.sources.Commande;
import fr.rodez3il.a2022.mrmatt.sources.Niveau;

import java.util.Objects;

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

    /**
     * Verifier si la partie est gagné ou pas
     * @param gagnant
     * @param nouvelEtat
     */
    private void estGagnant(Boolean gagnant, Niveau nouvelEtat){
        if (gagnant) {
            solution = listeCmd;
        } else {

            valChaine = nouvelEtat.valeurChaine();

            Noeud childNode= configContient(nouvelEtat);

            this.child.ajouter(childNode);
        }
    }

    /**
     * Vérifie si configPossible contient la variable valChaine
     * @param newNiveau
     * @return Noeud
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Noeud noeud = (Noeud) o;
        return visited == noeud.visited && Objects.equals(configPossible, noeud.configPossible) && Objects.equals(niveau, noeud.niveau) && Objects.equals(child, noeud.child) && Objects.equals(cmd, noeud.cmd) && Objects.equals(solution, noeud.solution) && Objects.equals(listeCmd, noeud.listeCmd) && Objects.equals(valChaine, noeud.valChaine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configPossible, niveau, child, cmd, visited, solution, listeCmd, valChaine);
    }
}
