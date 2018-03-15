package com.github.havlikmar.adventura_GUI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;
import com.github.havlikmar.adventura_GUI.logika.Predmet;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída HraTest slouží ke komplexnímu otestování
 * třídy Hra.
 *
 * @author     Jarmila Pavlíčková, Jan Říha, upravil Martin Havlík
 * @version    LS 2016/2017 (upraveno 16.5.2017)
 */
public class HraTest {
    private Hra hra1;
    
    // vytvoření pojmenovaných konstant, důvodem je hláška pmd.
    private static final String LES = "les";
    private static final String JDI_LES = "jdi les";
    private static final String JDI_REKA = "jdi řeka";
    private static final String REKA = "řeka";
    private static final String DRUHY_BREH = "druhý_břeh";
    private static final String JDI_DRUHY_BREH = "jdi druhý_břeh";
    private static final String DUL = "důl";
    private static final String JDI_DUL = "jdi důl";
    private static final String VEZMI_LOPATA = "vezmi lopata";
    private static final String CESTA = "cesta";
    private static final String JDI_CESTA = "jdi cesta";
    private static final String HLUBOKY_LES = "hluboký_les";
    private static final String JDI_HLUBOKY_LES = "jdi hluboký_les";
    private static final String JDI_JESKYNE = "jdi jeskyně";
    private static final String JDI_DNO_STUDNE = "jdi dno_studně";
    private static final String POUZIJ_MEC = "použij meč";
    private static final String KLIC_OD_TRUHLY = "klíč_od_truhly";
    private static final String CHALOUPKA = "chaloupka";
    private static final String JDI_CHALOUPKA = "jdi chaloupka";
    private static final String KLIC = "klíč";
    
    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @Before
    public void setUp() {
        hra1 = new Hra();
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     * Slouží k resetovánín nastavení, pro další testy hry.
     */
    @After
    public void tearDown() {
		HerniPlan.setZachranilMedvide(false);
		HerniPlan.setPotkalJezdce(false);
		HerniPlan.setZavalenyVchod(false);
		HerniPlan.setVerzeKonce(-1);
    }

    //== Soukromé metody používané v testovacích metodách ==========================
   
    /***************************************************************************
     * Slouží pro získání lektvaru od stařeny. 
     * Zjednodušení hlavního testu, v hlavním testu se neopakuje,
     * ale nejdelší možný konec potřeboval zkrátit, aby prošel pmd.
     */
    private void ziskaniLektvaru() {
        hra1.zpracujPrikaz(VEZMI_LOPATA);
        hra1.zpracujPrikaz("vezmi buchty");
        hra1.zpracujPrikaz(JDI_LES);
        hra1.zpracujPrikaz(JDI_HLUBOKY_LES);
        hra1.zpracujPrikaz("vezmi bylinky");
        hra1.zpracujPrikaz(JDI_LES);
        hra1.zpracujPrikaz("jdi okraj_lesa");
        hra1.zpracujPrikaz("mluv stařena");
        hra1.zpracujPrikaz("dej buchty stařena");
        hra1.zpracujPrikaz("použij bylinky");
        hra1.zpracujPrikaz("vezmi lektvar");
        hra1.zpracujPrikaz(JDI_LES);
    }
    
    /***************************************************************************
     * Slouží pro získání zlaťaků pro kupce. Zahrnuje cestu od kupce k zlaťákům a zpátky
     * na druhý břeh. Zjednodušení hlavního testu, jelikož se vyskytuje ve všech třech koncích hry.
     */
    private void ziskaniZlataku() {
        hra1.zpracujPrikaz(JDI_DUL);
        hra1.zpracujPrikaz(JDI_DRUHY_BREH);
        hra1.zpracujPrikaz(JDI_REKA);
        hra1.zpracujPrikaz(JDI_LES);
        hra1.zpracujPrikaz(JDI_HLUBOKY_LES);
        hra1.zpracujPrikaz("použij lopata");
        hra1.zpracujPrikaz("polož lopata");
        hra1.zpracujPrikaz("vezmi pytlík_peněz");
        hra1.zpracujPrikaz(JDI_LES);
        hra1.zpracujPrikaz(JDI_REKA);
        hra1.zpracujPrikaz(JDI_DRUHY_BREH);
        hra1.zpracujPrikaz(JDI_DUL);
        hra1.zpracujPrikaz(JDI_CESTA);
        hra1.zpracujPrikaz("dej pytlík_peněz kupec");
        hra1.zpracujPrikaz(JDI_DUL);
        hra1.zpracujPrikaz(JDI_DRUHY_BREH);
    }
    
    /***************************************************************************
     * Slouží pro přesun z lokace les do lokace dno_studně.
     * Zjednodušení hlavního testu, jelikož se vyskytuje ve všech třech koncích hry.
     */
    private void jdiDoJeskyne() {
        hra1.zpracujPrikaz(JDI_HLUBOKY_LES);
        hra1.zpracujPrikaz(JDI_JESKYNE);
        hra1.zpracujPrikaz("použij píšťalka");
        hra1.zpracujPrikaz("polož píšťalka");
        hra1.zpracujPrikaz(JDI_DNO_STUDNE);
    }
    
    /***************************************************************************
     * Slouží pro zabití vlkodlaka a následný návrat do hlubokýho_lesa
     * Zjednodušení hlavního testu, jelikož se vyskytuje ve všech třech koncích hry.
     */
    private void jdiZJeskyne() {
        hra1.zpracujPrikaz("jdi konec_jeskyně");
        hra1.zpracujPrikaz(POUZIJ_MEC);
        hra1.zpracujPrikaz("vezmi klíč");
        hra1.zpracujPrikaz(JDI_DNO_STUDNE);
        hra1.zpracujPrikaz(JDI_JESKYNE);
        hra1.zpracujPrikaz(JDI_HLUBOKY_LES);
        hra1.zpracujPrikaz("odemkni chaloupka");
    }
    
    /***************************************************************************
     * Slouží pro přesun z lokace les do lokace důl. Obsahuje všechny rozhovory a darování.
     * Zjednodušení hlavního testu, jelikož se vyskytuje ve všech třech koncích hry.
     */
    private void jdiRekaAzDul() {
        hra1.zpracujPrikaz(JDI_REKA);
        hra1.zpracujPrikaz("mluv pastevec");
        hra1.zpracujPrikaz("hádej slepice nic pes slepice zrní nic slepice");
        hra1.zpracujPrikaz("vezmi povolení_plavby");
        hra1.zpracujPrikaz(JDI_DRUHY_BREH);
        hra1.zpracujPrikaz("mluv krysař");
        hra1.zpracujPrikaz("dej povolení_plavby krysař");
        hra1.zpracujPrikaz(JDI_DUL);
    }

    //== Vlastní testovací metody ==================================================

    /***************************************************************************
     * Testuje zda jde udělat úkol u kupce.
     * Slouží k otestování lokální metody ziskaniZlataku.
     */
    @Test
    public void testZiskaniZlataku() {
        hra1.getHerniPlan().setAktualniLokace(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LES).vratSousedniLokaci("řeka"));
        
        // umožní jít dál. Už je odemknutu
        hra1.zpracujPrikaz("hádej slepice nic pes slepice zrní nic slepice");
        hra1.getHerniPlan().setAktualniLokace(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci("druhý_břeh").vratSousedniLokaci("důl").vratSousedniLokaci("cesta"));
        
        hra1.zpracujPrikaz(JDI_DUL);
        assertEquals(false, hra1.konecHry());
        assertEquals(DUL, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_DRUHY_BREH);
        assertEquals(false, hra1.konecHry());
        assertEquals(DRUHY_BREH, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_REKA);
        assertEquals(false, hra1.konecHry());
        assertEquals(REKA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        // Lopata je obsažena v originálním batohu
        Predmet lopata = new Predmet("lopata", "rezava lopata", true, true, "hluboký_les", "pytlík_peněz", "Kopeš, kopeš a najednou objevíš pytlík plný zlaťáků", "Už jsi tady kopal");
        hra1.getHerniPlan().getAktualniLokace().vlozPredmet(lopata);
        hra1.zpracujPrikaz(VEZMI_LOPATA);
        
        hra1.zpracujPrikaz(JDI_HLUBOKY_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(HLUBOKY_LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet("pytlík_peněz").isViditelny());
        hra1.zpracujPrikaz("použij lopata");
        hra1.zpracujPrikaz("polož lopata");
        hra1.zpracujPrikaz("vezmi pytlík_peněz");
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_REKA);
        assertEquals(false, hra1.konecHry());
        assertEquals(REKA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_DRUHY_BREH);
        assertEquals(false, hra1.konecHry());
        assertEquals(DRUHY_BREH, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_DUL);
        assertEquals(false, hra1.konecHry());
        assertEquals(DUL, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_CESTA);
        assertEquals(false, hra1.konecHry());
        assertEquals(CESTA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("dej pytlík_peněz kupec");
        
        hra1.zpracujPrikaz(JDI_DUL);
        assertEquals(false, hra1.konecHry());
        assertEquals(DUL, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_DRUHY_BREH);
        assertEquals(false, hra1.konecHry());
        assertEquals(DRUHY_BREH, hra1.getHerniPlan().getAktualniLokace().getNazev());
    }
    
    /***************************************************************************
     * Testuje zda se dá dojít z lesa do konce jeskyně a zpátky do hlubokého lesa.
     * Slouží k otestování lokální metody jdiDoJeskyne a jdiZJeskyně.
     */
    @Test
    public void testCestaLesJeskyne() {
        Lokace lokace = hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LES);
        hra1.getHerniPlan().setAktualniLokace(lokace);
       
        hra1.zpracujPrikaz(JDI_HLUBOKY_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(HLUBOKY_LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_JESKYNE);
        assertEquals(false, hra1.konecHry());
        
        // píštalku již máme v inventáři
        Predmet pistalka = new Predmet("píšťalka", "stará vyřezávaná píšťalka", true, true, "jeskyně", "dno_studně", "Zapíškáš na píšťalku a krysy odkráčejí ven", "Už píštalku použil.");
        hra1.getHerniPlan().getAktualniLokace().vlozPredmet(pistalka);
        hra1.zpracujPrikaz("vezmi píšťalka");
        
        assertEquals("jeskyně", hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("použij píšťalka");
        hra1.zpracujPrikaz("polož píšťalka");
        
        hra1.zpracujPrikaz(JDI_DNO_STUDNE);
        assertEquals(false, hra1.konecHry());
        assertEquals("dno_studně", hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        // Přidání meče do hry. Předmět již máme.
        Predmet mec = new Predmet("meč", "čistý, lesklý, nový meč", true, true, "konec_jeskyně", "vlkodlak", "Vlkodlak se na tebe vrhne a ty ho zasahuješ. Vzápětí umírá v hrozných bolestech", "Už jsi vlkodlaka zabil.");
        hra1.getHerniPlan().getAktualniLokace().vlozPredmet(mec);
        hra1.zpracujPrikaz("vezmi meč");
        
        hra1.zpracujPrikaz("jdi konec_jeskyně");
        assertEquals(false, hra1.konecHry());
        assertEquals("konec_jeskyně", hra1.getHerniPlan().getAktualniLokace().getNazev());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet(KLIC).isViditelny());
        hra1.zpracujPrikaz(POUZIJ_MEC);
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet(KLIC).isViditelny());
        hra1.zpracujPrikaz("vezmi klíč");
        assertTrue(hra1.getHerniPlan().getBatoh().obsahujePredmet(KLIC));
        
        hra1.zpracujPrikaz(JDI_DNO_STUDNE);
        assertEquals(false, hra1.konecHry());
        assertEquals("dno_studně", hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_JESKYNE);
        assertEquals(false, hra1.konecHry());
        assertEquals("jeskyně", hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_HLUBOKY_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(HLUBOKY_LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("odemkni chaloupka");
    }
    
    /***************************************************************************
     * Testuje zda se dá dojít z lesa do dolu, včetně všech rozhovorů, darování a hádání.
     * Slouží k otestování lokální metody jdiRekaAzDul.
     */
    @Test
    public void testjdiRekaAzDul() {
        Lokace lokace = hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(LES).vratSousedniLokaci("řeka");
        hra1.getHerniPlan().setAktualniLokace(lokace);
        
        hra1.zpracujPrikaz(JDI_REKA);
        assertEquals(false, hra1.konecHry());
        assertEquals(REKA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("mluv pastevec");

        assertFalse(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(DRUHY_BREH).isDosazitelny());
        hra1.zpracujPrikaz("hádej slepice nic pes slepice zrní nic slepice");
        assertTrue(hra1.getHerniPlan().getAktualniLokace().vratSousedniLokaci(DRUHY_BREH).isDosazitelny());
        assertTrue(hra1.getHerniPlan().getAktualniLokace().obsahujePredmet("povolení_plavby"));
        hra1.zpracujPrikaz("vezmi povolení_plavby");
        assertTrue(hra1.getHerniPlan().getBatoh().obsahujePredmet("povolení_plavby"));
        
        hra1.zpracujPrikaz(JDI_DRUHY_BREH);
        assertEquals(false, hra1.konecHry());
        assertEquals(DRUHY_BREH, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("mluv krysař");
        hra1.zpracujPrikaz("dej povolení_plavby krysař");

        hra1.zpracujPrikaz(JDI_DUL);
        assertEquals(false, hra1.konecHry());
        assertEquals(DUL, hra1.getHerniPlan().getAktualniLokace().getNazev());
    }
    
    /***************************************************************************
     * Testuje zda se dá splnit úkol u stařeny a vyrobit lektvar.
     * Slouží k otestování lokální metody ziskaniLektvaru.
     */
    @Test
    public void testZiskaniLektvaru() {
        assertEquals("domeček", hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz(VEZMI_LOPATA);
        hra1.zpracujPrikaz("vezmi buchty");
        assertEquals(false, hra1.konecHry());
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_HLUBOKY_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(HLUBOKY_LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("vezmi bylinky");
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz("jdi okraj_lesa");
        assertEquals(false, hra1.konecHry());
        assertEquals("okraj_lesa", hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("mluv stařena");
        hra1.zpracujPrikaz("dej buchty stařena");
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet("lektvar").isViditelny());
        hra1.zpracujPrikaz("použij bylinky");
        hra1.zpracujPrikaz("vezmi lektvar");
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
    }
    
    /***************************************************************************
     * Testuje průběh hry, po zavolání každěho příkazu testuje, zda hra končí
     * a v jaké aktuální místnosti se hráč nachází,
     * popř. zda jsou splněny všechny logické věci (viditelnost, zamčenost, atd.)
     * Jde o průběh hry s prvním úspěšným koncem. 
     */
    @Test
    public void testPrubehHry1() {
        assertEquals("domeček", hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("vezmi krumpáč");
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz(VEZMI_LOPATA);
        assertEquals(false, hra1.konecHry());
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("mluv pašeráci");
        assertEquals(false, hra1.konecHry());
        
        jdiRekaAzDul();
        
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet("drahokamy").isViditelny());
        hra1.zpracujPrikaz("použij krumpáč");
        hra1.zpracujPrikaz("vezmi drahokamy");
        
        hra1.zpracujPrikaz(JDI_CESTA);
        assertEquals(false, hra1.konecHry());
        assertEquals(CESTA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("mluv kupec");
        
        ziskaniZlataku();  
        
        hra1.zpracujPrikaz(JDI_REKA);
        assertEquals(false, hra1.konecHry());
        assertEquals(REKA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());

        hra1.zpracujPrikaz("dej drahokamy pašeráci");
        hra1.zpracujPrikaz("použij klíč_od_pasti");
        
        jdiDoJeskyne();
        jdiZJeskyne();
 
        hra1.zpracujPrikaz("použij kláda");
        
        hra1.zpracujPrikaz(JDI_CHALOUPKA);
        assertEquals(false, hra1.konecHry());
        assertEquals(CHALOUPKA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz(POUZIJ_MEC);
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet(KLIC_OD_TRUHLY).isViditelny());
        hra1.getHerniPlan();
		assertEquals(1,HerniPlan.getVerzeKonce());
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz("polož klíč");
        hra1.zpracujPrikaz("vezmi klíč_od_truhly");
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet("truhla").isZamknuty());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet("poklad").isViditelny());
        hra1.zpracujPrikaz("odemkni truhla");
        hra1.zpracujPrikaz("polož klíč_od_truhly");
        hra1.zpracujPrikaz("vezmi poklad");
        assertEquals(true, hra1.konecHry());
    }
    
    /***************************************************************************
     * Testuje průběh hry, po zavolání každěho příkazu testuje, zda hra končí
     * a v jaké aktuální místnosti se hráč nachází,
     * popř. zda jsou splněny všechny logické věci (viditelnost, zamčenost, atd.)
     * Jde o průběh hry s druhým úspěšným koncem.
     */
    @Test
    public void testPrubehHry2() {
        ziskaniLektvaru();
        jdiRekaAzDul();
        
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet("drahokamy").isViditelny());
        hra1.zpracujPrikaz("mluv trpaslík");
        hra1.zpracujPrikaz("dej lektvar trpaslík");
        
        hra1.zpracujPrikaz(JDI_CESTA);
        assertEquals(false, hra1.konecHry());
        assertEquals(CESTA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("mluv kupec");
        
        ziskaniZlataku(); 
        
        hra1.zpracujPrikaz(JDI_REKA);
        assertEquals(false, hra1.konecHry());
        assertEquals(REKA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        jdiDoJeskyne();
        
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet("truhlička").isZamknuty());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet("zdroj_moci").isViditelny());
        hra1.zpracujPrikaz("odemkni truhlička");
        hra1.zpracujPrikaz("polož talisman");
        hra1.zpracujPrikaz("vezmi zdroj_moci");
        
        jdiZJeskyne();

        hra1.zpracujPrikaz(JDI_CHALOUPKA);
        assertEquals(false, hra1.konecHry());
        assertEquals(CHALOUPKA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz(POUZIJ_MEC);
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet(KLIC_OD_TRUHLY).isViditelny());
        hra1.getHerniPlan();
		assertEquals(0,HerniPlan.getVerzeKonce());
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz("polož klíč");
        hra1.zpracujPrikaz("vezmi klíč_od_truhly");
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet("truhla").isZamknuty());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet("poklad").isViditelny());
        hra1.zpracujPrikaz("odemkni truhla");
        hra1.zpracujPrikaz("polož klíč_od_truhly");
        hra1.zpracujPrikaz("vezmi poklad");
        assertEquals(true, hra1.konecHry());
    }
    
    /***************************************************************************
     * Testuje průběh hry, po zavolání každěho příkazu testuje, zda hra končí
     * a v jaké aktuální místnosti se hráč nachází,
     * popř. zda jsou splněny všechny logické věci (viditelnost, zamčenost, atd.)
     * Jde o průběh hry s neúspěšným koncem.
     */
    @Test
    public void testPrubehHry3() {
        assertEquals("domeček", hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz(VEZMI_LOPATA);
        assertEquals(false, hra1.konecHry());
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        jdiRekaAzDul();
        
        hra1.zpracujPrikaz(JDI_CESTA);
        assertEquals(false, hra1.konecHry());
        assertEquals(CESTA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("mluv kupec");

        ziskaniZlataku();
        
        hra1.zpracujPrikaz(JDI_REKA);
        assertEquals(false, hra1.konecHry());
        assertEquals(REKA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        hra1.zpracujPrikaz(JDI_LES);
        assertEquals(false, hra1.konecHry());
        assertEquals(LES, hra1.getHerniPlan().getAktualniLokace().getNazev());
        
        jdiDoJeskyne();
        jdiZJeskyne();
        
        hra1.zpracujPrikaz(JDI_CHALOUPKA);
        assertEquals(false, hra1.konecHry());
        assertEquals(CHALOUPKA, hra1.getHerniPlan().getAktualniLokace().getNazev());
        assertFalse(hra1.getHerniPlan().getAktualniLokace().getPredmet(KLIC_OD_TRUHLY).isViditelny());
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz(POUZIJ_MEC);
        assertTrue(hra1.getHerniPlan().getAktualniLokace().getPredmet(KLIC_OD_TRUHLY).isViditelny());
        hra1.getHerniPlan();
		assertEquals(2,HerniPlan.getVerzeKonce());
        assertEquals(true, hra1.konecHry());
    }
    
    /***************************************************************************
     * Testuje průběh hry, po zavolání každěho příkazu testuje, zda hra končí
     * a v jaké aktuální místnosti se hráč nachází,
     * popř. zda jsou splněny všechny logické věci (viditelnost, zamčenost, atd.)
     * Jde o průběh hry s neúspěšným koncem.
     */
    @Test
    public void testLzeMenitKonec() {
        Predmet klic = new Predmet(KLIC, "tajemný klíč", true, true, HLUBOKY_LES, CHALOUPKA, "Dáš klíč do zámku a otočíš jím. Vzápětí se dveře otevřou", "Už jsi dveře otevřel.");
        Predmet klicOdPasti = new Predmet("klíč_od_pasti", "klíč od pasti medvíděte", true, true, LES, "medvídě", "Odemkneš past a medvídě štastně klopýtá do lesa", "Už jsi medvídě osvobodil");
        Predmet mec = new Predmet("meč", "čistý, lesklý, nový meč", true, true, CHALOUPKA, "tajemný_jezdec", "Jezdec se na tebe vrhá. Ty ho zasáhneš, bohužel meč se v něm zasekl. Vypadá to, že jezdec je nazastavitelný a pronásleduje tě z chaloupky.", "Jezdec je již mrtvý.");
        
        hra1.getHerniPlan().getAktualniLokace().vlozPredmet(klic);
        hra1.zpracujPrikaz("vezmi klíč");
        hra1.getHerniPlan().getAktualniLokace().vlozPredmet(klicOdPasti);
        hra1.zpracujPrikaz("vezmi klíč_od_pasti");
        hra1.getHerniPlan().getAktualniLokace().vlozPredmet(mec);
        hra1.zpracujPrikaz("vezmi meč");
        
        hra1.zpracujPrikaz(JDI_LES);
        hra1.getHerniPlan();
		assertFalse(HerniPlan.getZachranilMedvide());
        hra1.zpracujPrikaz("použij klíč_od_pasti");
		assertTrue(HerniPlan.getZachranilMedvide());
        hra1.zpracujPrikaz("jdi hluboký_les");
		assertFalse(HerniPlan.getZavalenyVchod());
        hra1.zpracujPrikaz("použij kláda");
		assertTrue(HerniPlan.getZavalenyVchod());
        hra1.zpracujPrikaz("odemkni chaloupka");
        hra1.zpracujPrikaz(JDI_CHALOUPKA);
		assertFalse(HerniPlan.getPotkalJezdce());
        hra1.zpracujPrikaz(POUZIJ_MEC);
		assertTrue(HerniPlan.getPotkalJezdce());
    }
}