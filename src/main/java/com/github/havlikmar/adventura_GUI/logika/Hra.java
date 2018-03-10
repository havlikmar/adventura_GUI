/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída Hra - třída představující logiku adventury.
 * 
 * Toto je hlavní třída logiky aplikace. Třída vytváří instanci třídy HerniPlan, která inicializuje
 * mistnosti hry a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 * Vypisuje uvítací a ukončovací text hry. Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, upravil Martin Havlík
 * @version    LS 2016/2017 (upraveno 17.5.2017)
 */

public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;    
    private HerniPlan herniPlan;
    private boolean konecHry = false;
        
    /**
     * Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazVezmi(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPoloz(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazMluv(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPouzij(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazProzkoumej(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazDej(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazHadej(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazOdemkni(herniPlan));
    }

    /**
     * Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return "Vítejte!\n" +
               "Toto je příběh o zlatokopovi, hledající poklad,\n" +
               "vaším úkolem je tento poklad najít. Traduje se že poklad má\n" +
               "tajemný jezdec, který žije v chaloupce.\n" +
               "Napište 'nápověda', pokud si nevíte rady, jak hrát dál.\n" +
               "\n" +
               herniPlan.getAktualniLokace().dlouhyPopis();
    }
    
    /**
     * Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        int verzeKonce = HerniPlan.getVerzeKonce();
        String text = "";
        switch(verzeKonce) {
            case 0: 
                text = "Objevil jsi poklad po kterém jsi toužil. Do konce života máš vystaráno. Už nikdy, ale nezapomeneš na dobrodružství,\nkteré jsi zažil. Do okolí se vrátili obyvatelé a les ožil. ";
                break;
            case 1:
                text = "Objevil jsi poklad po kterém jsi toužil. Do konce života máš vystaráno. Už nikdy, ale nezapomeneš na dobrodružství,\nkteré jsi zažil. Jelikož se z jeskyně ozývají zvuky tak les i nadéle zůstává neobydlený. ";
                break;
            case 2:
                text = "Tvá smrt nikoho nezasáhla. Místo abys pomáhal místním, tak ses honil za pokladem, což se ti nevyplatilo. ";
                break;
            default:
                text = "Hra byla ukoncena nestandartním způsobem. ";
                break;
        }
        return text + "Děkuji, že jste si zahráli.";
    }
    
    /** 
     * Vrací true, pokud hra skončila.
     * Slouží pro zjistění konce hry.
     */
     public boolean konecHry() {
        return konecHry;
    }

    /**
     * Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     * Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     * Pokud ano spustí samotné provádění příkazu.
     *
     * @param    radek  text, který zadal uživatel jako příkaz do hry.
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
     public String zpracujPrikaz(String radek) {
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++){
            parametry[i]= slova[i+1];   
        }
        String textKVypsani=" ... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.proved(parametry);
            
            //zkountroluje zda hra stale běži
            if (herniPlan.konecHry()) {
                konecHry = true;
            }
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }
    
    /**
     * Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     * mohou ji použít i další implementace rozhraní Prikaz.
     *  
     * @param    konecHry hodnota false = konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }
    
    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *  
     * @return    odkaz na herní plán
     */
     public HerniPlan getHerniPlan() {
        return herniPlan;
    }
}