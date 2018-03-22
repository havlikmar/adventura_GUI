/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI.logika;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.ui.Observable;
import com.github.havlikmar.adventura_GUI.ui.Observer;


/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * 
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny postavy, bytosti, postavy a lokace, které vzájemně propojuje pomocí východů 
 * a pamatuje si aktuální lokaci, ve které se hráč právě nachází.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, upravil Martin Havlík
 * @version    LS 2016/2017 (upraveno 17.3.2018)
 */
public class HerniPlan implements Observable{
    private Batoh batoh;
    private Lokace aktualniLokace;
    private List<Observer> posluchaci;
    private ArrayList<Lokace> lokace;
    
    // Slouží pro zaznamenání klíčových událostí na konec hry
    private static boolean zavalenyVchod = false;
    private static boolean potkalJezdce = false;
    private static boolean zachranilMedvide = false;
    private static int verzeKonce = -1;
   
    // vytvoření pojmenovaných konstant, důvodem je hláška pmd.
    private static final String HLUBOKY_LES = "hluboký_les";
    private static final String PODEKOVANI = "Děkuji za pomoc";
    
    /**
     * Konstruktor který vytváří jednotlivé lokace a propojuje je pomocí východů.
     */
    public HerniPlan() {
    	lokace = new ArrayList<Lokace>();
        zalozLokaceHry();
        batoh = new Batoh();  
        posluchaci = new ArrayList<Observer>();
    }

