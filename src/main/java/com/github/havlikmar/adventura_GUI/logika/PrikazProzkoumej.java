package com.github.havlikmar.adventura_GUI.logika;


/**
 * Třída PrikazHadej představuje příkaz pro hádání hádanky.
 * 
 * @author     Martin Havlík
 * @version    10.5.2017
 */
public class PrikazProzkoumej implements IPrikaz {
    private static final String NAZEV = "prozkoumej";
    private HerniPlan herniPlan;
    
    /**
     * Konstruktor třídy, slouží pro získání odkazu na herníPlán
     *
     * @param    herniPlan odkaz na herníPlan
     */   
    public PrikazProzkoumej(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }
    
    /**
     * Metoda představuje logiku příkazu pro prozkoumání lokace, předmětu či bytosti.
     * Nejprve zkontroluje parametry (může žádný, či právě jeden),
     * Pokud je parametr jeden, prozkoumá aktuální lokace.
     * Pokud je jeden, zkontroluje zda v lokaci je předmět, bytost a vypíše její popis.
     * 
     * @param    parametry  pole parametrů zadaných na příkazové řádce
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
    public String proved(String... parametry) {
        Lokace aktualniLokace = herniPlan.getAktualniLokace();
        
        if (parametry.length < 1) {
            String vypis = aktualniLokace.dlouhyPopis();
            
            if (herniPlan.getBatoh().vypisBatohu().equals("batoh:")) {
                return vypis;
            }
            else {
                vypis = vypis + "\n" + herniPlan.getBatoh().vypisBatohu();
                return vypis;
            }
        }
            
        if (parametry.length > 1) {
            return "Tomu nerozumim, muzu prozkoumat jen 1 predmet";
        }
        
        String nazevPredmetu = parametry[0]; 
        
        if (aktualniLokace.obsahujePredmet(nazevPredmetu) && aktualniLokace.getPredmet(nazevPredmetu).isViditelny()) {
            return aktualniLokace.getPredmet(nazevPredmetu).getPopis();
        }
    
        if(herniPlan.getBatoh().obsahujePredmet(nazevPredmetu)) {
            return herniPlan.getBatoh().getPredmet(nazevPredmetu).getPopis();
        }
    
        if (aktualniLokace.obsahujeBytost(nazevPredmetu) && aktualniLokace.getBytost(nazevPredmetu).isViditelny()) {
            return aktualniLokace.getBytost(nazevPredmetu).getPopis();
        }
    
        return "Předmět či bytost " + nazevPredmetu + " není v batohu ani v aktuální lokaci";
        }

    /**
     * Metoda vrací název příkazu. Jedná se o klíčové slovo,
     * kterým je možné prozkoumat lokaci, předmět, či bytost.
     *
     * @return    nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }
   
}
