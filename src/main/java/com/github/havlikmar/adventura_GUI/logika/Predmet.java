package com.github.havlikmar.adventura_GUI.logika;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.ui.Observable;
import com.github.havlikmar.adventura_GUI.ui.Observer;

/**
 * Třída Predmet představuje záznam předmětů ve hře.
 * 
 * @author     Martin Havlík
 * @version    13.3.2018
 */
public class Predmet implements Observable{
    private String nazev;
    private String popis;
    private boolean prenositelny;
    private boolean viditelny = true;
    private boolean zamcena = false;
    private String kdeLzePouzit;
    private String coZiskam;
    private String textPouzijPoprve;
    private String textPouzijOpakovane;
    private List<Observer> posluchaci;
    private String obr;
    
    /**
     * Konstruktor pro vytvoření jednotlivých předmětů.
     * 
     * @param   nazev   název předmětu
     * @param   popis   popis předmětu
     * @param   prenositelny    informace zda předmět jde přenést
     * @param   viditelny   informace o viditelnosti předmětu
     * @param  	obr   URI obrázku
     */
    public Predmet(String nazev, String popis, boolean prenositelny, boolean viditelny, String obr) {
        this.nazev = nazev;
        this.popis = popis;
        this.prenositelny = prenositelny;
        this.viditelny = viditelny;
        posluchaci = new ArrayList<Observer>();
        this.obr = obr;
    }
    
    /**
     * Konstruktor pro vytvoření jednotlivých předmětů. Jde o předměty které se dají použít, popř. s nimi pohnout.
     * 
     * @param   nazev   název předmětu
     * @param   popis   popis předmětu
     * @param   prenositelny    informace zda předmět jde přenést
     * @param   viditelny   informace o viditelnosti předmětu
     * @param   kdeLzePouzit    kde lze předmět použít
     * @param   coZiskam    co získám, když použiju předmět
     * @param   textPouzijPoprve    text který se vypíše, když poprvé použiju předmět
     * @param   textPouzijOpakovane text který se vypíše, když opakovaně použiju předmět
     * @param  	obr   URI obrázku
     */
    public Predmet(String nazev, String popis, boolean prenositelny, boolean viditelny, String kdeLzePouzit, String coZiskam, String textPouzijPoprve, String textPouzijOpakovane, String obr) {
        this.nazev = nazev;
        this.popis = popis;
        this.prenositelny = prenositelny;
        this.viditelny = viditelny;
        this.kdeLzePouzit = kdeLzePouzit;
        this.coZiskam = coZiskam;
        this.textPouzijPoprve = textPouzijPoprve;
        this.textPouzijOpakovane = textPouzijOpakovane;
        posluchaci = new ArrayList<Observer>();
        this.obr = obr;
    }
    
    /**
     * Konstruktor pro vytvoření jednotlivých předmětů. Jde o předměty které jsou zamčené a dají se odemknout.
     * 
     * @param   nazev   název předmětu
     * @param   popis   popis předmětu
     * @param   prenositelny    informace zda předmět jde přenést
     * @param   kdeLzePouzit    klíč k odemknutí předmětu
     * @param   coZiskam    co získám, když použiju předmět
     * @param   textPouzijPoprve    text který se vypíše, když poprvé použiju předmět
     * @param   textPouzijOpakovane text který se vypíše, když opakovaně použiju předmět
     * @param   zamcena informace zda předmět je zamčený
     * @param  	obr   URI obrázku
     */
    public Predmet(String nazev, String popis, boolean prenositelny, String kdeLzePouzit, String coZiskam, String textPouzijPoprve, String textPouzijOpakovane, boolean zamcena, String obr) {
        this.nazev = nazev;
        this.popis = popis;
        this.prenositelny = prenositelny;
        this.coZiskam = coZiskam;
        this.textPouzijPoprve = textPouzijPoprve;
        this.textPouzijOpakovane = textPouzijOpakovane;
        this.zamcena = zamcena;
        this.kdeLzePouzit = kdeLzePouzit;
        posluchaci = new ArrayList<Observer>();
        this.obr = obr;
    }
    
    /**
     * Getter pro získání názvu předmětu.
     * 
     * @return  vrací název předmětu
     */
    public String getNazev() {
        return nazev;
    }
    
    /**
     * Getter pro získání popisu předmětu.
     * 
     * @return  vrací popis předmětu
     */
    public String getPopis() {
        return popis;
    }
    
    /**
     * Getter pro získání informace zda předmět je přenositelný.
     * 
     * @return  vrací zda předmět je přenositelný
     */
    public boolean isPrenositelny() {
        return prenositelny;
    }
    
    /**
     * Setter pro nastavení popis předmětu
     *  
     * @param    popis  nový popis předmětu
     */
    public void setPopis(String popis) {
        this.popis = popis;
    }
    
