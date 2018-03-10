package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída Bytost představuje záznam bytostí ve hře.
 * 
 * @author     Martin Havlík
 * @version    6.5.2017
 */
public class Bytost {
    private String nazev;
    private String popis;
    private boolean viditelny = true;
    
    /**
     * Konstruktor pro vytvoření jednotlivých bytostí.
     * 
     * @param   nazev   název bytosti
     * @param   popis   popis bytosti
     */
    public Bytost(String nazev, String popis) {
        this.nazev = nazev;
        this.popis = popis;
    }
    
    /**
     * Konstruktor pro vytvoření jednotlivých bytostí. Slouží pro potřeby testů
     * 
     * @param   nazev   název bytosti
     * @param   popis   popis bytosti
     * @param   viditelny   viditelnost bytosti
     */
    public Bytost(String nazev, String popis, boolean viditelny) {
        this.nazev = nazev;
        this.popis = popis;
        this.viditelny = viditelny;
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
}