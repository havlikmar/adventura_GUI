package com.github.havlikmar.adventura_GUI.ui;

import java.net.URL;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.Hra;
import com.github.havlikmar.adventura_GUI.logika.IHra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Třída Controller představuje tvorbu a logiku GUI pro adventuru
 * 
 * @author     Martin Havlík
 * @version    17.3.2018
 */
public class Controller extends GridPane implements Observer {	
	@FXML private TextField 			textVstup;
	@FXML private TextArea  			textVystup;
	@FXML private ListView<String>  	seznamMistnosti;
	@FXML private ComboBox<String>  	prikazyMistnost;
	@FXML private ListView<ImageView>  	seznamPredmetu;
	@FXML private ComboBox<String>  	prikazyPredmety;
	@FXML private ListView<ImageView>  	seznamInventare;
	@FXML private ComboBox<String>  	prikazyInventar;
	@FXML private ListView<String>  	seznamPostav;
	@FXML private ComboBox<String> 		prikazyPostava;
	@FXML private ComboBox<String>  	hadanka1;
	@FXML private ComboBox<String>  	hadanka2;
	@FXML private ComboBox<String>  	hadanka3;
	@FXML private ComboBox<String>  	hadanka4;
	@FXML private ComboBox<String>  	hadanka5;
	@FXML private ComboBox<String> 	 	hadanka6;
	@FXML private ComboBox<String>  	hadanka7;
	@FXML private ListView<String>  	seznamBytosti;
	@FXML private Circle  				hrac;
	@FXML private Button  				odesli;
	@FXML private ToolBar  				hadej;
	@FXML private MenuItem 				konec;
	
	private IHra hra;
	private SeznamMistnosti dataMistnosti;
	private VypisPredmetu dataPredmetu;
	private VypisInventare dataInventar;
	private VypisPostava dataPostava;
	private VypisBytosti dataBytosti;
	private HerniPlan plan;
	private URL napoveda;
	
	/**
	 * Metoda pro zpracování kliku na nápovědu
	 * 
	 * @param	arg0 akce kliknutí na prvek menu
	 */
	@FXML public void klikniNapoveda(ActionEvent event){
	napoveda = this.getClass().getResource("/ui/napoveda.html");
	WebView webView = new WebView();
	WebEngine engine = webView.getEngine();
	engine.load(napoveda.toString());
	Scene scene = new Scene(webView);
	Stage stage = new Stage();
	stage.setScene(scene);
	stage.show();
	stage.setWidth (1200);
	stage.setHeight(720);
	}
	
	/**
	 * Metoda čte příkaz ze vstupního textového pole
	 * a zpracuje ho...
	 */
	public void odesliPrikaz() {
		String prikaz = textVstup.getText();
		String odpoved = "\n" + "--------------------------------------------" + "\n";
		odpoved = odpoved + hra.zpracujPrikaz(prikaz);
		textVystup.appendText(odpoved);
		textVstup.setText("");
		konecHry();
	}
	
	/**
	 * Metoda zpracuje ukončení hry
	 * a zablokuje ovládací prvky
	 */
	public void konecHry() {
		if(hra.konecHry()) {
			textVystup.appendText("\n" + hra.vratEpilog());
			hadej.setDisable(true);
			textVstup.setDisable(true);
			odesli.setDisable(true);
			seznamMistnosti.setDisable(true);
			textVstup.setDisable(true);
			seznamMistnosti.setDisable(true);
			prikazyMistnost.setDisable(true);
			seznamPredmetu.setDisable(true);
			prikazyPredmety.setDisable(true);
			seznamInventare.setDisable(true);
			prikazyInventar.setDisable(true);
			seznamPostav.setDisable(true);
			prikazyPostava.setDisable(true);
			seznamBytosti.setDisable(true);
			konec.setDisable(true);
			
		}
	}
	
	/**
	 * Metoda pro zpracování kliku na listu místností
	 * 
	 * @param	arg0 akce kliknutí na prvek listu
	 */
	@FXML public void klikMistnosti(MouseEvent arg0) {
		vypis(hra.zpracujPrikaz(prikazyMistnost.getSelectionModel().getSelectedItem() + " " + seznamMistnosti.getSelectionModel().getSelectedItem()));
		konecHry();
	}

