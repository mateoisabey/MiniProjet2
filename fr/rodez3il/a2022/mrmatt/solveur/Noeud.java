package fr.rodez3il.a2022.mrmatt.solveur;

import fr.rodez3il.a2022.mrmatt.solveur.structures.DictionnaireChaine;
import fr.rodez3il.a2022.mrmatt.solveur.structures.ListeTableau;
import fr.rodez3il.a2022.mrmatt.sources.Commande;
import fr.rodez3il.a2022.mrmatt.sources.Niveau;

import java.util.Objects;

public class Noeud {

    private static DictionnaireChaine<String, Noeud> configPossible;


    private static Niveau state;

    //fils ca sera un tableau de noeud 4 position (haut, bas, Gauche, Droite)
    private ListeTableau<Noeud> childs ;

    private String cmd;

    private String res = null ;
    private String listeCmd;

    private static Boolean visited;

    /**
     * Constructeur d'un noeud.
     * @param configPossible
     * @param state
     * @param cmd
     * @param res
     * @param listeCmd
     */
    public Noeud(DictionnaireChaine<String, Noeud> configPossible, Niveau state, String cmd, String res, String listeCmd) {
        this.configPossible = configPossible;
        this.state = state;
        this.childs = new ListeTableau<>();
        this.cmd = cmd;
        this.res = res;
        this.listeCmd = listeCmd;
        this.visited = false;
    }


    public String calculerFils(){
        visited = true;
        configPossible.inserer(state.valeurChaine(),this);
        Commande[] commandesPossible = Commande.values();
        //boucler pour toutes les directions
        for (int i = 0; i < commandesPossible.length; i++) {
            //Verification si c'est en cours
            if (state.enCours()) {
                //verification si le joueur peur se déplacer
                if (state.deplacementPossible(commandesPossible[i])) {
                    listeCmd = this.cmd + "" + commandesPossible[i];
                    Niveau newState = state.copier();
                    newState.calcule();
                    Boolean aGagne = newState.estGagnant();
                    affichageGagnant(aGagne);

                }
            }
        }

        return null;
    }
    public String affichageGagnant(Boolean aGagne, Niveau newState) {
        String etatNoeud;
        if (aGagne) {
            res = listeCmd;
        } else {
            Noeud noeud;
            etatNoeud = newState.valeurChaine();
        }
    }


    public DictionnaireChaine<String, Noeud> getConfigPossible() {
        return configPossible;
    }

    public void setConfigPossible(DictionnaireChaine<String, Noeud> configPossible) {
        this.configPossible = configPossible;
    }

    public Niveau getState() {
        return state;
    }

    public void setState(Niveau state) {
        this.state = state;
    }

    public ListeTableau<Noeud> getChilds() {
        return childs;
    }

    public void setChilds(ListeTableau<Noeud> childs) {
        this.childs = childs;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getListeCmd() {
        return listeCmd;
    }

    public void setListeCmd(String listeCmd) {
        this.listeCmd = listeCmd;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Noeud noeud = (Noeud) o;
        return Objects.equals(configPossible, noeud.configPossible) && Objects.equals(state, noeud.state) && Objects.equals(childs, noeud.childs) && Objects.equals(cmd, noeud.cmd) && Objects.equals(res, noeud.res) && Objects.equals(listeCmd, noeud.listeCmd) && Objects.equals(visited, noeud.visited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configPossible, state, childs, cmd, res, listeCmd, visited);
    }
}
