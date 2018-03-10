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
 * Testovací třída PrikazMluvTest slouží ke komplexnímu otestování PrikazMluv
 *
 * @author     Martin Havlík
 * @version    12.5.2017
 */
public class PrikazMluvTest {
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
     * Testuje, zda jsou správně nastaveny parametry pro mluvení s postavou.
     * Ověří zda jdou splněny podmínky a že se vypíší správné texty.
     * Předměty, postavy, bytosti nemusí odpovídat vlastní hře, 
     */
    @Test
    public void testLzeMluvit() {
        Predmet vec1 = new Predmet("vec1", "popis1", true, true); 
        Predmet vec2 = new Predmet("vec2", "popis2", true, true); 
        Predmet vec3 = new Predmet("vec2", "popis2", true, true); 
        
        Lokace lokace1 = new Lokace("lokace1","",true);
        Lokace lokace2 = new Lokace("lokace2","",true);
        
        Bytost bytost1 = new Bytost("bytost1", "popis1", true);
        
        Postava postava1 = new Postava("postava1", vec1, vec2,"mluvPred.", "mluvPo", "nechci", "chci");
        Postava postava2 = new Postava("postava1", vec1, vec2,"mluvPred", "mluvPo", "nechci", "chci");
        
        lokace1.vlozPredmet(vec1);
        lokace1.vlozPredmet(vec3);
        
        lokace1.vlozBytost(bytost1);
        
        lokace1.vlozPostavu(postava1);
        lokace2.vlozPostavu(postava2);
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        
        hra1.zpracujPrikaz("vezmi vec1");
        
        // nelze mluvit s věcí a bytostí
        assertEquals(hra1.zpracujPrikaz("mluv vec3"),"Postava: vec3 tu není");
        assertEquals(hra1.zpracujPrikaz("mluv bytost1"),"Postava: bytost1 tu není");
        
        // nelze mluvit s postavou, která zde není
        assertEquals(hra1.zpracujPrikaz("mluv postava2"),"Postava: postava2 tu není");
        
        // daná postava zde je, kontrola zda vypíše zprávné texty
        assertEquals(hra1.zpracujPrikaz("mluv postava1"),"mluvPred. Chci za to: vec1");
        hra1.zpracujPrikaz("dej vec1 postava1");
        assertEquals(hra1.zpracujPrikaz("mluv postava1"),"mluvPo");
    }
}
