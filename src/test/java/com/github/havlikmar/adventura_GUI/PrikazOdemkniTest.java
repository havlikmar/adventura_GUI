/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI;

import org.junit.Before;
import org.junit.Test;

import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;
import com.github.havlikmar.adventura_GUI.logika.Predmet;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PrikazOdemkniTest slouží ke komplexnímu otestování PrikazOdemkni
 *
 * @author     Martin Havlík
 * @version    15.5.2017
 */
public class PrikazOdemkniTest {
    private Hra hra1;
    
    // vytvoření pojmenovaných konstant, důvodem je hláška pmd.
    private static final String LOKACE2 = "lokace2";
    private static final String POPRVE = "poprve";
    private static final String VEC2 = "vec2";
    private static final String VEC4 = "vec4";

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp() {
        hra1 = new Hra();
    }

    /**
     * Testuje, zda jsou správně nastaveny parametry pro použití předmětu.
     * Cílem předmětu je jinný předmět
     * Ověří zda jdou předměty použít a zda jsou správně nastaveny paramtry sebrání
     * Předměty, postavy, bytosti nemusí odpovídat vlastní hře, 
     */
    @Test
    public void testLzeodemknout() {
        Predmet vec1 = new Predmet("vec1", "", true, true, "lokace1", LOKACE2, POPRVE, "podruhe"); //klíč lokace
        Predmet vec2 = new Predmet(VEC2, "", true, false); // věc co dostanu
        Predmet vec3 = new Predmet("vec3", "", true, true); // klíč předmět
        Predmet vec4 = new Predmet(VEC4, "", false, "vec3", VEC2, POPRVE, "podruhe", true); // co odemkne
        
        Lokace lokace1 = new Lokace("lokace1", "", true);
        Lokace lokace2 = new Lokace(LOKACE2, "", true, "vec1");
        
        lokace1.setVychod(lokace2);
        
        lokace1.vlozPredmet(vec1);
        lokace1.vlozPredmet(vec2);
        lokace1.vlozPredmet(vec3);
        lokace1.vlozPredmet(vec4);
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        
        // Jde odemknout lokaci
        assertTrue(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LOKACE2).isZamcena());
        assertEquals(hra1.zpracujPrikaz("odemkni lokace3"), "Předmět, či lokace: lokace3 nemůžeš odemknout. Potřebuješ klíč"); 
        assertTrue(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LOKACE2).isZamcena());
        assertEquals(hra1.zpracujPrikaz("odemkni lokace2"), "Předmět, či lokace: lokace2 nemůžeš odemknout. Potřebuješ klíč"); 
        hra1.zpracujPrikaz("vezmi vec1");    
        assertTrue(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LOKACE2).isZamcena());
        assertEquals(hra1.zpracujPrikaz("odemkni lokace2"), POPRVE); 
        assertFalse(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LOKACE2).isZamcena());
        
        // Jde odemknout předmět
        hra1.zpracujPrikaz("vezmi vec3");   
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet(VEC4).isZamknuty());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet(VEC2).isViditelny());
        assertEquals(hra1.zpracujPrikaz("odemkni vec5"),"Předmět, či lokace: vec5 nemůžeš odemknout. Potřebuješ klíč");
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet(VEC4).isZamknuty());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet(VEC2).isViditelny());
        assertEquals(hra1.zpracujPrikaz("odemkni vec4"), POPRVE); 
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet(VEC4).isZamknuty());
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet(VEC2).isViditelny());
        assertEquals(hra1.zpracujPrikaz("odemkni vec4"),"podruhe"); 
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet(VEC4).isZamknuty());
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet(VEC2).isViditelny());
    }
}