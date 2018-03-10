/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI;

import org.junit.Before;
import org.junit.Test;

import com.github.havlikmar.adventura_GUI.logika.Bytost;
import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;
import com.github.havlikmar.adventura_GUI.logika.Postava;
import com.github.havlikmar.adventura_GUI.logika.Predmet;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PrikazPolozTest slouží ke komplexnímu otestování PrikazPoloz
 *
 * @author     Martin Havlík
 * @version    12.5.2017
 */
public class PrikazPolozTest {
    private Hra hra1;
    
    // vytvoření pojmenovaných konstant, důvodem je hláška pmd.
    private static final String VEC1 = "vec1";
    private static final String VEC2 = "vec2";

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp() {
        hra1 = new Hra();
    }

    /**
     * Testuje, zda jsou správně nastaveny parametry pro položení předmětu.
     * Ověří zda jdou položit různé kombinace předmětů.
     * Předměty, postavy, bytosti nemusí odpovídat vlastní hře, 
     */
    @Test
    public void testLzePolozit() {
        Predmet vec1 = new Predmet(VEC1, "popis1", true, true); 
        Predmet vec2 = new Predmet(VEC2, "popis2", true, true); 
        
        Lokace lokace1 = new Lokace("lokace1","",true);
        
        Bytost bytost1 = new Bytost("bytost1", "popis1", true);
        
        Postava postava = new Postava("postava", vec1, vec2,"mluvPred", "mluvPo", "nechci", "chci");
        
        lokace1.vlozPredmet(vec1);
        lokace1.vlozPredmet(vec2);
        
        lokace1.vlozBytost(bytost1);
        
        lokace1.vlozPostavu(postava);
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        
        hra1.zpracujPrikaz("vezmi vec1");
        assertTrue(hra1.getHerniPlan().getBatoh().obsahujePredmet(VEC1));
  
        // ověření, že jde položit předmět
        assertFalse(hra1.getHerniPlan().getAktualniLokace().obsahujePredmet(VEC1));
        assertEquals(hra1.zpracujPrikaz("polož vec1"),"Položil jsem předmět: vec1");
        assertTrue(hra1.getHerniPlan().getAktualniLokace().obsahujePredmet(VEC1));
        assertFalse(hra1.getHerniPlan().getBatoh().obsahujePredmet(VEC1));
        
        // oveření že nejde položiz předmět, který nemám
        assertTrue(hra1.getHerniPlan().getAktualniLokace().obsahujePredmet(VEC2));
        assertEquals(hra1.zpracujPrikaz("polož vec2"),"Předmět: vec2 nemám v batohu");
        assertTrue(hra1.getHerniPlan().getAktualniLokace().obsahujePredmet(VEC2));
        assertFalse(hra1.getHerniPlan().getBatoh().obsahujePredmet(VEC2));
        
        // Nejde položit bytost
        assertFalse(hra1.getHerniPlan().getAktualniLokace().obsahujePredmet("bytost1"));
        assertEquals(hra1.zpracujPrikaz("polož bytost1"),"Předmět: bytost1 nemám v batohu");
        
        // Nejde položit postavu
        assertFalse(hra1.getHerniPlan().getBatoh().obsahujePredmet("postava"));
        assertEquals(hra1.zpracujPrikaz("polož postava"),"Předmět: postava nemám v batohu");
    }
}