	/**
	 * Metoda pro zpracování kliku na tlačítko hádej
	 * Slouží k aktivaci hádání
	 * 
	 * @param	arg0 akce kliknutí na tlačítko hádej
	 */
	@FXML public void klikHadej(ActionEvent arg0) {
		vypis(hra.zpracujPrikaz("hádej " + 
				hadanka1.getSelectionModel().getSelectedItem() + " " + 
				hadanka2.getSelectionModel().getSelectedItem() + " " +
				hadanka3.getSelectionModel().getSelectedItem() + " " +
				hadanka4.getSelectionModel().getSelectedItem() + " " +
				hadanka5.getSelectionModel().getSelectedItem() + " " +
				hadanka6.getSelectionModel().getSelectedItem() + " " +
				hadanka7.getSelectionModel().getSelectedItem()));
		konecHry();
	}
	
	/**
	 * Metoda pro zpracování kliku na listu bytostí
	 * 
	 * @param	arg0 akce kliknutí na list bytostí
	 */
	@FXML public void klikBytost(MouseEvent arg0) {
		vypis(hra.zpracujPrikaz("prozkoumej " + seznamBytosti.getSelectionModel().getSelectedItem()));
		konecHry();
	}
	
	/**
	 * Metoda pro zpracování kliku na listu postav
	 * 
	 * @param	arg0 akce kliknutí na list postav
	 */
	@FXML public void klikPostava(MouseEvent arg0) {
		if (prikazyPostava.getSelectionModel().getSelectedItem().equals("dej")) {
			vypis(hra.zpracujPrikaz(prikazyPostava.getSelectionModel().getSelectedItem() + " " + seznamInventare.getSelectionModel().getSelectedItem().getId() + " " + seznamPostav.getSelectionModel().getSelectedItem()));
		}
		else {
		vypis(hra.zpracujPrikaz(prikazyPostava.getSelectionModel().getSelectedItem() + " " + seznamPostav.getSelectionModel().getSelectedItem()));
		}
		konecHry();
	}
	
	/**
	 * Metoda pro zpracování kliku na listu předmětů v místnosti
	 * 
	 * @param	arg0 akce kliknutí na list předmětů
	 */
	@FXML public void klikPredmety(MouseEvent arg0) {
		if(seznamPredmetu.getSelectionModel().getSelectedItem() == null) {
		}
		else
		{
		vypis(hra.zpracujPrikaz(prikazyPredmety.getSelectionModel().getSelectedItem() + " " + seznamPredmetu.getSelectionModel().getSelectedItem().getId()));
		konecHry();
		}
	}
	
	/**
	 * Metoda pro zpracování kliku na prvek menu Konec
	 * 
	 * @param	arg0 akce kliknutí na prvek konec
	 */
	@FXML public void klikKonec(ActionEvent arg0) {
		vypis(hra.zpracujPrikaz("konec"));
		konecHry();
	}
	
	/**
	 * Metoda pro zpracování kliku na prvek menu Nová hra
	 * 
	 * @param	arg0 akce kliknutí na prvek nová hra
	 */
	@FXML public void klikNovaHra(ActionEvent arg0) {
        vypis("");
		hadej.setDisable(false);
		textVstup.setDisable(false);
		odesli.setDisable(false);
		seznamMistnosti.setDisable(false);
		textVstup.setDisable(false);
		seznamMistnosti.setDisable(false);
		prikazyMistnost.setDisable(false);
		seznamPredmetu.setDisable(false);
		prikazyPredmety.setDisable(false);
		seznamInventare.setDisable(false);
		prikazyInventar.setDisable(false);
		seznamPostav.setDisable(false);
		prikazyPostava.setDisable(false);
		seznamBytosti.setDisable(false);
		konec.setDisable(false);
		hra = new Hra();

		textVystup.appendText(hra.vratUvitani());
		plan = hra.getHerniPlan();
		dataMistnosti = new SeznamMistnosti(plan);
		seznamMistnosti.setItems(dataMistnosti.getMistnosti());
		prikazyMistnost.getSelectionModel().selectFirst();
		
		dataPredmetu = new VypisPredmetu(plan);
		seznamPredmetu.setItems(dataPredmetu.getPredmety());
		prikazyPredmety.getSelectionModel().selectFirst();
		
		dataInventar = new VypisInventare(plan);
		seznamInventare.setItems(dataInventar.getPredmety());
		prikazyInventar.getSelectionModel().selectFirst();

		dataBytosti = new VypisBytosti(plan);
		seznamBytosti.setItems(dataBytosti.getBytosti());
		
		dataPostava = new VypisPostava(plan);
		seznamPostav.setItems(dataPostava.getPostavy());
		prikazyPostava.getSelectionModel().selectFirst();
		
		hrac.setLayoutX(82.00);
        hrac.setLayoutY(308.00);
        
        plan.pridejPosluchace(this);
	}
	