    /**
     * Vytváří jednotlivé lokace a propojuje je pomocí východů.
     * Jako výchozí aktuální lokaci nastaví domeček.
     */
    private void zalozLokaceHry() {
        Lokace domecek = new Lokace("domeček","domeček, ve kterém přebýváš", true, 82.00, 308.00);
        lokace.add(domecek);
        Lokace chaloupka = new Lokace("chaloupka", "na posteli spí jezdec. Za ním je truhla. U pásu má přivázaný klíč", true, "klíč", 495.00, 400.00);
        lokace.add(chaloupka);
        Lokace jeskyne = new Lokace("jeskyně","strašitelná jeskyně, ve které se to hemží hladovými krysami",true, 538.00, 252.00);
        lokace.add(jeskyne);
        Lokace les = new Lokace("les","les v kterým jsou pytláci a chycené medvídě", true, 247.00, 308.00);
        lokace.add(les);
        Lokace hlubokyLes = new Lokace(HLUBOKY_LES,"temný les, ve kterém rostou bylinky s vchodem do jeskyně.\nUprostřed je studně a kousek od ní chaloupka. Nad jeskyní je hromada kamení s kládou, která z něj vyčnívá", true, 405.00, 308.00);
        lokace.add(hlubokyLes);
        Lokace okrajLesa = new Lokace("okraj_lesa","okraj lesa, u kterého se u kotlíku s ohněm hrbí stařenka",true, 247.00, 400.00);
        lokace.add(okrajLesa);
        Lokace reka = new Lokace("řeka","řeka u které se nachází pastevec s lodí", true, 247.00, 214.00);
        lokace.add(reka);
        Lokace druhyBreh = new Lokace("druhý_břeh","druhý břeh řeky, na břehu je krysař", false, 248.00, 121.00);
        lokace.add(druhyBreh);
        Lokace dul = new Lokace("důl","opuštěný důl, kde se dřív těžili drahokamy. Nahází se zde trpaslík", true, 370.00, 69.00);
        lokace.add(dul);
        Lokace cesta = new Lokace("cesta","cesta na které je kupec", true, 500.00, 121.00);
        lokace.add(cesta);
        Lokace dnoStudne = new Lokace("dno_studně","dno studně se zářicí truhlou. Truhlu obklopuje magický štít", false, 653.00, 308.00);
        lokace.add(dnoStudne);
        Lokace konecJeskyne = new Lokace("konec_jeskyně","konec jeskyně, kde hybernuje vlkodlak. Pod ním leží lesklý klíč", true, 774.00, 353.00);
        lokace.add(konecJeskyne);
        
        Predmet klicOdTruhly = new Predmet("klíč_od_truhly", "starý zdobený klíč od truhly", true, false);
        Predmet krumpac = new Predmet("krumpáč", "starý krumpáč pro těžbu", true, true, "důl", "drahokamy", "Kutáš, kutáš a najednou ze zdi vypadne malý drahokam", "už jsi tady kutal");
        Predmet buchty = new Predmet("buchty", "čerstvě upečené buchty", true, true);
        Predmet bylinky = new Predmet("bylinky", "léčebné bylinky", true, true, "okraj_lesa", "lektvar", "Dáš bylinky vařit a za chvíli roztok změní barvu. Lektvar je na světě","Už jsi lektvar uvařil");
        Predmet zdrojMoci = new Predmet("zdroj_moci", "zářivá kulička. Zdroj jezdcovi moci", true, false);
        Predmet pistalka = new Predmet("píšťalka", "stará vyřezávaná píšťalka", true, true, "jeskyně", "dno_studně", "Zapíškáš na píšťalku a krysy odkráčejí ven. Za nimi se objevil otvor", "Už píštalku použil.");
        Predmet drahokamy = new Predmet("drahokamy", "nefalšované drahokamy", true, false);
        Predmet klicOdPasti = new Predmet("klíč_od_pasti", "klíč od pasti medvíděte", true, true, "les", "medvídě", "Odemkneš past a medvídě štastně klopýtá do lesa", "Už jsi medvídě osvobodil");
        Predmet lopata = new Predmet("lopata", "rezava lopata", true, true, HLUBOKY_LES, "pytlík_peněz", "Kopeš, kopeš a najednou objevíš pytlík plný zlaťáků", "Už jsi tady kopal");
        Predmet pytlikPenez = new Predmet("pytlík_peněz", "pytlík peněz", true, false);
        Predmet povoleniPlavby = new Predmet("povolení_plavby", "povolení k užití lodě", true, true);
        Predmet truhla = new Predmet("truhla", "velká zdobená truhla", false, "klíč_od_truhly", "poklad", "Odemykáš krásnou ozdobnou truhlu.", "Truhlu jsi již odemkl", true);
        Predmet mec = new Predmet("meč", "čistý, lesklý, nový meč", true, true, "konec_jeskyně", "vlkodlak", "Vlkodlak se probouzí. Vrhá na tebe vrhne a ty ho zasahuješ. Vzápětí umírá v hrozných bolestech", "Už jsi vlkodlaka zabil.");
        Predmet poklad = new Predmet("poklad", "poklad nevyčíslitelné hodnoty", true, false);
        Predmet klic = new Predmet("klíč", "tajemný klíč", true, false, HLUBOKY_LES, "chaloupka", "Dáš klíč do zámku a otočíš jím. Vzápětí se dveře otevřou", "Už jsi dveře otevřel.");
        Predmet klada = new Predmet("kláda", "kláda na hromadě kamenů ", false, true, HLUBOKY_LES, "jeskyně", "Zatlačíš na kládu a najednou se kameny sesunou a zasypou jeskyni", "Už jsi kládu použil.");
        Predmet recept = new Predmet("recept","K výrobě lektvaru jsou potřeba bylinky z nedalekého lesa. Dej je vařit nad ohněm a lektvar je na světě.", true, false);
        Predmet lektvar = new Predmet("lektvar", "lektvar proti kletbě", true, false);
        Predmet talisman = new Predmet("talisman","talisman, který ruší účinky ochrané kletby", true, true);
        Predmet truhlicka = new Predmet("truhlička", "tajemná truhlička, kterou obklopuje tajemná záře", false, "talisman", "zdroj_moci", "Nasadíš si talisman dojdeš k předmětu a on se otevře. Vypadne z něj zářivá kulička", "Už jsi truhličku otevřel", true);
        
        Postava paseraci = new Postava("pašeráci", drahokamy, klicOdPasti, "Nechceš hezké medívídě, může se hodit jako lovecká trofej,\nnebo ho můžeš prodat dál, či ho pustit pokud jsi lidumil?", "S tebou byla radost obchodovat", "To my nechceme. Chceme drahokamy!", "Dobrá nabídka, tady máš klíč od pasti a dělej si co chceš. Dáme ti jednu radu, nedaleko je hromada kamenů, dávej si pozor,\npřed nedávnem někdo zahradil jeskyni. Vypadalo to, jako by to někdo udělal schválně.");
        Postava pastevec = new Postava("pastevec", "slepice nic pes slepice zrní nic slepice ", povoleniPlavby, "Nechceš mi pomoci. Potřebuji převést psa, slepici, a pytel zrní. Můžu převést jen jednu věc.\nPes nemůže být na stejném břehu se slepicí a slepice se zrním. Pokud to vyřešíš, můžeš za to použít mou loď.", "Děkuji za vyřešení hádanky", "To není řešení.", "Správné řešení, tady máš povolení užívat mou loď.");
        Postava kupec = new Postava("kupec", pytlikPenez, mec, "Potřebuji pomoc. V hlubokém lese jsem před časem zakopal peníze. Bohužel tam nyní straší a já se tam bojím jít,\npokud mi je přineseš dám ti tento meč.", PODEKOVANI, "To nechci. Chtěl jsem své peníze.", "Tady máš ten slíbený meč");
        Postava krysar = new Postava("krysař", povoleniPlavby, pistalka, "Potřebuji pomoc. Potřebuji se dostat na druhou stranu. Můžu ti dát svou píštalku, která dokáže ovládat myši.", PODEKOVANI, "To nechci. Potřebuji povolení plavby.", "Zde je tvá píštalka");
        Postava starena = new Postava("stařena", buchty, recept, "Nemáš něco k jídlu, mám strašný hlad. Můžu ti dát recept k lektvaru proti kletbě.", PODEKOVANI, "To nechci. Potřebuji buchty.", "Zde je tvůj recept. Vím že hledáš poklad. Poklad je hlídaný záhadným jezdcem.\nTraduje se že jezdec je nesmrtelný a jeho moc pramení z předmětu v nedaleké jeskyni.");
        Postava trpaslik = new Postava("trpaslík", lektvar, talisman, "Potřebuji pomoct, dívka o kterou se s mými bratry staréme je zakletá. Pokud jí pomůžeš, dostaneš talisman, který dokáže zneškodnit ochranné kletby", PODEKOVANI, "To nechci. Potřebuji lektvar.", "Zde je talisman. Děkuji za tvou pomoc");
        
        Bytost medvide = new Bytost("medvídě", "malé zraněné medvídě");
        Bytost vlkodlak = new Bytost("vlkodlak", "strašlivý vlkodlak, který spí");
        Bytost tajemnyJezdec = new Bytost("tajemný_jezdec", "Tajemný jezdec, který vlastní poklad. Právě spí");
        
        les.vlozPostavu(paseraci);
        reka.vlozPostavu(pastevec);
        cesta.vlozPostavu(kupec);
        druhyBreh.vlozPostavu(krysar);
        okrajLesa.vlozPostavu(starena);
        dul.vlozPostavu(trpaslik);
        
        les.vlozBytost(medvide);
        konecJeskyne.vlozBytost(vlkodlak);
        chaloupka.vlozBytost(tajemnyJezdec);
        
        domecek.vlozPredmet(krumpac);
        domecek.vlozPredmet(buchty);
        domecek.vlozPredmet(lopata);
        hlubokyLes.vlozPredmet(bylinky);
        hlubokyLes.vlozPredmet(klada);
        hlubokyLes.vlozPredmet(pytlikPenez);
        dul.vlozPredmet(drahokamy);
        okrajLesa.vlozPredmet(lektvar);
        dnoStudne.vlozPredmet(truhlicka);
        dnoStudne.vlozPredmet(zdrojMoci);
        konecJeskyne.vlozPredmet(klic);
        chaloupka.vlozPredmet(truhla);
        chaloupka.vlozPredmet(poklad);
        chaloupka.vlozPredmet(klicOdTruhly);
        
        domecek.setVychod(les);
        les.setVychod(domecek);
        les.setVychod(hlubokyLes);
        les.setVychod(okrajLesa);
        les.setVychod(reka);
        reka.setVychod(les);
        reka.setVychod(druhyBreh);
        druhyBreh.setVychod(reka);
        druhyBreh.setVychod(dul);
        dul.setVychod(druhyBreh);
        dul.setVychod(cesta);
        cesta.setVychod(dul);
        okrajLesa.setVychod(les);
        hlubokyLes.setVychod(les);
        hlubokyLes.setVychod(jeskyne);
        hlubokyLes.setVychod(chaloupka);
        jeskyne.setVychod(hlubokyLes);
        jeskyne.setVychod(dnoStudne);
        dnoStudne.setVychod(jeskyne);
        dnoStudne.setVychod(konecJeskyne);
        konecJeskyne.setVychod(dnoStudne);
        chaloupka.setVychod(hlubokyLes);

        aktualniLokace = domecek;  
    }

