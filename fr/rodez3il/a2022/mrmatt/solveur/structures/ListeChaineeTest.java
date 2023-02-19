package fr.rodez3il.a2022.mrmatt.solveur.structures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ListeChaineeTest {

    @Test
    void testAjouter() {
        ListeChainee<Integer> liste = new ListeChainee<>();
        liste.ajouter(1);
        liste.ajouter(2);
        liste.ajouter(3);
        assertEquals(3, liste.taille());
    }

    @Test
    void testElement() {
        ListeChainee<Integer> liste = new ListeChainee<>();
        liste.ajouter(1);
        liste.ajouter(2);
        liste.ajouter(3);
        assertEquals(2, liste.element(1));
    }

    @Test
    void testEnlever() {
        ListeChainee<Integer> liste = new ListeChainee<>();
        liste.ajouter(1);
        liste.ajouter(2);
        liste.ajouter(3);
        liste.enlever(1);
        assertEquals(2, liste.taille());
        assertFalse(liste.contient(2));
    }

    @Test
    void testTaille() {
        ListeChainee<String> liste = new ListeChainee<>();
        assertEquals(0, liste.taille());
        liste.ajouter("Hello");
        assertEquals(1, liste.taille());
        liste.enlever(0);
        assertEquals(0, liste.taille());
    }

    @Test
    void testEstVide() {
        ListeChainee<String> liste = new ListeChainee<>();
        assertTrue(liste.estVide());
        liste.ajouter("Test");
        assertFalse(liste.estVide());
        liste.enlever(0);
        assertTrue(liste.estVide());
    }

    @Test
    void testContient() {
        ListeChainee<String> liste = new ListeChainee<>();
        liste.ajouter("Hello");
        liste.ajouter("World");
        liste.ajouter("Test");
        assertTrue(liste.contient("World"));
        assertFalse(liste.contient("Java"));
    }

    @Test
    void testEquals() {
        ListeChainee<Integer> liste1 = new ListeChainee<>();
        liste1.ajouter(1);
        liste1.ajouter(2);
        liste1.ajouter(3);

        ListeChainee<Integer> liste2 = new ListeChainee<>();
        liste2.ajouter(1);
        liste2.ajouter(2);
        liste2.ajouter(3);

        ListeChainee<Integer> liste3 = new ListeChainee<>();
        liste3.ajouter(3);
        liste3.ajouter(2);
        liste3.ajouter(1);

        assertNotEquals(liste1, liste3);
    }
}