	/**
	 * Metoda pro zpracování kliku na prvek listu inventář
	 * 
	 * @param	arg0 akce kliknutí na prvek listu inventář
	 */
	@FXML public void klikInventar(MouseEvent arg0) {
		if (prikazyPostava.getSelectionModel().getSelectedItem().equals("dej")) {
		}
		else {
			if(seznamInventare.getSelectionModel().getSelectedItem() == null) {
			}
			else
			{
			vypis(hra.zpracujPrikaz(prikazyInventar.getSelectionModel().getSelectedItem() + " " + seznamInventare.getSelectionModel().getSelectedItem().getId()));
			}
		}
		konecHry();
	}

	/**
	 * Metoda pro vypis na obrazovku.
	 * Používá se v případě, kdy je potřeba vypsat akci prováděnou grafickými prvky.
	 * 
	 * @param	text text, který se vypisuje na obrazovku
	 */
	public void vypis(String text){
		String odpoved = "\n" + "----------------------------------------------------" + "\n";
		odpoved = odpoved + text;
		textVystup.appendText(odpoved);
		textVstup.setText("");
	}
		
	/**
	 * Metoda pro iniciaci ovládacích prvků
	 * 
	 * @param	hra adventura, kterou hrajeme
	 */
	public void inicializuj(IHra hra) {
		this.hra = hra;
		textVystup.appendText(hra.vratUvitani());
		plan = hra.getHerniPlan();
		dataMistnosti = new SeznamMistnosti(plan);
		seznamMistnosti.setItems(dataMistnosti.getMistnosti());
		prikazyMistnost.getItems().addAll("jdi", "odemkni");
		prikazyMistnost.getSelectionModel().selectFirst();
		
		dataPredmetu = new VypisPredmetu(plan);
		seznamPredmetu.setItems(dataPredmetu.getPredmety());
		prikazyPredmety.getItems().addAll("vezmi", "použij", "odemkni", "prozkoumej");
		prikazyPredmety.getSelectionModel().selectFirst();
		
		dataInventar = new VypisInventare(plan);
		seznamInventare.setItems(dataInventar.getPredmety());
		prikazyInventar.getItems().addAll("použij", "polož", "prozkoumej");
		prikazyInventar.getSelectionModel().selectFirst();

		dataBytosti = new VypisBytosti(plan);
		seznamBytosti.setItems(dataBytosti.getBytosti());
		
		dataPostava = new VypisPostava(plan);
		seznamPostav.setItems(dataPostava.getPostavy());
		prikazyPostava.getItems().addAll("mluv", "dej");
		prikazyPostava.getSelectionModel().selectFirst();
		
		hadanka1.getItems().addAll("pes", "slepice", "zrní" , "nic");
		hadanka2.getItems().addAll("pes", "slepice", "zrní" , "nic");
		hadanka3.getItems().addAll("pes", "slepice", "zrní" , "nic");
		hadanka4.getItems().addAll("pes", "slepice", "zrní" , "nic");
		hadanka5.getItems().addAll("pes", "slepice", "zrní" , "nic");
		hadanka6.getItems().addAll("pes", "slepice", "zrní" , "nic");
		hadanka7.getItems().addAll("pes", "slepice", "zrní" , "nic");
		
		plan.pridejPosluchace(this);
	}

	/**
	 * Metoda pro aktualizaci souřadnic hráče.
	 * Slouží pro označení hráče na mapě
	 * 
	 */
	@Override
	public void uprav() {
		hrac.setLayoutX(plan.getAktualniLokace().getPoziceX());
        hrac.setLayoutY(plan.getAktualniLokace().getPoziceY());
	}
}

