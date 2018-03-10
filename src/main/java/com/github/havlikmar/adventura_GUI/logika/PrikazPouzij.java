package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída PrikazPozij představuje příkaz pro pouzití předmětu
 * 
 * @author     Martin Havlík
 * @version    10.5.2017
 */
public class PrikazPouzij implements IPrikaz {
    private static final String NAZEV = "použij";
    private HerniPlan herniPlan;
    /**
     * Konstruktor třídy, slouží pro získání odkazu na herníPlán
     *
     * @param    herniPlan odkaz na herníPlan
     */   
    public PrikazPouzij(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }
    
    /**
     * Metoda představuje logiku příkazu pro použití předmětu.
     * Nejprve zkontroluje parametry (může mít právě jeden),
     * ověří, že hráč je ve správné lokaci a že v ní lze použít daný předmět,
     * pokud je předmět použije.
     * 
     * @param    parametry  pole parametrů zadaných na příkazové řádce
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevim co mam použít";
        }
        
        if (parametry.length > 1) {
            return "Tomu nerozumim, muzu použít jen 1 věc";
        }
       
        String nazevPredmetu = parametry[0];
        Lokace aktualniLokace = herniPlan.getAktualniLokace();
        Batoh batoh = herniPlan.getBatoh(); 
        Predmet predmetVBatohu = batoh.getPredmet(nazevPredmetu);
        Predmet predmet = aktualniLokace.getPredmet(nazevPredmetu);
        VolbaMoznosti volbaPodminky = jePrenositelny(nazevPredmetu);
        
        switch(volbaPodminky) {
            case PRENOSITELNA: 
                String coZiskam = predmetVBatohu.getCoZiskam();
                Lokace lokace = aktualniLokace.vratSousedniLokaci(coZiskam);
                String vypis = predmetVBatohu.getTextPouzijPoprve();
           
                if (lokace != null && !lokace.isDosazitelny()) {
                    lokace.setDosazitelny(true);
                    return vypis;
                }

                if (aktualniLokace.obsahujeBytost(coZiskam) && aktualniLokace.getBytost(coZiskam).isViditelny()) {
                    aktualniLokace.getBytost(coZiskam).setViditelny(false);
                    predmetVBatohu.setParametryPredmetu(coZiskam, aktualniLokace, herniPlan, batoh);
                    vypis = vypis + HerniPlan.getZpravaSouboj(herniPlan.getBatoh());
                    return vypis;
                }
        
                if (aktualniLokace.obsahujePredmet(coZiskam) && !aktualniLokace.getPredmet(coZiskam).isViditelny()) {
                    aktualniLokace.getPredmet(coZiskam).setViditelny(true);
                    predmetVBatohu.setParametryPredmetu(nazevPredmetu, aktualniLokace, herniPlan, batoh);
                    return vypis;
                }
                
                return batoh.getPredmet(nazevPredmetu).getTextPouzijOpakovane();

            case NEPRENOSITELNA:
                coZiskam = predmet.getCoZiskam();
                lokace = aktualniLokace.vratSousedniLokaci(coZiskam);
            
                if (lokace != null && lokace.isDosazitelny()) {
                    predmet.setParametryPredmetu(nazevPredmetu, aktualniLokace, herniPlan, batoh);
                    
                    if (predmet.isViditelny()) {
                        return "Aby jsi mohl s předmětem: " + nazevPredmetu + " pohnout musíš mít v batohu klíč";
                    }
                    
                    lokace.setDosazitelny(false);
                    return predmet.getTextPouzijPoprve();
                }      
                
                return predmet.getTextPouzijOpakovane();
                
            default:
                return "Předmět: " + nazevPredmetu + " zde nemůžeš použít";
        }
    }
    
    /**
     * Metoda představuje kontrolu podmínek a následné rozlišení 
     * zda jde o přenositelný, či nepřenostilný předmět.
     * 
     * @param    nazevPredmetu  název předmětu, který chci použít
     * @return   vrací se číslo, který reprezentuje zda jde o přenostilný, nepřenostilelný předmět, či o předmět nesplňující podmínky.
     */
    public VolbaMoznosti jePrenositelny(String nazevPredmetu) {
         Batoh batoh = herniPlan.getBatoh();
         Lokace aktualniLokace = herniPlan.getAktualniLokace();
         Predmet predmetVBatohu = batoh.getPredmet(nazevPredmetu);
         Predmet predmet = aktualniLokace.getPredmet(nazevPredmetu);

         if (batoh.obsahujePredmet(nazevPredmetu) && predmetVBatohu.getKdeLzePouzit() != null && predmetVBatohu.getKdeLzePouzit().equals(aktualniLokace.getNazev())) {
             return VolbaMoznosti.PRENOSITELNA;
         }
         
         if (aktualniLokace.obsahujePredmet(nazevPredmetu) && predmet.getKdeLzePouzit() != null && predmet.getKdeLzePouzit().equals(aktualniLokace.getNazev())) {
             return VolbaMoznosti.NEPRENOSITELNA;
         }
         
         return VolbaMoznosti.NENI;
    }
    
    /**
     * Metoda vrací název příkazu. Jedná se o klíčové slovo,
     * kterým je možné použít předmět
     * 
     * @return    nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }
}