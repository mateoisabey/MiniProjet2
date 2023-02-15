package fr.rodez3il.a2022.mrmatt.sources;

import fr.rodez3il.a2022.mrmatt.sources.objets.EtatRocher;
import fr.rodez3il.a2022.mrmatt.sources.objets.ObjetPlateau;
import fr.rodez3il.a2022.mrmatt.sources.objets.Pomme;
import fr.rodez3il.a2022.mrmatt.sources.objets.Rocher;
import fr.rodez3il.a2022.mrmatt.sources.objets.Vide;

public class Niveau implements Cloneable {

	// Les objets sur le plateau du niveau
	private ObjetPlateau[][] plateau;
	// Position du joueur
	private int joueurX;
	private int joueurY;
	// Nombre de déplacements déjà réalisés depuis le début de la partie
	private int nombreDeplacements;
	// Pommes restantes à collecter
	private int pommesRestantes;
	// Drapeau d'états
	private boolean perdu;
	private boolean gagne;
	private boolean intermediaire;
	private boolean annuleExiste;
	// Sauvegarde d'un état précédent
	private Niveau etatPrecedent;

	/**
	 * Constructeur interne privé.
	 * 
	 * @author proussille
	 */
	private Niveau() {
		plateau = new ObjetPlateau[0][0];
		intermediaire = false;
		pommesRestantes = 0;
		nombreDeplacements = 0;
		annuleExiste = false;
		etatPrecedent = null;
	}

	/**
	 * Constructeur public : crée un niveau depuis un fichier.
	 * 
	 * @param chemin Le fichier à charger
	 * @author proussille
	 */
	public Niveau(String chemin) {
		this();
		chargerNiveau(chemin);
	}

	/**
	 * Crée une copie de l'objet actuel.
	 * 
	 * @return Un objet en copie profonde de l'objet actuel (similaire à clone())
	 * @author proussille
	 */
	public Niveau copier() {
		Niveau copie = new Niveau();
		copie.plateau = Utils.cloneTableau(plateau);
		copie.joueurX = joueurX;
		copie.joueurY = joueurY;
		copie.nombreDeplacements = nombreDeplacements;
		copie.pommesRestantes = pommesRestantes;
		copie.perdu = perdu;
		copie.gagne = gagne;
		copie.intermediaire = intermediaire;
		copie.annuleExiste = false;
		copie.etatPrecedent = null;
		return copie;
	}

	/**
	 * Charge les valeurs des attributs depuis un niveau précédent.
	 * 
	 * @param precedent Le niveau à restaurer.
	 * @author proussille
	 */
	private void restaurer(Niveau precedent) {
		plateau = Utils.cloneTableau(precedent.plateau);
		joueurX = precedent.joueurX;
		joueurY = precedent.joueurY;
		nombreDeplacements = precedent.nombreDeplacements;
		pommesRestantes = precedent.pommesRestantes;
		perdu = precedent.perdu;
		gagne = precedent.gagne;
		intermediaire = precedent.intermediaire;
		annuleExiste = false;
		etatPrecedent = null;
	}

	/**
	 * Échange les cases de [sourceX][sourceY] et [destinationX][destinationY].
	 * 
	 * @param sourceX
	 * @param sourceY
	 * @param destinationX
	 * @param destinationY
	 * @author proussille
	 */
	private void echanger(int sourceX, int sourceY, int destinationX, int destinationY) {
		ObjetPlateau temp = plateau[destinationX][destinationY];
		plateau[destinationX][destinationY] = plateau[sourceX][sourceY];
		plateau[sourceX][sourceY] = temp;
	}

	/**
	 * Charge un niveau depuis un fichier.
	 * 
	 * @param fichier Le chemin du fichier à charger.
	 * @author proussille
	 */
	private void chargerNiveau(String fichier) {
		String contenu = Utils.lireFichier(fichier);
		String[] lignes = contenu.split("\n");
		int cols = Integer.valueOf(lignes[0]);
		int rows = Integer.valueOf(lignes[1]);
		plateau = new ObjetPlateau[cols][rows];
		int x = 0;
		int y = 0;
		for (int l = 2; l < lignes.length; l++) {
			for (int c = 0; c < lignes[l].length(); c++) {
				char chr = lignes[l].charAt(c);
				ObjetPlateau element = null;
				if ("*-+# H".indexOf(chr) >= 0) {
					element = ObjetPlateau.depuisCaractere(chr);
				}
				if (chr == 'H') {
					joueurX = x;
					joueurY = y;
				}
				if (chr == '+') {
					pommesRestantes++;
				}
				plateau[x++][y] = element;
			}
			x = 0;
			y++;
		}

	}