    /**
     * Setter pro nastavení viditelnosti předmětu.
     *  
     * @param   viditelny   nová viditelnost předmětu
     */
    public void setViditelny(boolean viditelny) {
        this.viditelny = viditelny;
        this.oznamPosluchaci();
    }
    
    /**
     * Getter pro získání informace zda předmět je viditelný.
     * 
     * @return  vrací zda předmět je zamčený
     */
    public boolean isViditelny() {
        return viditelny;
    }
    
    /**
     * Setter pro nastavení přenositelnosti předmětu.
     *  
     * @param   prenositelny    nová přenositelnost předmětu
     */
    public void setPrenositelny(boolean prenositelny) {
        this.prenositelny = prenositelny;
    }
    
    /**
     * Setter pro nastavení zamčení předmětu.
     *  
     * @param   zamcena nová informace zda je předmět zamčený
     */
    public void setZamknuty(boolean zamcena) {
        this.zamcena = zamcena;
    }
    
    /**
     * Getter pro získání kde lze předmět použít.
     * 
     * @return  vrací název lokace kde lze předmět použít
     */
    public String getKdeLzePouzit() {
        return kdeLzePouzit;
    }
    
    /**
     * Getter pro získání textu který se vypíše po prvním použití předmětu.
     * 
     * @return  vrací text, který se vypíše po prvním použití předmětu
     */
    public String getTextPouzijPoprve() {
        return textPouzijPoprve;
    }
    
    /**
     * Getter pro získání textu který se vypíše po opakovaném použití předmětu.
     * 
     * @return  vrací text, který se vypíše po opakovaném použití předmětu
     */
    public String getTextPouzijOpakovane() {
        return textPouzijOpakovane;
    }
    
    /**
     * Getter pro získání názvu předmětu, který hráč dostane od postavy
     * 
     * @return  vrací název předmětu, který hráč dostane od postavy
     */
    public String getCoZiskam() {
        return coZiskam;
    }
    
    /**
     * Getter pro získání informace zda předmět je zamčený.
     * 
     * @return  vrací zda předmět je zamčený
     */
    public boolean isZamknuty() {
        return zamcena;
    }
    
    /**
     * Metoda představuje logiku pro úpravu popisů lokací a předmětů.
     * Slouží pro úpravu textu a nastavení parametrů určených pro ukončení hry
     * 
     * @param    predmet    představuje cíl příkazu použil, lokace představuje aktuální lokaci
     * @param    lokace představuje aktuální lokaci
     * @param    herniPlan  představuje odkaz na herniPlan
     * @param    batoh  představuje batoh hráče
     */
    public void setParametryPredmetu (String predmet, Lokace lokace, HerniPlan herniPlan, Batoh batoh) {
        if (predmet.equals("vlkodlak")) {
            kdeLzePouzit = "chaloupka";
            coZiskam = "tajemný_jezdec";
            textPouzijPoprve = "Jezdec se na tebe vrhá. Ty ho zasáhneš, bohužel meč se v něm zasekl.\nVypadá to, že jezdec je nazastavitelný a pronásleduje tě.";
            textPouzijOpakovane = "Jezdec je již mrtvý.";
            lokace.setPopis("konec jeskyně, kde se nachází mrtvý vlkodlak.");

            if (lokace.obsahujePredmet("klíč")) {
                lokace.getPredmet("klíč").setViditelny(true);
            }
        }
        
        if (predmet.equals("medvídě")) {
            lokace.setPopis("les ve kterým jsou pytláci.");
            HerniPlan.setZachranilMedvide(true);
        }
        
        if (predmet.equals("tajemný_jezdec")) {
            HerniPlan.setPotkalJezdce(true);
            lokace.setPopis("v chaloupce se nachází truhla");
            
            if (lokace.obsahujePredmet("klíč_od_truhly")) {
                lokace.getPredmet("klíč_od_truhly").setViditelny(true);
            }
        }
        
        if (predmet.equals("bylinky")) {
            batoh.polozPredmet(predmet);
            lokace.setPopis("temný les s vchodem do jeskyně.\nUprostřed je studně a kousek od ní chaloupka. Nad jeskyní je hromada kamení s kládou, která z něj vyčnívá.");
        }
        
        if (predmet.equals("kláda") && batoh.obsahujePredmet("klíč")) {
            lokace.setPopis("temný les, se zasypaným vchodem do jeskyně.\nUprostřed je studně a kousek od ní chaloupka.");
            HerniPlan.setZavalenyVchod(true);
            lokace.getPredmet(predmet).setViditelny(false);
        }
    }
    
    /**
     * Metoda slouží k výpisu názvu předmětu po volání daného objektu
     * 
     * @return  vrací název předmětu
     */
    @Override
    public String toString() {
        return nazev;
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
	
	/**
     * Getter pro získání URI obrazku.
     * 
     * @return  URI obrázku
     */
	public String getObr() {
		return obr;
	}
}