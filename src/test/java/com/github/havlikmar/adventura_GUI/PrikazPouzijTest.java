/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI;

import org.junit.Before;
import org.junit.Test;

import com.github.havlikmar.adventura_GUI.logika.Bytost;
import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;
import com.github.havlikmar.adventura_GUI.logika.Predmet;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PrikazPouzijTest slouží ke komplexnímu otestování PrikazPouzij
 *
 * @author     Martin Havlík
 * @version    19.5.2017
 */
public class PrikazPouzijTest {
    private Hra hra1;
    
    // vytvoření pojmenovaných konstant, důvodem je hláška pmd.
    private static final String LOKACE1 = "lokace1";
    private static final String LOKACE2 = "lokace2";
    private static final String POPRVE = "poprve";
    private static final String PODRUHE = "podruhé";
    private static final String KLADA = "kláda";
    private static final String VEC3 = "vec3";
    private static final String LOKACE3 = "lokace3";
    private static final String POUZIJ_VEC1 = "použij vec1";
    private static final String BYTOST = "bytost";

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
    public void testLzePouzitVec() {
        Predmet vec1 = new Predmet("vec1", "", true, true, LOKACE1, VEC3, POPRVE, PODRUHE, hra1.getHerniPlan()); // v této lokaci jde použít
        Predmet vec2 = new Predmet("vec2", "", true, true, LOKACE2, VEC3, POPRVE, PODRUHE, hra1.getHerniPlan()); // v této lokaci nejde použít
        Predmet vec3 = new Predmet(VEC3, "", true, false, hra1.getHerniPlan()); // věc co dostanu
        Predmet vec4 = new Predmet(KLADA, "", false, true, LOKACE1, LOKACE2, POPRVE, PODRUHE, hra1.getHerniPlan()); // kontrola klády
        Predmet vec5 = new Predmet("klíč", "", true, true, hra1.getHerniPlan()); // klíč
        
        Lokace lokace1 = new Lokace(LOKACE1,"",true); // současná lokace
        Lokace lokace2 = new Lokace(LOKACE2,"",true);
        
        lokace1.setVychod(lokace2);
        
        lokace1.vlozPredmet(vec1);
        lokace1.vlozPredmet(vec2);
        lokace1.vlozPredmet(vec3);
        lokace1.vlozPredmet(vec4);
        lokace1.vlozPredmet(vec5);
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        
        hra1.zpracujPrikaz("vezmi vec1");
        hra1.zpracujPrikaz("vezmi vec2");
        hra1.zpracujPrikaz("vezmi vec6");
        
        //nejde použít věc, která jde použít v jinné lokaci
        assertFalse(lokace1.getPredmet(VEC3).isViditelny());
        assertEquals(hra1.zpracujPrikaz("použij vec2"),"Předmět: vec2 zde nemůžeš použít"); 
        assertFalse(lokace1.getPredmet(VEC3).isViditelny());
        
        // jde požít věc ve správné lokaci
        assertFalse(lokace1.getPredmet(VEC3).isViditelny());
        assertEquals(hra1.zpracujPrikaz(POUZIJ_VEC1), POPRVE); 
        assertTrue(lokace1.getPredmet(VEC3).isViditelny());
        assertEquals(hra1.zpracujPrikaz(POUZIJ_VEC1), PODRUHE); 
        assertTrue(lokace1.getPredmet(VEC3).isViditelny());
        
        // nejde pohnout s kládou pokud nemám klíč
        assertTrue(lokace1.getPredmet(KLADA).isViditelny());
        assertEquals(hra1.zpracujPrikaz("použij kláda"),"Aby jsi mohl s předmětem: kláda pohnout musíš mít v batohu klíč"); 
        assertTrue(lokace1.getPredmet(KLADA).isViditelny());
        assertTrue(lokace1.vratSousedniLokaci(LOKACE2).isDosazitelny());
        
        // jde pohnout s kládou pokud mám klíč
        hra1.zpracujPrikaz("vezmi klíč");
        assertTrue(hra1.getHerniPlan().getBatoh().obsahujePredmet("klíč"));
        assertTrue(lokace1.getPredmet(KLADA).isViditelny());
        assertEquals(hra1.zpracujPrikaz("použij kláda"), POPRVE); 
        assertFalse(lokace1.getPredmet(KLADA).isViditelny());
        assertFalse(lokace1.vratSousedniLokaci(LOKACE2).isDosazitelny());
        
        // nejde pohnout znovu, jeskně je zavalená
        assertFalse(lokace1.getPredmet(KLADA).isViditelny());
        assertEquals(hra1.zpracujPrikaz("použij kláda"), PODRUHE); 
        assertFalse(lokace1.getPredmet(KLADA).isViditelny());
        assertFalse(lokace1.vratSousedniLokaci(LOKACE2).isDosazitelny());
    }
        
    /**
     * Testuje, zda jsou správně nastaveny parametry pro použití předmětu.
     * Cílem předmětu jsou lokace, či bytosti
     * Ověří zda jdou předměty použít a zda jsou správně nastaveny paramtry sebrání
     * Předměty, postavy, bytosti nemusí odpovídat vlastní hře, 
     */
    @Test
    public void testLzePouzitOstatni() {
        Predmet vec1 = new Predmet("vec1", "", true, true, LOKACE1, BYTOST, POPRVE, PODRUHE, hra1.getHerniPlan()); // cílem je bytost
        Predmet vec2 = new Predmet("vec2", "", true, true, LOKACE1, LOKACE3, POPRVE, PODRUHE, hra1.getHerniPlan()); 
        
        Lokace lokace1 = new Lokace(LOKACE1, "", true);
        Lokace lokace2 = new Lokace(LOKACE2, "", true);
        Lokace lokace3 = new Lokace(LOKACE3, "", false);
        
        Bytost bytost = new Bytost(BYTOST, "popis");
        
        lokace1.setVychod(lokace2);
        lokace1.setVychod(lokace3);
        
        lokace1.vlozBytost(bytost);
        
        lokace1.vlozPredmet(vec1);
        lokace1.vlozPredmet(vec2);
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        
        hra1.zpracujPrikaz("vezmi vec1");
        hra1.zpracujPrikaz("vezmi vec2");

        // cílem je bytost
        assertTrue(lokace1.getBytost(BYTOST).isViditelny());
        assertEquals(hra1.zpracujPrikaz(POUZIJ_VEC1), POPRVE); 
        assertFalse(lokace1.getBytost(BYTOST).isViditelny());
        assertEquals(hra1.zpracujPrikaz(POUZIJ_VEC1), PODRUHE); 
        assertFalse(lokace1.getBytost(BYTOST).isViditelny());
        hra1.getHerniPlan().setAktualniLokace(lokace2);
        assertEquals(hra1.zpracujPrikaz(POUZIJ_VEC1), "Předmět: vec1 zde nemůžeš použít");
        
        // cílem je lokace
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        assertFalse(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LOKACE3).isDosazitelny());
        assertEquals(hra1.zpracujPrikaz("použij vec2"), POPRVE);
        assertTrue(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LOKACE3).isDosazitelny());
        assertEquals(hra1.zpracujPrikaz("použij vec2"), PODRUHE);
        assertTrue(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LOKACE3).isDosazitelny());
    }
}