	/**
	 * Produit une sortie du niveau sur la sortie standard.
	 * 
	 * @author proussille
	 */
	public void afficher() {
		for (int j = 0; j < plateau[0].length; j++) {
			for (int i = 0; i < plateau.length; i++) {
				System.out.print(plateau[i][j].afficher());
			}
			System.out.println();
		}
		System.out.println("Pommes restantes : " + pommesRestantes);
		System.out.println("Déplacements : " + nombreDeplacements);
	}

	/**
	 * Réalise le patron Visiteur pour un objet Pomme : compte le nombre de pommes
	 * restantes.
	 * 
	 * @param p Objet en question
	 * @param x Position x de l'objet
	 * @param y Position y de l'objet
	 * 
	 * @author proussille
	 */
	public void etatSuivantVisiteur(Pomme p, int x, int y) {
		pommesRestantes++;
	}

	/**
	 * Réalise le patron Visiteur pour un objet Rocher : calcule la position du
	 * rocher en question à l'étape suivante (fixe <=> chute, chute => bas, chute =>
	 * bas/gauche/droit, chute => perdu)
	 * 
	 * @param r Objet en question
	 * @param x Position x de l'objet
	 * @param y Position y de l'objet
	 * 
	 * @author proussille
	 */
	public void etatSuivantVisiteur(Rocher r, int x, int y) {
		switch (r.getEtat()) {
		case CHUTE:
			// Game over
			if (x == joueurX && y + 1 == joueurY) {
				perdu = true;
			} else if (y < plateau[x].length - 1) { // on n'est pas en bas
				if (plateau[x][y + 1].estVide()) {
					echanger(x, y, x, y + 1);
				} else if (plateau[x][y + 1].estGlissant()) {
					// si c'est vide à gauche
					// et à gauche + bas
					if (x > 0 && plateau[x - 1][y].estVide() && plateau[x - 1][y + 1].estVide()) {
						echanger(x, y, x - 1, y + 1);
					}
					// si c'est vide à droite
					// et à droite + bas
					else if (x < plateau.length - 1 && plateau[x + 1][y].estVide() && plateau[x + 1][y + 1].estVide()) {
						echanger(x, y, x + 1, y + 1);
					}
					// sinon, on s'arrête
					else {
						r.setEtat(EtatRocher.FIXE);
					}
				}
				// sinon, on s'arrête
				else {
					r.setEtat(EtatRocher.FIXE);
				}
			} else {
				// Si un rocher en chute ne peut plus tomber,
				// il passe en état "fixe"
				r.setEtat(EtatRocher.FIXE);
			}
			break;
		case FIXE:
			// Si un rocher fixe n'a rien en dessous,
			// il passe en état "chute"
			if (y < plateau[x].length - 1 && plateau[x][y + 1].estVide())
				r.setEtat(EtatRocher.CHUTE);
			break;
		}
		intermediaire = r.getEtat() == EtatRocher.CHUTE ? true : intermediaire;
	}

	/**
	 * Calcule l'état suivant du niveau.
	 * 
	 * @author proussille
	 */
	public void etatSuivant() {
		intermediaire = false;
		pommesRestantes = 0;
		for (int x = plateau.length - 1; x >= 0; x--) {
			for (int y = plateau[x].length - 1; y >= 0; y--) {
				plateau[x][y].visiterPlateauCalculEtatSuivant(this, x, y);
			}
		}
		gagne = (pommesRestantes == 0);
	}

	/**
	 * Indique si la partie est toujours en cours.
	 * 
	 * @author proussille
	 * @return
	 */
	public boolean enCours() {
		// tant qu'on n'a ni gagné, ni perdu
		return !(gagne || perdu);
	}

	/**
	 * Indique s'il est possible de réaliser le déplacement (+deltaX,+deltaY) depuis
	 * la position du joueur : - teste si le déplacement est dans les limites du
	 * niveau - teste si la case cible est marchable - teste si un objet peut être
	 * déplacé latéralement, et s'il y a bien la place de le déplacer derrière.
	 * 
	 * @param deltaX
	 * @param deltaY
	 * @return true si le déplacement est valide, false sinon
	 * @author proussille
	 */
	private boolean deplacementPossible(int deltaX, int deltaY) {
		boolean retour = false;
		int cibleX = joueurX + deltaX;
		int cibleY = joueurY + deltaY;
		if ((deltaX == 0 && deltaY == 0) || cibleX > plateau.length - 1 || cibleX < 0 || cibleY > plateau[0].length - 1
				|| cibleY < 0) {
			// Coup impossible : la cible est en dehors du plateau.
		} else {
			if (plateau[cibleX][cibleY].estMarchable()) {
				retour = true;
			}
			if (plateau[cibleX][cibleY].estPoussable() && deltaY == 0) {
				int objetDerriereX = joueurX + deltaX * 2;
				if ((objetDerriereX >= 0 || objetDerriereX <= plateau.length - 1)
						&& plateau[objetDerriereX][cibleY].estVide()) {
					retour = true;
				}
			}
		}
		return retour;
	}

