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
 * Testovací třída PrikazProzkoumejTest slouží ke komplexnímu otestování PrikazProzkoumej
 *
 * @author     Martin Havlík
 * @version    12.5.2017
 */
public class PrikazProzkoumejTest {
    private Hra hra1;
    
    // vytvoření pojmenovaných konstant, důvodem je hláška pmd.
    private static final String POPIS1 = "popis1";

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp() {
        hra1 = new Hra();
    }

    /**
     * Testuje, zda jsou správně nastaveny parametry pro prozkoumání předmětu.
     * Ověří zda jdou prozkoumat různé kombinace předmětů, bytostí, postav, lokací
     * Předměty, postavy, bytosti nemusí odpovídat vlastní hře, 
     */
    @Test
    public void testLzeProzkoumat() {
        Predmet vec1 = new Predmet("vec1", POPIS1, true, true, hra1.getHerniPlan());
        Predmet vec2 = new Predmet("vec2", "popis2", true, false, hra1.getHerniPlan()); 
        Predmet vec3 = new Predmet("vec3", "popis3", false, true, hra1.getHerniPlan()); 
        Predmet vec4 = new Predmet("vec4", "popis4", false, false, hra1.getHerniPlan()); 
        
        Postava postava = new Postava("postava", vec1, vec2,"mluvPred", "mluvPo", "nechci", "chci");
        
        Bytost bytost1 = new Bytost("bytost1", POPIS1, true);
        Bytost bytost2 = new Bytost("bytost2", "popis2", false);

        Lokace lokace1 = new Lokace("lokace1","",true);
        
        lokace1.vlozPredmet(vec1);
        lokace1.vlozPredmet(vec2);
        lokace1.vlozPredmet(vec3);
        lokace1.vlozPredmet(vec4);
        
        lokace1.vlozPostavu(postava);
        
        lokace1.vlozBytost(bytost1);
        lokace1.vlozBytost(bytost2);
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);

        // ověření, že jde prozkoumat různé kombince předmětů
        assertEquals(hra1.zpracujPrikaz("prozkoumej vec1"),POPIS1);
        assertEquals(hra1.zpracujPrikaz("prozkoumej vec2"),"Předmět či bytost vec2 není v batohu ani v aktuální lokaci");
        assertEquals(hra1.zpracujPrikaz("prozkoumej vec3"),"popis3");
        assertEquals(hra1.zpracujPrikaz("prozkoumej vec4"),"Předmět či bytost vec4 není v batohu ani v aktuální lokaci");

        // ověření, že jde prozkoumat různé kombince bytostí
        assertEquals(hra1.zpracujPrikaz("prozkoumej bytost1"), POPIS1);
        assertEquals(hra1.zpracujPrikaz("prozkoumej bytost2"),"Předmět či bytost bytost2 není v batohu ani v aktuální lokaci");
        
        // ověření, že nejde prozkoumat postava
        assertEquals(hra1.zpracujPrikaz("prozkoumej postava"),"Předmět či bytost postava není v batohu ani v aktuální lokaci");
        
        // Prozkoumání lokace
        String popis = hra1.getHerniPlan().getAktualniLokace().dlouhyPopis() + "\n"
                            + hra1.getHerniPlan().getBatoh().vypisBatohu();
                            
        // batoh je prázdný neměl by se vypsat, v testu nelze ošetřit, mělo by se vráti false
        assertNotSame(hra1.zpracujPrikaz("prozkoumej"), popis);
        hra1.zpracujPrikaz("vezmi vec1");
        popis = hra1.getHerniPlan().getAktualniLokace().dlouhyPopis() + "\n"
                            + hra1.getHerniPlan().getBatoh().vypisBatohu();
        assertEquals(hra1.zpracujPrikaz("prozkoumej"), popis);
    }
}