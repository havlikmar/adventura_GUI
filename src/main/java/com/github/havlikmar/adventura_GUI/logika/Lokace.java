/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI.logika;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Trida Lokace - popisuje jednotlivé lokace (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Lokace" reprezentuje jedno místo (místnost, prostor, ...) ve scénáři hry.
 * Lokace může mít sousední lokace připojené přes východy. Pro každý východ
 * si lokace ukládá odkaz na sousedící lokace.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, upravil Martin Havlík
 * @version    LS 2016/2017, (upraveno 16.5.2017)
 */
public class Lokace {
    private String nazev;
    private String popis;
    private boolean dosazitelny = true;
    private boolean zamcena = false;
    private String klic;
    private Set<Lokace> vychody;   
    private Map<String, Predmet> predmety;
    private Map<String, Postava> postavy;
    private Map<String, Bytost> bytosti;

    /**
     * Vytvoření lokace se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem". Slouží k vytvoření normální či znepřístupnělé lokace
     *
     * @param    nazev nazev lokace, jednoznačný identifikátor, jedno slovo nebo víceslovný název bez mezer
     * @param    popis Popis lokace
     * @param    dosazitelny hodnota zda je lokace dosažitelná
     */
    public Lokace(String nazev, String popis, boolean dosazitelny) {
        this.nazev = nazev;
        this.popis = popis;
        this.dosazitelny = dosazitelny;
        vychody = new HashSet<>();
        predmety = new HashMap<>();
        postavy = new HashMap<>();
        bytosti = new HashMap<>();
    }
    
    /**
     * Vytvoření lokace se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem". Slouží k vytvoření zamčené lokace
     *
     * @param   nazev   nazev lokace, jednoznačný identifikátor, jedno slovo nebo víceslovný název bez mezer
     * @param   popis   Popis lokace
     * @param   zamcena hodnota zda je lokace zamčená
     * @param   klic    věc potřebná k odemčení lokace
     */
    public Lokace(String nazev, String popis, boolean zamcena, String klic) {
        this.nazev = nazev;
        this.popis = popis;
        this.zamcena = zamcena;
        this.klic = klic;
        vychody = new HashSet<>();
        predmety = new HashMap<>();
        postavy = new HashMap<>();
        bytosti = new HashMap<>();
    }

    /**
     * Definuje východ z lokace (sousední/vedlejsi lokace). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední lokace uvedena
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední lokace).
     * Druhé zadání stejné lokace tiše přepíše předchozí zadání (neobjeví
     * se žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param    vedlejsi lokace, která sousedi s aktualní lokací.
     */
    public void setVychod(Lokace vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou lokací. Překrývá se metoda equals ze
     * třídy Object. Dvě lokace jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param     o object, který se má porovnávat s aktuálním
     * @return    hodnotu true, pokud má zadaná lokace stejný název, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Lokace)) {
            return false;    // pokud parametr není typu Lokace, vrátíme false
        }
        // přetypujeme parametr na typ Lokace 
        Lokace druha = (Lokace) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druha.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object.
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }
      
    /**
     * Vrací název lokace (byl zadán při vytváření lokace jako parametr
     * konstruktoru)
     *
     * @return    název lokace
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis lokace, který může vypadat následovně: Jsi v
     * mistnosti/lokaci vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     * Pokud není ani u dané skupiny ani jedna věc, tak se daná skupina nevypíše.
     *
     * @return    dlouhý popis lokace
     */
    public String dlouhyPopis() {
        String vypis = "Jsi v mistnosti/lokaci " + popis + ".\n"
                        + popisVychodu();
                        
        if (!seznamPredmetu().equals("předměty:")) {
            vypis = vypis + "\n" + seznamPredmetu();
        }
                
        if (!seznamPostav().equals("postavy:")) {
            vypis = vypis + "\n" + seznamPostav();
        }
        
        if (!seznamBytosti().equals("bytosti:")) {
            vypis = vypis + "\n" + seznamBytosti();
        }
                
        return vypis;
    }
    
    /**
     * Vrací výpis předmětů, který může vypadat následovně: 
     * předměty: chleba rohlíky šátečky
     *
     * @return    výpis předmětů v lokaci
     */
    private String seznamPredmetu() {
        String seznam = "předměty:";
        
        for (String nazevPredmetu : predmety.keySet()) {
            if (predmety.get(nazevPredmetu).isViditelny()) {
                seznam += " " + nazevPredmetu;
            }
        }
        
        return seznam;
    }
    
    /**
     * Vrací výpis bytostí, který může vypadat následovně: 
     * bytosti: vlkodlak zlobr medvídě
     *
     * @return    výpis bytostí v lokaci
     */
    private String seznamBytosti() {
        String seznam = "bytosti:";
        
        for (String nazevBytosti : bytosti.keySet()) {
            if (bytosti.get(nazevBytosti).isViditelny()) {
                seznam += " " + nazevBytosti;
            }
        }
        
        return seznam;
    }
    
    /**
     * Vrací výpis postav, který může vypadat následovně: 
     * postavy: převozník pastevec myslivec
     *
     * @return    výpis postav v lokaci
     */
    private String seznamPostav() {
        String seznam = "postavy:";
        
        for (String nazevPostavy : postavy.keySet()) {
            seznam += " " + nazevPostavy;
        }
        
        return seznam;
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return    popis východů - názvů sousedních lokací
     */
    private String popisVychodu() {
        String vracenyText = "vychody:";
        
        for (Lokace sousedni : vychody) {
            if (sousedni.isDosazitelny()) {
                vracenyText += " " + sousedni.getNazev();
            }
        }
        
        return vracenyText;
    }

    /**
     * Vrací lokaci, která sousedí s aktuální lokací a jejíž název je zadán
     * jako parametr. Pokud lokace s udaným jménem nesousedí s aktuální
     * lokací, vrací se hodnota null.
     *
     * @param     nazevSouseda Jméno sousední lokace (východu)
     * @return    lokace, která se nachází za příslušným východem, nebo hodnota null, pokud lokace zadaného jména není sousedem.
     */
    public Lokace vratSousedniLokaci(String nazevSouseda) {
        List<Lokace>hledaneLokace = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneLokace.isEmpty()) {
            return null;
        }
        else {
            return hledaneLokace.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující lokace, se kterými ta lokace sousedí.
     * Takto získaný seznam sousedních lokací nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Lokace.
     *
     * @return    nemodifikovatelná kolekce lokací (východů), se kterými tato lokace sousedí.
     */
    public Collection<Lokace> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }
    
    /**
     * Metoda slouží k přidání předmětu do lokace
     *
     * @param   predmet predmět, který chci přidat
     */
    public void vlozPredmet(Predmet predmet) {
        predmety.put(predmet.getNazev(), predmet);
    }

    /**
     * Metoda slouží k odebrání předmětu z lokace
     *
     * @param   nazevPredmetu název předmětu, který chci odebrat
     * @return  předmět který odebírám
     */
    public Predmet vezmiPredmet(String nazevPredmetu) {
        return predmety.remove(nazevPredmetu);
    }
    
    /**
     * Metoda slouží k zjištění zda v lokaci je předmět
     *
     * @param   nazevPredmetu název předmětu, pro který chci zjistit zda je v lokaci
     * @return  informace zda je předmět v lokaci
     */
    public boolean obsahujePredmet(String nazevPredmetu) {
        return predmety.containsKey(nazevPredmetu);
    }
    
    /**
     * Metoda slouží k zjištění zda v lokaci je bytost
     *
     * @param   nazevBytosti název bytosti, pro který chci zjistit zda je v lokaci
     * @return  informace zda je bytost v lokaci
     */
    public boolean obsahujeBytost(String nazevBytosti) {
        return bytosti.containsKey(nazevBytosti);
    }
    
    /**
     * Metoda slouží k zjištění zda v lokaci je postava
     *
     * @param   nazevPostavy název postavy, pro který chci zjistit zda je v lokaci
     * @return  informace zda je postava v lokaci
     */
    public boolean obsahujePostavu(String nazevPostavy) {
        return postavy.containsKey(nazevPostavy);
    }
    
    /**
     * Metoda slouží k přidání postavy do lokace
     *
     * @param   postava postava, kterou chci přidat
     */
    public void vlozPostavu(Postava postava) {
        postavy.put(postava.getJmeno(), postava);
    }
    
    /**
     * Metoda slouží k přidání bytosti do lokace
     *
     * @param   bytost bytost, kterou chci přidat
     */
    public void vlozBytost(Bytost bytost) {
        bytosti.put(bytost.getNazev(), bytost);
    }
    
    /**
     * Metoda slouží k zjištění přístupu k instancím třídy Postava
     *
     * @param   nazevPostavy název postavy, pro kterou chci získat její instanci
     * @return  hledaná postava
     */
    public Postava getPostava(String nazevPostavy) {
       return postavy.get(nazevPostavy);
    }   
    
    /**
     * Metoda slouží k zjištění přístupu k instancím třídy Predmet
     *
     * @param   nazevPredmetu název předmětu, pro který chci získat její instanci
     * @return  hledaný předmět
     */
    public Predmet getPredmet(String nazevPredmetu) {
       return predmety.get(nazevPredmetu);
    }  
    
    /**
     * Metoda slouží k zjištění přístupu k instancím třídy Bytost
     *
     * @param   nazevBytosti název bytosti, pro kterou chci získat její instanci
     * @return  hledaná bytost
     */
    public Bytost getBytost(String nazevBytosti) {
       return bytosti.get(nazevBytosti);
    }  
    
    /**
     * Metoda slouží k zjištění zda lokace je dosažitelná
     *
     * @return  informace zda je lokace dosažitelná
     */
    public boolean isDosazitelny() {
       return dosazitelny;
    }  
    
    /**
     * Metoda slouží k nastavení dosažitelnosti lokace
     *
     * @param   dosazitelny nová dosažitelnost lokace
     */
    public void setDosazitelny(boolean dosazitelny) {
        this.dosazitelny = dosazitelny;
    }  
    
    /**
     * Metoda slouží k zjištění zda lokace je zamčená
     *
     * @return  informace zda je lokace zamčená
     */
    public boolean isZamcena() {
       return zamcena;
    } 
    
    /**
     * Metoda slouží k nastavení zamčení lokace
     *
     * @param   zamcena nová informace zda je lokace zamčená
     */
    public void setZamcena(boolean zamcena) {
        this.zamcena = zamcena;
    }
    
    /**
     * Metoda slouží k nastavení popisu lokace
     *
     * @param   popis nový popis lokace
     */
    public void setPopis(String popis) {
        this.popis = popis;
    }  
    
    /**
     * Metoda slouží k zjištění klíče pro odemčení lokace
     *
     * @return  klíč, sloužící k odemčení lokace
     */
    public String getKlic() {
        return klic;
    }
}
