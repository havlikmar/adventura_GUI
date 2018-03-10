package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída PrikazHadej představuje příkaz pro hádání hádanky.
 * 
 * @author     Martin Havlík
 * @version    10.5.2017
 */
public class PrikazDej implements IPrikaz {
    private static final String NAZEV = "dej";
    private HerniPlan herniPlan;
    
    /**
     * Konstruktor třídy, slouží pro získání odkazu na herníPlán
     *
     * @param    herniPlan odkaz na herníPlan
     */   
    public PrikazDej(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }
    /**
     * Metoda představuje logiku příkazu pro dání předmětu postavě.
     * Nejprve zkontroluje parametry (má dva, první představuje předmět, který dávám, druhý postavu, které dávám),
     * ověří, že hráč je ve stejné lokaci co postava a daný předmět je správný.
     *
     * @param    parametry  pole parametrů zadaných na příkazové řádce
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
    public String proved(String... parametry) {
        if (parametry.length < 2) {
            return "Nevím komu to mám dát";
        }
        
        if (parametry.length > 2) {
            return "Tomu nerozumím, můžu dát předmět jenom jedné osobě";
        }
        
        String nazevPredmetu = parametry[0];
        String nazevPostavy = parametry[1];
        
        Predmet predmet = herniPlan.getBatoh().getPredmet(nazevPredmetu);
        
        if (!herniPlan.getAktualniLokace().obsahujePostavu(nazevPostavy)) {
            return "Postava: " + nazevPostavy + " tu není";
        }
        
        Postava postava = herniPlan.getAktualniLokace().getPostava(nazevPostavy);
        
        if (postava.getCoChce() == null) {
            return "Postava: " + nazevPostavy + " po tobě nechce žádnou věc";
        }
        
        if (predmet == null) {
            return "Predmet: " + nazevPredmetu + " nemáš";
        }
        
        String recPostavy = postava.getRecVymena(predmet);
        
        if (postava.jeMoznaVymena(predmet)) {
            Predmet dar = postava.getCoMa();
            herniPlan.getBatoh().polozPredmet(nazevPredmetu);
            herniPlan.getBatoh().vlozPredmet(dar);
            postava.setVymena(true);
        }
        
        return recPostavy;
    }
    
    /**
     *  Metoda vrací název příkazu. Jedná se o klíčové slovo,
     *  kterým je možné dát předmět postavě.
     *
     * @return  nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }
}