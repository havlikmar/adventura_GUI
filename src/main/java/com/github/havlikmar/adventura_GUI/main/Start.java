/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI.main;

import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.IHra;
import com.github.havlikmar.adventura_GUI.ui.TextoveRozhrani;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru určenou k dalším úpravám a rozšiřování
 *
 * @author     Jarmila Pavlíčková, Jan Říha
 * @version    LS 2016/2017
 */
public class Start {
    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args) {
        IHra hra = new Hra();
        TextoveRozhrani uiRozhrani = new TextoveRozhrani(hra);
        
        if (args.length > 0) {
            uiRozhrani.hraZeSouboru(args[0]);
        }
        else {
            uiRozhrani.hraj();
        }
    }
    
    private Start() {}
}