	/**
	 * Réalise le déplacement du joueur selon (+dX,+dy).
	 * 
	 * @require deplacementPossible(deltaX, deltaY) == true
	 * @param deltaX
	 * @param deltaY
	 * @author proussille
	 */
	private void deplacer(int deltaX, int deltaY) {
		int cibleX = joueurX + deltaX;
		int cibleY = joueurY + deltaY;
		nombreDeplacements++;
		if (plateau[cibleX][cibleY].estMarchable()) {
			if (!plateau[cibleX][cibleY].estVide()) {
				plateau[cibleX][cibleY] = new Vide();
			}
			echanger(cibleX, cibleY, joueurX, joueurY);
		} else if (plateau[cibleX][cibleY].estPoussable()) {
			int objetDerriereX = joueurX + deltaX * 2;
			// ici, on est dans le cas [joueur][rocher][vide]
			// on permute vide x rocher
			echanger(cibleX, cibleY, objetDerriereX, cibleY);
			// puis vide x joueur
			echanger(cibleX, cibleY, joueurX, joueurY);
		}
		joueurX = cibleX;
		joueurY = cibleY;
	}

	/**
	 * Récupère une commande et agit en conséquence.
	 * 
	 * @param c La commande
	 * @return true si le plateau a changé, false sinon.
	 * @author proussille
	 */
	public boolean jouer(Commande c) {
		boolean retour = true;
		if (c == Commande.ANNULER) {
			if (annuleExiste) {
				restaurer(etatPrecedent);
				System.out.println("Le coup a été annulé.");
			} else {
				System.out.println("Impossible d'annuler !");
				retour = false;
			}
		} else if (c == Commande.QUITTER) {
			retour = false;
			perdu = true;
		} else {
			int deltaX = 0;
			int deltaY = 0;
			if (c == Commande.DROITE && joueurX < plateau.length - 1) {
				deltaX = 1;
			} else if (c == Commande.HAUT && joueurY > 0) {
				deltaY = -1;
			} else if (c == Commande.BAS && joueurY < plateau[joueurX].length - 1) {
				deltaY = 1;
			} else if (c == Commande.GAUCHE && joueurX > 0) {
				deltaX = -1;
			}
			if (deplacementPossible(deltaX, deltaY)) {
				annuleExiste = true;
				etatPrecedent = copier();
				deplacer(deltaX, deltaY);
			} else {
				retour = false;
			}
		}
		return retour;
	}

	/**
	 * Affiche l'état final (gagné ou perdu) une fois le jeu terminé.
	 * 
	 * @author proussille
	 */
	public void afficherEtatFinal() {
		if (gagne) {
			System.out.println("VOUS AVEZ GAGNE !");
		} else if (perdu) {
			System.out.println("VOUS AVEZ PERDU !");
		}
	}

	/**
	 * Indique si l'état actuel est un état transitoire (animation)
	 * 
	 * @return
	 * @author proussille
	 */
	public boolean estIntermediaire() {
		return enCours() && intermediaire;
	}

	///////////////////////////////////////////////////////////////////////

	// Méthodes supplémentaires pour le solveur.

	/**
	 * Indique si le déplacement passé en paramètre est possible.
	 * 
	 * @param direction
	 * @return VRAI ssi le déplacement est possible.
	 */
	public boolean deplacementPossible(Commande direction) {
		boolean result;
		switch (direction) {
		case BAS:
			result = deplacementPossible(0, 1);
			break;
		case HAUT:
			result = deplacementPossible(0, -1);
			break;
		case DROITE:
			result = deplacementPossible(1, 0);
			break;
		case GAUCHE:
			result = deplacementPossible(-1, 0);
			break;
		default:
			result = false;
		}
		return result;
	}

	/**
	 * Réalise le déplacement passé en paramètre sur le niveau.
	 * @param direction Le déplacement à réaliser.
	 */
	public void deplacer(Commande direction) {
		jouer(direction);
		calcule();
	}

	/**
	 * Calcule l'état fixe suivant (saute les états intermédiaires).
	 */
	public void calcule() {
		etatSuivant();
		while (estIntermediaire()) {
			etatSuivant();
		}
	}

	/**
	 * Accesseur pour savoir si le joueur a gagné.
	 */
	public boolean estGagnant() {
		return gagne;
	}

	/**
	 * Renvoie la valeur du niveau sous forme de chaîne (pour déterminer les doublons).
	 */
	public String valeurChaine() {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < plateau[0].length; j++) {
			for (int i = 0; i < plateau.length; i++) {
				sb.append(plateau[i][j].afficher());
			}
			sb.append('$');
		}
		return sb.toString();
	}
}
