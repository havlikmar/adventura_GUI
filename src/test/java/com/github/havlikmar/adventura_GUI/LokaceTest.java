package com.github.havlikmar.adventura_GUI;

import org.junit.Before;
import org.junit.Test;

import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída LokaceTest slouží ke komplexnímu otestování
 * třídy Lokace a třídy prikazJdi
 *
 * @author     Jarmila Pavlíčková, Jan Říha, upravil Martin Havlík
 * @version    LS 2016/2017 (upraveno 19.5.2017)
 */
public class LokaceTest {
    private Hra hra1;

    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @Before
    public void setUp() {
        hra1 = new Hra();
    }

    /**
     * Testuje, zda jsou správně nastaveny průchody mezi prostory hry. Prostory
     * nemusí odpovídat vlastní hře.
     * Zároveň se testuje, že nejde vejít do zamčené a nedostupné lokace.
     */
    @Test
    public  void testLzeProjit() {      
        Lokace lokace1 = new Lokace("hala", "vstupní hala budovy VŠE na Jižním městě",true, 247.00, 308.00);
        Lokace lokace2 = new Lokace("bufet", "bufet, kam si můžete zajít na svačinku",true, 247.00, 308.00);
        Lokace lokace3 = new Lokace("lokace3", "je nedostupná", false, 247.00, 308.00);
        Lokace lokace4 = new Lokace("lokace4", "je zamčená", true, "klíč1", 247.00, 308.00);
        
        lokace1.setVychod(lokace2);
        lokace2.setVychod(lokace1);
        lokace1.setVychod(lokace3);
        lokace1.setVychod(lokace4);
        
        assertEquals(lokace2, lokace1.vratSousedniLokaci("bufet"));
        assertEquals(null, lokace2.vratSousedniLokaci("pokoj"));
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        assertEquals(hra1.zpracujPrikaz("jdi lokace3"), "Do lokace: lokace3 se odsud jít nedá!");
        assertEquals(hra1.zpracujPrikaz("jdi lokace4"), "Lokace: lokace4 je zamčená. K jejímu odemčení potřebuješ klíč");
    }
}