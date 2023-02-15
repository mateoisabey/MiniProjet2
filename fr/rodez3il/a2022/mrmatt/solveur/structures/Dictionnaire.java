package fr.rodez3il.a2022.mrmatt.solveur.structures;

public interface Dictionnaire<C, V> {

	/**
	 * Ajoute un couple <Clé, Valeur> au dictionnaire.
	 * @param cle
	 * @param valeur
	 */
	void inserer(C cle, V valeur);

	/**
	 * Indique s'il existe une clé f dans le dictionnaire
	 * telle que f.equals(cle) est VRAI.
	 * @param cle
	 * @return
	 */
	boolean contient(C cle);

	/**
	 * Renvoie la valeur associée à la clé.
	 * @param cle
	 * @return NULL si la clé n'existe pas.
	 */
	V valeur(C cle);

}
