package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída PrikazHadej představuje příkaz pro hádání hádanky.
 * 
 * @author     Martin Havlík
 * @version    10.5.2017
 */
public class PrikazOdemkni implements IPrikaz {
    private static final String NAZEV = "odemkni";
    private HerniPlan herniPlan;
    
    /**
     * Konstruktor třídy, slouží pro získání odkazu na herníPlán
     *
     * @param    herniPlan odkaz na herníPlan
     */   
    public PrikazOdemkni(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }
    
    /**
     * Metoda představuje logiku příkazu pro odemknutí předmětu, či lokace.
     * Nejprve zkontroluje parametry (musí být zadaný právě 1),
     * ověří, že předmět s daným jménem je v aktuální lokaci, či lokace sousedí s aktulní lokací,
     * odemkne lokaci, předmět. Pokud je v předmetu (truhle), jinný předmět, tak ho zviditelný.
     * 
     * @param    parametry  pole parametrů zadaných na příkazové řádce
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím co mam odemknout";
        }
        
        if (parametry.length > 1) {
            return "Tomu nerozumím, můžu odemknout jen 1 predmet";
        }
        
        String nazevZamcene = parametry[0];
        Lokace lokace = herniPlan.getAktualniLokace().vratSousedniLokaci(nazevZamcene);
        Predmet predmet = herniPlan.getAktualniLokace().getPredmet(nazevZamcene);
        
        if (lokace != null && herniPlan.getBatoh().obsahujePredmet(lokace.getKlic())) {
            if (lokace.isZamcena()) {
                lokace.setZamcena(false);
                return herniPlan.getBatoh().getPredmet(lokace.getKlic()).getTextPouzijPoprve();
            } 
            else {
                return herniPlan.getBatoh().getPredmet(lokace.getKlic()).getTextPouzijOpakovane();
            }
        }
        
        if (predmet != null && herniPlan.getBatoh().obsahujePredmet(predmet.getKdeLzePouzit())) {
            if (predmet.isZamknuty()) {
                predmet.setZamknuty(false);
                
                if (herniPlan.getAktualniLokace().obsahujePredmet(predmet.getCoZiskam())) {
                    herniPlan.getAktualniLokace().getPredmet(predmet.getCoZiskam()).setViditelny(true); 
                }
                
                return predmet.getTextPouzijPoprve();
            }
            else {
                return predmet.getTextPouzijOpakovane();
            }
        }
        
        return "Předmět, či lokace: " + nazevZamcene + " nemůžeš odemknout. Potřebuješ klíč";
    }
    
    /**
     * Metoda vrací název příkazu. Jdená se o klíčové slovo,
     * kterým je možné odemknout přemět, či lokaci.
     * 
     * @return název příkazu
     */
       public String getNazev() {
        return NAZEV;
    }
}