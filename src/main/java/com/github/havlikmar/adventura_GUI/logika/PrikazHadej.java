package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída PrikazHadej představuje příkaz pro hádání hádanky.
 * 
 * @author     Martin Havlík
 * @version    6.5.2017
 */
public class PrikazHadej implements IPrikaz {
    private static final String NAZEV = "hádej";
    private HerniPlan herniPlan;
    
    /**
     * Konstruktor třídy, slouží pro získání odkazu na herníPlán
     *
     * @param    herniPlan odkaz na herníPlan
     */   
    public PrikazHadej(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }
    
    /**
     * Metoda představuje logiku příkazu pro hádání hádanky.
     * Nejprve zkontroluje parametry (může mít 1-7 parametrů),
     * ověří, že hádá ve správné lokaci a že odpověd je správná,
     * pokud je odemkne vstup do lokace.
     * 
     * @param    parametry  pole parametrů zadaných na příkazové řádce
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím co mám hádat";
        }
        
        String odpoved = "";
        for (int i = 0; i < parametry.length; i++ ) {
            odpoved += parametry[i] + " ";
        }
        
        Lokace aktualniLokace = herniPlan.getAktualniLokace();
        
        // Ve hře je jedna hádanka, jde o specifické řešení.
        if (aktualniLokace.getNazev().equals("řeka")) {
            
            Postava postava = herniPlan.getAktualniLokace().getPostava("pastevec");
            String recPostavy = postava.getRecHadanka(odpoved);
            
            if (postava.jeUhadnuto(odpoved)) {
                
                Predmet dar = postava.getCoMa();
                herniPlan.getAktualniLokace().vlozPredmet(dar);
                postava.setVymena(true);
                aktualniLokace.vratSousedniLokaci("druhý_břeh").setDosazitelny(true);
            }     
            
            return recPostavy;
        }     
        
        return "Zde nemůžeš hádat";
    }
    
    /**
     * Metoda vrací název příkazu. Jedná se o klíčové slovo,
     * kterým je možné uhádnout hádanku
     * 
     * @return název příkazu
     */
    public String getNazev() {
        return NAZEV;
    }
}
