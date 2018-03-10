package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída PrikazMluv představuje příkaz pro mluvení s postavou.
 * 
 * @author     Martin Havlík
 * @version    6.5.2017
 */
public class PrikazMluv implements IPrikaz {
    private static final String NAZEV = "mluv";
    private HerniPlan herniPlan;
    
    /**
     * Konstruktor třídy, slouží pro získání odkazu na herníPlán
     *
     * @param    herniPlan odkaz na herníPlan
     */  
    public PrikazMluv(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }
    
    /**
     * Metoda představuje logiku příkazu pro mluvení s postavou.
     * Nejprve zkontroluje parametry (může mít právě 1),
     * ověří,  že postava je ve správné lokaci a
     * vrátí správný dialog mezi hráčem a postavou
     * 
     * @param    parametry  pole parametrů zadaných na příkazové řádce
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím s kým mám mluvit";
        }
        
        if (parametry.length > 1) {
            return "Tomu nerozumím, muzu mluvit jen s jednou postavou";
        }
        
        String nazevPostavy = parametry[0];  
        Postava postava = herniPlan.getAktualniLokace().getPostava(nazevPostavy);
        
        if (!herniPlan.getAktualniLokace().obsahujePostavu(nazevPostavy)) {
            return "Postava: " + nazevPostavy + " tu není";
        }
        
        return postava.getProjev();
    }
    
    /**
     * Metoda vrací název příkazu. Jedná se o klíčové slovo,
     * s kterým je možné mluvit s postavou
     * 
     * @return název příkazu
     */
    public String getNazev() {
        return NAZEV;
    }
}