    /**
     * Metoda vrací odkaz na aktuální lokaci, ve které se hráč právě nachází.
     *
     * @return    aktuální lokace
     */
    public Lokace getAktualniLokace() {
        return aktualniLokace;
    }

    /**
     * Metoda nastaví aktuální lokaci, používá se nejčastěji při přechodu mezi lokacemi.
     *
     * @param    lokace nová aktuální lokace
     */
    public void setAktualniLokace(Lokace lokace) {
        aktualniLokace = lokace;
        oznamPosluchaci();
    }
    
    /**
     * Metoda vrací odkaz na batoh hráče. Slouží k přístupu k němu.
     *
     * @return    batoh hráče
     */
    public Batoh getBatoh() {
        return batoh;
    }  
    
    /**
     * Metoda vrací informaci zda hra končí.
     *
     * @return    hodnota zda hra končí
     */
     public boolean konecHry () {
        if (verzeKonce == 2) {
            return true;
        }
        
        return getBatoh().obsahujePredmet("poklad");
    }  
    
    /**
     * Metoda slouží k nastavení informace zda je zavalený vchod do jeskyně.
     *
     * @param   newZavalenyVchod nová hodnota po zavalení vchodu
     */
    public static void setZavalenyVchod(boolean newZavalenyVchod) {
        zavalenyVchod = newZavalenyVchod;
    }
    
