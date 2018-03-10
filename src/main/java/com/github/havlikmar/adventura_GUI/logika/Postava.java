package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída Postava představuje záznam postav ve hře.
 * 
 * @author     Martin Havlík
 * @version    6.5.2017
 */
public class Postava {
    private String jmeno;
    private Predmet coChce;
    private Predmet coMa;
    private String mluvPred;
    private String mluvPo;
    private String recNechce;
    private String recChce;
    private boolean probehlaVymena=false;
    private String reseni;
    
    /**
     * Konstruktor pro vytvoření jednotlivých Postav. Tyto postavy chtějí směňovat věci.
     * 
     * @param   jmeno   jméno postavy
     * @param   coChce  co daná postava chce přinést
     * @param   coMa    co daná postava nabízí k výměně
     * @param   mluvPred    text co postava říká před výměnou
     * @param   mluvPo  text co postava říká po výměně
     * @param   recNechce   co řekne postava když dostane špatný předmět
     * @param   recChce co řekne postava když dostane správný předmět
     */
    public Postava(String jmeno, Predmet coChce, Predmet coMa, String mluvPred, String mluvPo, String recNechce, String recChce) {
        this.jmeno = jmeno;
        this.coChce = coChce;
        this.coMa = coMa;
        this.mluvPred = mluvPred;
        this.mluvPo = mluvPo;
        this.recNechce = recNechce;
        this.recChce = recChce;
    }
    
    /**
     * Konstruktor pro vytvoření jednotlivých Postav. Tyto postavy chtějí uhodnout hádanku.
     * 
     * @param   jmeno   jméno postavy
     * @param   reseni  řešení hádanky
     * @param   coMa    co daná postava nabízí k výměně
     * @param   mluvPred    text co postava říká před výměnou
     * @param   mluvPo  text co postava říká po výměně
     * @param   recNechce   co řekne postava když dostane špatný předmět
     * @param   recChce co řekne postava když dostane správný předmět
     */
    public Postava(String jmeno, String reseni, Predmet coMa, String mluvPred, String mluvPo, String recNechce, String recChce) {
        this.jmeno = jmeno;
        this.reseni = reseni;
        this.coMa = coMa;
        this.mluvPred = mluvPred;
        this.mluvPo = mluvPo;
        this.recNechce = recNechce;
        this.recChce = recChce;
    }
    
    /**
     * Metoda pro získání informací, zda je hádanka uhodnutá. 
     * 
     * @param   hadani  hráčovo řešení hádanky
     * @return  vrací informaci zda hráč vyřešil hádanku
     */
    public boolean jeUhadnuto(String hadani) {
        if (probehlaVymena) {
            return false;
        }
        
        return reseni.equals(hadani);
    }
    
    /**
     * Metoda pro získání projevu postavy
     * 
     * @return  vrací projev postavy
     */
    public String getProjev() {
        if (probehlaVymena) {
            return mluvPo;
        }else{
            if (coChce == null) {
                return mluvPred;
            }
            else {
                return mluvPred + " Chci za to: " + coChce;
            }
        }
    }
    
    /**
     * Getter pro získání předmětu který postava nabízí k výměně
     * 
     * @return  vrací předmět, který postava nabízí k výměně
     */
    public Predmet getCoMa() {
        return coMa;
    }
    
    /**
     * Metoda pro získání řeči postavy během výměny
     * 
     * @param   predmet predmet, který dal hráč k výměně.
     * @return  vrací projev postavy
     */
    public String getRecVymena(Predmet predmet) {
        if (probehlaVymena) {
            return "Výměna již proběhla";
        }
        
        if (predmet.equals(coChce)) {
            return recChce;
        }
        
        return recNechce;
    }
    
    /**
     * Metoda pro získání řeči postavy během hádání
     * 
     * @param   hadani řetezec slov, který je podle hráče řešení hádanky
     * @return  vrací projev postavy
     */
     public String getRecHadanka(String hadani) {
        if (probehlaVymena) {
            return "Už jsi hádanku uhádl";
        }
        
        if (hadani.equals(reseni)) {
            return recChce;
        }
        
        return recNechce;
    }
    
    /**
     * Getter pro získání jména postavy
     * 
     * @return  vrací jméno postavy
     */
    public String getJmeno() {
        return jmeno;
    }
    
    /**
     * Metoda pro informace zda je možná výměna
     * 
     * @param   predmet predmet, který hráč nabízí k výměně
     * @return  vrací zda je možná výměna
     */
    public boolean jeMoznaVymena(Predmet predmet) {
        if (probehlaVymena) {
            return false;
        }
        
        return predmet.equals(coChce);
    }
    
    /**
     * Setter pro nastavení zda proběhla výměna
     *  
     * @param    probehlaVymena  nové hodnoty zda proběhla výměna
     */
    public void setVymena(boolean probehlaVymena) {
        this.probehlaVymena = probehlaVymena;
    }
    
    /**
     * Getter pro získání předmětů, který postava chce
     * 
     * @return  vrací předmět, který postava chce
     */
    public Predmet getCoChce() {
        return coChce;
    }
}