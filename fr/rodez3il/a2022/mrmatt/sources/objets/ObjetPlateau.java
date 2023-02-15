package fr.rodez3il.a2022.mrmatt.sources.objets;

import fr.rodez3il.a2022.mrmatt.sources.Niveau;

public abstract class ObjetPlateau {

	/**
	 * 
	 * @return Renvoie le caractère d'affichage de l'objet.
	 */
	public abstract char afficher();
	
	/**
	 * Fabrique des objets
	 * @param chr le symbole à produire
	 * @return la classe ObjetPlateau correspondante
	 */
	public static ObjetPlateau depuisCaractere(char chr) {
		ObjetPlateau nouveau = null;
		switch(chr) {
		case '-':
			nouveau = new Herbe();
			break;
		case '+':
			nouveau = new Pomme();
			break;
		case '*':
			nouveau = new Rocher();
			break;
		case ' ':
			nouveau = new Vide();
			break;
		case '#':
			nouveau = new Mur();
			break;
		case 'H':
			nouveau = new Joueur();
			break;
		}
		return nouveau;
	}
	
	/**
	 * Indique si la case est Vide
	 * @return
	 */
	public boolean estVide() {
		return false;
	}
	
	/**
	 * Indique si le joueur peut marcher sur la case
	 * @return
	 */
	public boolean estMarchable() {
		return false;
	}
	
	/**
	 * Indique si l'objet est poussable
	 * @return
	 */
	public boolean estPoussable() {
		return false;
	}
		
	/**
	 * Fonction toString
	 */
	@Override
	public String toString() {
		return Character.toString(this.afficher());
	}
	
	/**
	 * Indique si l'objet est glissant
	 */
	
	public boolean estGlissant() {
		return false;
	}

	/**
	 * Patron visiteur : appelle niveau.etatSuivantVisiteur([Objet], x, y)
	 * pour respecter le polymorphisme lors du calcul de l'état suivant.
	 * 
	 * Fonction laissée vide (*volontairement*), à spécialiser dans les sous-classes.
	 * 
	 * @param niveau
	 * @param x
	 * @param y
	 */
	public void visiterPlateauCalculEtatSuivant(Niveau niveau, int x, int y) {
	}
	
}
