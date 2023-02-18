package fr.rodez3il.a2022.mrmatt.solveur.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionnaireChaineTest {

    private DictionnaireChaine<String, Integer> dico;

    @BeforeEach
    void setUp() {
        dico = new DictionnaireChaine<>();
        dico.inserer("un", 1);
        dico.inserer("deux", 2);
        dico.inserer("trois", 3);
        dico.inserer("quatre", 4);
    }

    @Test
    void testInserer() {

        assertEquals(4, dico.taille());
        assertEquals(4, dico.valeur("4"));
    }

    @Test
    void testContient() {
        assertTrue(dico.contient("un"));
        assertFalse(dico.contient("quatre"));
    }

    @Test
    void testValeur() {
        assertEquals(1, dico.valeur("un"));
        assertEquals(3, dico.valeur("trois"));
        assertNull(dico.valeur("quatre"));
    }


}
