package fr.rodez3il.a2022.mrmatt.sources.objets;

public class Vide extends ObjetPlateau {

	@Override
	public char afficher() {
		return ' ';
	}

	@Override
	public boolean estMarchable() {
		return true;
	}
	
	@Override
	public boolean estVide() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
