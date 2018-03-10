/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI;

import org.junit.Before;
import org.junit.Test;

import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PrikazHadejTest slouží ke komplexnímu otestování PrikazHadej
 *
 * @author     Martin Havlík
 * @version    15.5.2017
 */
public class PrikazHadejTest {
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
     * Testuje, zda jsou správně nastaveny parametry pro použití předmětu.
     * Cílem předmětu je jinný předmět
     * Ověří zda jdou předměty použít a zda jsou správně nastaveny paramtry sebrání
     * Předměty, postavy, bytosti nemusí odpovídat vlastní hře, 
     */
    @Test
    public void testLzeHadat() {
        Lokace lokace = hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci("les");
        hra1.getHerniPlan().setAktualniLokace(lokace);
        assertEquals(hra1.zpracujPrikaz("hádej slepice"),"Zde nemůžeš hádat");
        
        hra1.getHerniPlan().setAktualniLokace(lokace.vratSousedniLokaci("řeka"));
        
        assertFalse(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci("druhý_břeh").isDosazitelny());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().obsahujePredmet("povolení_plavby"));
        assertEquals(hra1.zpracujPrikaz("hádej slepice"),"To není řešení.");
        assertFalse(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci("druhý_břeh").isDosazitelny());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().obsahujePredmet("povolení_plavby"));
        assertEquals(hra1.zpracujPrikaz("hádej slepice nic pes slepice zrní nic slepice"),"Správné řešení, tady máš povolení užívat mou loď.");
        assertTrue(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci("druhý_břeh").isDosazitelny());
        assertTrue(hra1.getHerniPlan().getAktualniLokace().obsahujePredmet("povolení_plavby"));
        
    }
}
