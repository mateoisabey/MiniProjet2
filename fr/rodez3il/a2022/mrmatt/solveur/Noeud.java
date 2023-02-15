package fr.rodez3il.a2022.mrmatt.solveur;

import fr.rodez3il.a2022.mrmatt.solveur.structures.DictionnaireChaine;
import fr.rodez3il.a2022.mrmatt.solveur.structures.ListeTableau;

public class Noeud {

    private DictionnaireChaine<String, Noeud> configPossible;


    private Noeud state;

    //fils ca sera un tableau de noeud 4 position (haut, bas, Gauche, Droite)
    private ListeTableau<Noeud> childs ;

    private String cmd;

    private String res = null ;
    private String nextCmd;

    private Boolean visited;

    public Noeud(DictionnaireChaine<String, Noeud> configPossible, Noeud state, String cmd, String res, String nextCmd) {
        this.configPossible = configPossible;
        this.state = state;
        this.childs = new ListeTableau<>();
        this.cmd = cmd;
        this.res = res;
        this.nextCmd = nextCmd;
        this.visited = false;
    }
}
