package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída PrikazVemi představuje příkaz pro sebrani předmětu ze země a vložení do batohu.
 * 
 * @author     Martin Havlík
 * @version    6.5.2017
 */
public class PrikazVezmi implements IPrikaz {
    private static final String NAZEV = "vezmi";
    private HerniPlan herniPlan;
    
    /**
     * Konstruktor třídy, slouží pro získání odkazu na herníPlán
     *
     * @param    herniPlan odkaz na herníPlan
     */   
    public PrikazVezmi(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }
    
    /**
     * Metoda představuje logiku příkazu pro sebrání předmětu.
     * Nejprve zkontroluje parametry (musí být zadaný právě 1),
     * ověří, že předmět s daným jménem je v aktuální lokaci,
     * 
     * @param    parametry  pole parametrů zadaných na příkazové řádce
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím co mam sebrat";
        }
        
        if (parametry.length > 1) {
            return "Tomu nerozumím, můžu sebrat jen 1 předmět";
        }
        
        String nazevPredmetu = parametry[0];
        Lokace aktualniLokace = herniPlan.getAktualniLokace();
        
        if (!aktualniLokace.obsahujePredmet(nazevPredmetu)) {
            if ((aktualniLokace.obsahujeBytost(nazevPredmetu) && aktualniLokace.getBytost(nazevPredmetu).isViditelny()) || aktualniLokace.getPostava(nazevPredmetu) != null) {
                return "Bytost nebo bytost nemůžeš vzít";
            }
        
            return "předmět: " + nazevPredmetu + " tu není";
        }
        
        Predmet predmet = herniPlan.getAktualniLokace().vezmiPredmet(nazevPredmetu);
        
        if (!predmet.isViditelny()) {
            aktualniLokace.vlozPredmet(predmet);
            return "předmět: " + nazevPredmetu + " tu není";
        }
        
        if (!predmet.isPrenositelny()) {
            aktualniLokace.vlozPredmet(predmet);
            return "Neumím sebrat nepřenositelný předmět";
        }
        
        Batoh batoh = herniPlan.getBatoh();
        
        if (batoh.isPlny()) {
            aktualniLokace.vlozPredmet(predmet);
            return "Batoh je plny, nejdriv musis neco zahodit";
        }
        
        batoh.vlozPredmet(predmet);
        return "Sebral jsem předmět: " + nazevPredmetu;
    }
    
    /**
     * Metoda vrací název příkazu. Jedná se o klíčové slovo,
     * kterým je možné vzít předmět.
     * 
     * @return název příkazu
     */
       public String getNazev() {
        return NAZEV;
    }
}