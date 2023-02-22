package fr.rodez3il.a2022.mrmatt.solveur;

import fr.rodez3il.a2022.mrmatt.solveur.structures.DictionnaireChaine;
import fr.rodez3il.a2022.mrmatt.solveur.structures.ListeTableau;
import fr.rodez3il.a2022.mrmatt.sources.Niveau;

public class Solveur {

	/**
	 * permet de renvoyer les solutions (Une liste de commande
	 * @param niveau
	 * @return string
	 */
	public static String trouverSolution(Niveau niveau) {

		DictionnaireChaine<String, Noeud> configConnu = new DictionnaireChaine<>();
		Noeud noeud = new Noeud(configConnu, niveau, "");
		ListeTableau<Noeud> aTraiter = new ListeTableau<>();
		ListeTableau<Noeud> traite = new ListeTableau<>();
		aTraiter.ajouter(noeud);
		String solution = null;

		// on reste dans la boucle tant qu'on a pas la solution
		while (solution == null){
			Noeud n = aTraiter.enlever(0);
			traite.ajouter(n);
			solution = n.calculerFils();

			if(solution == null) {
				ListeTableau<Noeud> listeChilds = n.getChilds();
				parcoursListeChils(listeChilds,  aTraiter,  traite );
			}

		}
		return solution;
	}


	/**
	 * permet de parcourir la listeTableau de Noeud
	 * @param listeChilds
	 * @param aTraiter
	 * @param traite
	 */
	private static void parcoursListeChils(ListeTableau<Noeud> listeChilds, ListeTableau<Noeud> aTraiter, ListeTableau<Noeud> traite) {
		for(int i = 0; i < listeChilds.taille(); i++) {
			Noeud childs = listeChilds.element(i);
			if(!aTraiter.contient(childs))
				aTraiter.ajouter(childs);
		}
	}

	public static void main(String[] args) {
		String solution = trouverSolution(new Niveau("niveaux/1rocher.txt"));
		if (solution == null) {
			System.out.println("Pas de solution.");
		} else {
			System.out.println("Une solution est : " + solution);
		}
	}

}
