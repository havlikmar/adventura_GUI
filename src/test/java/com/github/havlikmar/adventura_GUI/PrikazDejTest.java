/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI;

import org.junit.Before;
import org.junit.Test;

import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;
import com.github.havlikmar.adventura_GUI.logika.Postava;
import com.github.havlikmar.adventura_GUI.logika.Predmet;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PrikazVezmiTest slouží ke komplexnímu otestování PrikazDej
 *
 * @author     Martin Havlík
 * @version    10.5.2017
 */
public class PrikazDejTest {
    private Hra hra1;

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp() {
        hra1 = new Hra();
    }

    /**
     * Testuje, zda jsou správně nastaveny parametry pro výměnu předmětů
     * Ověří zda jdou dát postavám různé kombinace předmětů
     * Předměty, postavy, bytosti nemusí odpovídat vlastní hře, 
     */
    @Test
    public void testLzeDat() {
        Predmet vec1 = new Predmet("vec1", "", true, true,"neni potřeba"); // tento predmet chce
        Predmet vec2 = new Predmet("vec2", "", true, true,"neni potřeba"); // tento predmet dá
        Predmet vec3 = new Predmet("vec3", "", true, true,"neni potřeba"); 
        
        Postava postava = new Postava("postava", vec1, vec2,"mluvPred", "mluvPo", "nechci", "chci");

        Lokace lokace1 = new Lokace("lokace1","",true, 247.00, 308.00);
        
        lokace1.vlozPredmet(vec1);
        lokace1.vlozPredmet(vec3);
        
        lokace1.vlozPostavu(postava);
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        hra1.zpracujPrikaz("vezmi vec1");
        hra1.zpracujPrikaz("vezmi vec3");
        
        // postava tento předmět nechce
        assertEquals(hra1.zpracujPrikaz("dej vec3 postava"),"nechci");
        assertTrue(hra1.getHerniPlan().getBatoh().obsahujePredmet("vec3"));
        
        // postava tento předmět chce
        assertEquals(hra1.zpracujPrikaz("dej vec1 postava"),"chci");
        assertFalse(hra1.getHerniPlan().getBatoh().obsahujePredmet("vec1"));
        assertTrue(hra1.getHerniPlan().getBatoh().obsahujePredmet("vec2"));
        
        // výměna již proběhla
        assertEquals(hra1.zpracujPrikaz("dej vec3 postava"),"Výměna již proběhla");
    }
}