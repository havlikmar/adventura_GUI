package com.github.havlikmar.adventura_GUI.logika;

import java.util.Map;
import java.util.HashMap;

/**
 * Třída Batoh představuje batoh pro uchovávání věcí hráče
 * 
 * @author     Martin Havlík
 * @version    6.5.2017
 */
public class Batoh {
    private Map<String, Predmet> veciVBatohu;   
    private static final int MAX_POCET = 4;

    /**
     * Konstruktor pro vytvoření obsahu batohu
     */
    public Batoh() {
        veciVBatohu = new HashMap<>();
    }

    /**
     * Metoda pro vlozeci predmetu do batohu
     * 
     * @param   predmet  předmět, který se bude vkládat
     */
    public void vlozPredmet (Predmet predmet) {
        veciVBatohu.put(predmet.getNazev(), predmet);
    }

    /**
     * Vrací výpis bytohu, který může vypadat následovně: 
	 * batoh: lopata kladivo srp
     *
     * @return    výpis obsahu batohu
     */
    public String vypisBatohu() {
        String vypis = "batoh:";
        
        for (String predmet : veciVBatohu.keySet()) {
            vypis += " " + predmet;
        }
        
        return vypis;
    }
  
    /**
     * Metoda pro zjištění zda batoh je plný
     * 
     * @return  vrací zda batoh je plný
     */
    public boolean isPlny() {
        return veciVBatohu.size() >= MAX_POCET;
    }
    
    /**
     * Metoda pro získání informací, zda batoh obsahuje předmět
     * 
     * @param   nazevPredmetu  název předmetu pro který zjištujeme zda je v batohu
     * @return  vrací zda předmět je v batohu
     */
    public boolean obsahujePredmet(String nazevPredmetu) {
        return veciVBatohu.containsKey(nazevPredmetu);
    }
    
    /**
     * Metoda pro položení předmětu do lokace
     * 
     * @param   nazevPredmetu  název předmetu, který má být poležen
     * @return  vrací předmět, který má být položen
     */
    public Predmet polozPredmet(String nazevPredmetu) {
        return veciVBatohu.remove(nazevPredmetu);
    }
    
    /**
     * Metoda pro získání předmětu z batohu
     * 
     * @param   nazevPredmetu  předmět pro který chceme zjistit zda je v batohu
     * @return  vrací věc, na kterou jsme se dotazovali
     */
    public Predmet getPredmet(String nazevPredmetu) {
       return veciVBatohu.get(nazevPredmetu);
    }  
}