/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI.logika;

/**
 * Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jarmila Pavlickova, Luboš Pavlíček, Jan Říha, upravil Martin Havlík
 * @version    LS 2016/2017 (upraveno 19.5.2017)
 */
class PrikazNapoveda implements IPrikaz {
    private static final String NAZEV = "nápověda";
    private SeznamPrikazu platnePrikazy;

    /**
    * Konstruktor třídy
    *
    * @param    platnePrikazy seznam příkazů, které je možné ve hře použít, aby je nápověda mohla zobrazit uživateli.
    */    
    public PrikazNapoveda(SeznamPrikazu platnePrikazy) {
        this.platnePrikazy = platnePrikazy;
    }

    /**
     * Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     * vcelku primitivní zpráva a seznam dostupných příkazů.
     *
     * @return    napoveda ke hre
     */
    @Override
    public String proved(String... parametry) {
        return "Tvým úkolem je najít poklad tajemného jezdce.\n"
                + "Traduje se, že žije v chaloupce v lese.\n"
                + "Pokud nevíš, jak zadat příkazy, přečti si\n"
                + "uživatelskou příručku. Když měníš předmět s\n"
                + "postavou, předměty se vymění automaticky. Pokud\n"
                + "získáš předmět přes příkaz použij, hádej či otevři, předmět\n"
                + "vypadne do aktuální lokace a musíš ho vzít. Aby jsi zjistil\n"
                + "jeho název, musíš použít příkaz prozkoumej. Pokud si nevíš\n"
                + "rady podívej se na návod do uživatelské příručky\n"
                + "\n"
                + "Můžeš zadat tyto příkazy:\n"
                + platnePrikazy.vratNazvyPrikazu();
    }

     /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání).
     *  
     * @return    nazev prikazu
     */
    @Override
      public String getNazev() {
        return NAZEV;
     }
}