/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI;

import org.junit.Before;
import org.junit.Test;

import com.github.havlikmar.adventura_GUI.logika.Batoh;
import com.github.havlikmar.adventura_GUI.logika.Bytost;
import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;
import com.github.havlikmar.adventura_GUI.logika.Postava;
import com.github.havlikmar.adventura_GUI.logika.Predmet;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PrikazVezmiTest slouží ke komplexnímu otestování PrikazVezmi
 *
 * @author     Martin Havlík
 * @version    10.5.2017
 */
public class PrikazVezmiTest {
    private Hra hra1;
    private static final String VEC1 = "vec1";
    private static final String VEC2 = "vec2";
    private static final String VEC3 = "vec3";
    private static final String VEC4 = "vec4";
    private static final String VEC5 = "vec5";

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp() {
        hra1 = new Hra();
    }

    /**
     * Testuje, zda jsou správně nastaveny parametry sebrání předmětu.
     * Ověří zda různé předměty, postavy, bytosti jdou vzít. 
     * Předměty, postavy, bytosti nemusí odpovídat vlastní hře, 
     */
    @Test
    public  void testLzeSebrat() {      
        Predmet vec1 = new Predmet(VEC1, "", true, true);
        Predmet vec2 = new Predmet(VEC2, "", true, false);
        Predmet vec3 = new Predmet(VEC3, "", false, true);
        Predmet vec4 = new Predmet(VEC4, "", false, false);
        
        Bytost bytost1 = new Bytost ("bytost1", "", true);
        Bytost bytost2 = new Bytost ("bytost2", "", false);
        
        Postava postava = new Postava("postava", vec1, vec2,"", "", "", "");
        
        Lokace lokace1 = new Lokace("lokace1","",true);
        
        lokace1.vlozPredmet(vec1);
        lokace1.vlozPredmet(vec2);
        lokace1.vlozPredmet(vec3);
        lokace1.vlozPredmet(vec4);
        
        lokace1.vlozBytost(bytost1);
        lokace1.vlozBytost(bytost2);
        
        lokace1.vlozPostavu(postava);
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        Batoh batoh = hra1.getHerniPlan().getBatoh();
        
        assertEquals(hra1.zpracujPrikaz("vezmi vec1"),"Sebral jsem předmět: vec1");  
        assertFalse(lokace1.obsahujePredmet(VEC1));
        assertTrue(batoh.obsahujePredmet(VEC1));
        assertEquals(hra1.zpracujPrikaz("vezmi vec2"),"předmět: vec2 tu není");   
        assertTrue(lokace1.obsahujePredmet(VEC2));
        assertFalse(batoh.obsahujePredmet(VEC2));
        assertEquals(hra1.zpracujPrikaz("vezmi vec3"),"Neumím sebrat nepřenositelný předmět");   
        assertTrue(lokace1.obsahujePredmet(VEC3));
        assertFalse(batoh.obsahujePredmet(VEC3));
        assertEquals(hra1.zpracujPrikaz("vezmi vec4"),"předmět: vec4 tu není");   
        assertTrue(lokace1.obsahujePredmet(VEC4));
        assertFalse(batoh.obsahujePredmet(VEC4));
        
        assertEquals(hra1.zpracujPrikaz("vezmi bytost1"),"Bytost nebo bytost nemůžeš vzít");  
        assertTrue(lokace1.obsahujeBytost("bytost1"));
        assertFalse(batoh.obsahujePredmet("bytost1"));
        assertEquals(hra1.zpracujPrikaz("vezmi bytost2"),"předmět: bytost2 tu není");   
        assertTrue(lokace1.obsahujeBytost("bytost2"));
        assertFalse(batoh.obsahujePredmet("bytost2"));
        
        assertEquals(hra1.zpracujPrikaz("vezmi postava"),"Bytost nebo bytost nemůžeš vzít"); 
        assertTrue(lokace1.obsahujePostavu("postava"));
        assertFalse(batoh.obsahujePredmet("postava"));
    }
    
    /**
     * Testuje, zda jsou správně nastaveny parametry sebrání předmětu.
     * Ověří zda jde sebrat omezeny pocet predmetu
     * Předměty, postavy, bytosti nemusí odpovídat vlastní hře, 
     */
    @Test
    public  void testLzeSebratOmezenyPocet() {      
        Predmet vec1 = new Predmet(VEC1, "", true, true);
        Predmet vec2 = new Predmet(VEC2, "", true, true);
        Predmet vec3 = new Predmet(VEC3, "", true, true);
        Predmet vec4 = new Predmet(VEC4, "", true, true);
        Predmet vec5 = new Predmet(VEC5, "", true, true);
      
        Lokace lokace1 = new Lokace("lokace1","",true);
        
        lokace1.vlozPredmet(vec1);
        lokace1.vlozPredmet(vec2);
        lokace1.vlozPredmet(vec3);
        lokace1.vlozPredmet(vec4);
        lokace1.vlozPredmet(vec5);
        
        hra1.getHerniPlan().setAktualniLokace(lokace1);
        Batoh batoh = hra1.getHerniPlan().getBatoh();
        
        assertEquals(hra1.zpracujPrikaz("vezmi vec1"),"Sebral jsem předmět: vec1");  
        assertFalse(lokace1.obsahujePredmet(VEC1));
        assertTrue(batoh.obsahujePredmet(VEC1));
        assertEquals(hra1.zpracujPrikaz("vezmi vec2"),"Sebral jsem předmět: vec2");  
        assertFalse(lokace1.obsahujePredmet(VEC2));
        assertTrue(batoh.obsahujePredmet(VEC2));
        assertEquals(hra1.zpracujPrikaz("vezmi vec3"),"Sebral jsem předmět: vec3");  
        assertFalse(lokace1.obsahujePredmet(VEC3));
        assertTrue(batoh.obsahujePredmet(VEC3));
        assertEquals(hra1.zpracujPrikaz("vezmi vec4"),"Sebral jsem předmět: vec4"); 
        assertFalse(lokace1.obsahujePredmet(VEC4));
        assertTrue(batoh.obsahujePredmet(VEC4));
        assertEquals(hra1.zpracujPrikaz("vezmi vec5"),"Batoh je plny, nejdriv musis neco zahodit");  
        assertTrue(lokace1.obsahujePredmet(VEC5));
        assertFalse(batoh.obsahujePredmet(VEC5));
    }
}