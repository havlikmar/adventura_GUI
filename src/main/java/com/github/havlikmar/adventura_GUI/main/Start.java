/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package com.github.havlikmar.adventura_GUI.main;

import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.IHra;
import com.github.havlikmar.adventura_GUI.ui.Controller;
import com.github.havlikmar.adventura_GUI.ui.TextoveRozhrani;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru určenou k dalším úpravám a rozšiřování
 *
 * @author     Jarmila Pavlíčková, Jan Říha
 * @version    LS 2016/2017 (úprava 17.3. Martin Havlík)
 */
public class Start extends Application {
    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args) {
    	if (args.length == 0) {
            launch(args);
        } else {
            if (args[0].equals("-text")) {
                IHra hra = new Hra();
                TextoveRozhrani ui = new TextoveRozhrani(hra);
                ui.hraj();
            } else {
                System.out.println("Neplatný parametr");
            }
        }
    }
    
    /**
	 * Metoda, ve které se konstruuje okno, kontroler a hra,
	 * která se předává kontroleru
	 * 
	 * @param	primaryStage stage hry
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../ui/MainWindow.fxml"));    	
    	Parent root = loader.load();

    	Controller controller = loader.getController(); 	
    	IHra hra = new Hra();
		controller.inicializuj(hra);

    	primaryStage.setScene(new Scene(root));
    	primaryStage.show();
    	primaryStage.setTitle("Adventura s grafickým rozhraním");
	}
}