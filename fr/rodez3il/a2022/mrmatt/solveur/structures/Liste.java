package fr.rodez3il.a2022.mrmatt.solveur.structures;

public interface Liste<T> {

	/**
	 * Ajoute un élément à la liste.
	 * @param element l'élément à ajouter
	 */
	void ajouter(T element);

	/**
	 * Indique si la liste est vide.
	 * @return true ssi la liste est vide.
	 */
	boolean estVide();

	/**
	 * Indique la taille de la liste.
	 * @return La taille de la liste.
	 */
	int taille();
	
	/**
	 * Enlève (et retourne) l'élément à la position
	 * i.
	 * @param i la position de l'élément.
	 * @return L'élément qui a été supprimé. 
	 */
	T enlever(int i);
	
	/**
	 * Renvoie l'élément à la position i.
	 * @param i
	 * @return
	 */
	T element(int i);

	/**
	 * Indique s'il existe un élément f dans la liste
	 * tel que f.equals(e) est VRAI.
	 * @param e L'élément à comparer.
	 * @return
	 */
	boolean contient(T e);

}
