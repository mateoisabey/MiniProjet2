package fr.rodez3il.a2022.mrmatt.sources.objets;

/**
 * Herbe
 * @author proussille
 *
 */
public class Herbe extends ObjetPlateau {

	/**
	 * Symbole
	 */
	@Override
	public char afficher() {
		return '-';
	}

	/**
	 * Est traversable
	 */
	@Override
	public boolean estMarchable() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
