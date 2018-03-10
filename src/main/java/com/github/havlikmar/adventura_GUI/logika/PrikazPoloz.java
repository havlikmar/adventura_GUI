package com.github.havlikmar.adventura_GUI.logika;


/**
 * Třída PrikazHadej představuje příkaz pro hádání hádanky.
 * 
 * @author     Martin Havlík
 * @version    10.5.2017
 */
public class PrikazPoloz implements IPrikaz {
    private static final String NAZEV = "polož";
    private HerniPlan herniPlan;
    
    /**
     * Konstruktor třídy, slouží pro získání odkazu na herníPlán
     *
     * @param    herniPlan odkaz na herníPlan
     */   
    public PrikazPoloz(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }
    
    /** 
     * Nejprve zkontroluje parametry (může mít právě jeden),
     * ověří, že hráč je ve správné lokaci a že předmět který pokládám je v batohu.
     * Pak ho položí
     * 
     * @param    parametry  pole parametrů zadaných na příkazové řádce
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevim co mam položit";
        }
        
        if (parametry.length > 1) {
            return "Tomu nerozumim, muzu položit jen 1 predmet";
        }
        
        String nazevPredmetu = parametry[0];
        
        if (!herniPlan.getBatoh().obsahujePredmet(nazevPredmetu)) {
            return "Předmět: " + nazevPredmetu + " nemám v batohu";
        }
        
        Predmet predmet = herniPlan.getBatoh().polozPredmet(nazevPredmetu);
        herniPlan.getAktualniLokace().vlozPredmet(predmet);  
        return "Položil jsem předmět: " + nazevPredmetu;
    }
    
    /**
     * Metoda vrací název příkazu. Jedná se o klíčové slovo,
     * kterým je možné předmět položit
     */
    public String getNazev(){
        return NAZEV;
    }
}