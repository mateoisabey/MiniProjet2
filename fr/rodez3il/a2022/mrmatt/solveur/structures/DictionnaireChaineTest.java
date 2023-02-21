package fr.rodez3il.a2022.mrmatt.solveur.structures;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DictionnaireChaineTest {

    private DictionnaireChaine<String, Integer> dico;


    @Before
    public void setUp() {
        dico = new DictionnaireChaine<>();
    }

    @Test
    public void testInserer() {
        dico.inserer("a", 1);
        dico.inserer("b", 2);
        dico.inserer("c", 3);
        dico.inserer("d", 4);
        assertEquals(4, dico.taille());
    }

    @Test
    public void testContient() {
        dico.inserer("a", 1);
        dico.inserer("b", 2);
        assertTrue(dico.contient("a"));
        assertFalse(dico.contient("c"));
    }

    @Test
    public void testValeur() {
        dico.inserer("a", 1);
        dico.inserer("b", 2);
        assertEquals((Integer) 1, dico.valeur("a"));
        assertNull(dico.valeur("c"));
    }




}