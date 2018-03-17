package com.github.havlikmar.adventura_GUI.logika;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.ui.Observable;
import com.github.havlikmar.adventura_GUI.ui.Observer;

/**
 * Třída Bytost představuje záznam bytostí ve hře.
 * 
 * @author     Martin Havlík
 * @version    17.3.2018
 */
public class Bytost implements Observable{
    private String nazev;
    private String popis;
    private boolean viditelny = true;
    private List<Observer> posluchaci;
    
    /**
     * Konstruktor pro vytvoření jednotlivých bytostí.
     * 
     * @param   nazev   název bytosti
     * @param   popis   popis bytosti
     */
    public Bytost(String nazev, String popis) {
        this.nazev = nazev;
        this.popis = popis;
        posluchaci = new ArrayList<Observer>();
    }
    
    /**
     * Konstruktor pro vytvoření jednotlivých bytostí. Slouží pro potřeby testů
     * 
     * @param   nazev  		název bytosti
     * @param   popis  		popis bytosti
     * @param   viditelny	viditelnost bytosti
     */
    public Bytost(String nazev, String popis, boolean viditelny) {
        this.nazev = nazev;
        this.popis = popis;
        this.viditelny = viditelny;
        posluchaci = new ArrayList<Observer>();
    }
    
    /**
     * Getter pro získání informací, zda je bytost viditelná.
     * 
     * @return   vrací informace zda postava je viditelná
     */
    public boolean isViditelny() {
        return viditelny;
    }
    
    /**
     * Setter pro nastavení viditelnosti bytosti.
     *  
     * @param    viditelny  nové hodnoty viditelnosti
     */
    public void setViditelny(boolean viditelny) {
        this.viditelny = viditelny;
    	oznamPosluchaci();
    }
    
    /**
     * Getter pro získání názvu bytosti
     * 
     * @return   vrací název bytosti
     */
    public String getNazev() {
        return nazev;
    }
    
    /**
     * Getter pro získání popisu bytosti
     *     
     * @return   vrací popis bytosti
     */
    public String getPopis() {
        return popis;
    }
    
    /**
	 * Metoda pro přidání posluchače k odběru
	 * 
	 *  @param	observer posluchač
	 */
    public void pridejPosluchace(Observer observer) {
    	posluchaci.add(observer);
    }
    
    /**
	 * Metoda pro odebrání posluchače z odběru
	 * 
	 *  @param	observer posluchač
	 */
	public void odeberPosluchace(Observer observer){
		posluchaci.remove(observer);
	}
	
	/**
	 * Metoda pro oznámenín posluchačů o změně
	 * 
	 */
	public void oznamPosluchaci(){
		for(Observer observer: posluchaci) {
			observer.uprav();
		}
	}
}