    /**
     * Metoda slouží k nastavení informace zda jsi bojoval s jezdcem.
     *
     * @param   newPotkalJezdce nová hodnota při setkání s jezdcem
     */
    public static void setPotkalJezdce(boolean newPotkalJezdce) {
        potkalJezdce = newPotkalJezdce;
    }
    
    /**
     * Metoda slouží k nastavení informace zda jsi zachránil medvídě.
     *
     * @param   newZachranilMedvide nová hodnota při zachránění medvíděte
     */
    public static void setZachranilMedvide(boolean newZachranilMedvide) {
        zachranilMedvide = newZachranilMedvide;
    }
    
    /**
     * Metoda slouží k nastavení epilogu a zprávy při souboji s jezdcem.
     *
     * @param   batoh batoh hráče
     * @return  zpráva vypsaná na konzoli při souboji s jezdcem
     */
    public static String getZpravaSouboj(Batoh batoh) {
        if (potkalJezdce) {
            
            if (batoh.obsahujePredmet("zdroj_moci")) {
                verzeKonce = 0;
                return "\n" + "Najednout se z batohu objeví záře, jezdec začne krvácet až vykrvácí.\nJezdec padá a vypadává z něj klíč";
            }
            
            if (zachranilMedvide) {
                
                if (zavalenyVchod) {
                    verzeKonce = 1;
                    return "\n" + "Jezdec vyjde z chaloupky v tom přiběhne medvědice a schodí ho do studně.\nZe studně je slyšet bušení a medvedice utíká zpět k medvíděti.\nJdeš se podívat do chaloupky a uvidíš na zemi klíč.";
                }
                else {
                    verzeKonce = 2;
                    return "\n" + "Jezdec vyjde z chaloupky v tom přiběhne medvědice a schodí ho do studně.\nZa chvíli se z lesa objeví jezdec, jako by se mu nic nestalo a zabijí tě.";
                }
            }
            
            verzeKonce = 2;
            return "\n" + "Jezdec tě dohání a zabíjí tě.";
        }
        
        return "";
    }
    
    /**
     * Metoda slouží k zjištění zda jsi zachránil medvídě. 
     * Slouží pro potřeby testů.
     *
     * @return  vrátí zda jsi zachránil medvídě
     */
    public static boolean getZachranilMedvide() {
        return zachranilMedvide;
    }
    
    /**
     * Metoda slouží k zjištění zda jsi zavalil vchod. 
     * Slouží pro potřeby testů.
     *
     * @return  vrátí zda jsi zavalil vchod
     */
    public static boolean getZavalenyVchod() {
        return zavalenyVchod;
    }
    
    /**
     * Metoda slouží k zjištění zda jsi bojoval s jezdcem. 
     * Slouží pro potřeby testů.
     *
     * @return  vrátí zda jsi potkal jezdce
     */
    public static boolean getPotkalJezdce() {
        return potkalJezdce;
    }
    
    /**
     * Metoda slouží k zjištění verze konce. Slouží pro vypsání epilogu 
     * a ukončení hry, v případě neúspěchu při souboji
     *
     * @return  vrátí verzi konce hry
     */
    public static int getVerzeKonce() {
        return verzeKonce;
    }
    
    /**
     * Metoda slouží k vytvoření verze konce hry. 
     * Tato metoda se používá výhradně v testech, kdy je potřeba
     * po testech průchodu resetovat nastavení
     * 
     * @param   newVerzeKonce nová verze konce
     */
    public static void setVerzeKonce(int newVerzeKonce) {
        verzeKonce = newVerzeKonce;
    }
    
    /**
	 * Metoda pro přidání posluchače k odběru
	 * 
	 *  @param	observer posluchač
	 */
    public void pridejPosluchace(Observer observer) {
    	posluchaci.add(observer);
    }
    
    /**
	 * Metoda pro odebrání posluchače z odběru
	 * 
	 *  @param	observer posluchač
	 */
	public void odeberPosluchace(Observer observer){
		posluchaci.remove(observer);
	}
	
	/**
	 * Metoda pro oznámenín posluchačů o změně
	 * 
	 */
	public void oznamPosluchaci(){
		for(Observer observer: posluchaci) {
			observer.uprav();
		}
	}
	
	/**
	 * Metoda pro získání lokací
	 * 
	 * @return  vrátí všechny lokace
	 */
	public ArrayList<Lokace> getLokace(){
		return lokace;
	}
}
