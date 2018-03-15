package com.github.havlikmar.adventura_GUI.ui;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.IHra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;
import com.github.havlikmar.adventura_GUI.main.Start;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Controller extends GridPane implements Observer {	
	@FXML private TextField 	textVstup;
	@FXML private TextArea  	textVystup;
	@FXML private ListView  	seznamMistnosti;
	@FXML private ComboBox  	prikazyMistnost;
	@FXML private ListView  	seznamPredmetu;
	@FXML private ComboBox  	prikazyPredmety;
	@FXML private ListView  	seznamInventare;
	@FXML private ComboBox  	prikazyInventar;
	@FXML private ListView  	seznamPostav;
	@FXML private ComboBox 		prikazyPostava;
	@FXML private ComboBox  	hadanka1;
	@FXML private ComboBox  	hadanka2;
	@FXML private ComboBox  	hadanka3;
	@FXML private ComboBox  	hadanka4;
	@FXML private ComboBox  	hadanka5;
	@FXML private ComboBox 	 	hadanka6;
	@FXML private ComboBox  	hadanka7;
	@FXML private ListView  	seznamBytosti;
	@FXML private Circle  		hrac;
	
	private IHra hra;
	private SeznamMistnosti dataMistnosti;
	private VypisPredmetu dataPredmetu;
	private VypisInventare dataInventar;
	private VypisPostava dataPostava;
	private VypisBytosti dataBytosti;
	private HerniPlan plan;
	
	/**
	 * Metoda čte příkaz ze vstupního textového pole
	 * a zpracuje ho...
	 */
	public void odesliPrikaz() {
//		TODO zpracovat příkaz a vepsat výsledek do výstupní oblasti
		String prikaz = textVstup.getText();
		String odpoved = "\n" + "--------------------------------------------" + "\n";
		odpoved = odpoved + hra.zpracujPrikaz(prikaz);
		textVystup.appendText(odpoved);
		textVstup.setText("");
	}
	
	@FXML public void klikMistnosti(MouseEvent arg0) {
		vypis(hra.zpracujPrikaz(prikazyMistnost.getSelectionModel().getSelectedItem() + " " + seznamMistnosti.getSelectionModel().getSelectedItem()));
	}
	
	@FXML public void klikHadej(ActionEvent arg0) {
		vypis(hra.zpracujPrikaz("hádej " + 
				hadanka1.getSelectionModel().getSelectedItem() + " " + 
				hadanka2.getSelectionModel().getSelectedItem() + " " +
				hadanka3.getSelectionModel().getSelectedItem() + " " +
				hadanka4.getSelectionModel().getSelectedItem() + " " +
				hadanka5.getSelectionModel().getSelectedItem() + " " +
				hadanka6.getSelectionModel().getSelectedItem() + " " +
				hadanka7.getSelectionModel().getSelectedItem()));
	}
	
	@FXML public void klikBytost(MouseEvent arg0) {
		vypis(hra.zpracujPrikaz("prozkoumej " + seznamBytosti.getSelectionModel().getSelectedItem()));
	}
	
	@FXML public void klikPostava(MouseEvent arg0) {
		if (prikazyPostava.getSelectionModel().getSelectedItem().equals("dej")) {
			vypis(hra.zpracujPrikaz(prikazyPostava.getSelectionModel().getSelectedItem() + " " + seznamInventare.getSelectionModel().getSelectedItem() + " " + seznamPostav.getSelectionModel().getSelectedItem()));
		}
		else {
		vypis(hra.zpracujPrikaz(prikazyPostava.getSelectionModel().getSelectedItem() + " " + seznamPostav.getSelectionModel().getSelectedItem()));
		}
	}
	
	@FXML public void klikPredmety(MouseEvent arg0) {
		vypis(hra.zpracujPrikaz(prikazyPredmety.getSelectionModel().getSelectedItem() + " " + seznamPredmetu.getSelectionModel().getSelectedItem()));
	}
	
	@FXML public void klikInventar(MouseEvent arg0) {
		if (prikazyPostava.getSelectionModel().getSelectedItem().equals("dej")) {
		}
		else {
			vypis(hra.zpracujPrikaz(prikazyInventar.getSelectionModel().getSelectedItem() + " " + seznamInventare.getSelectionModel().getSelectedItem()));
		}
	}

	

	public void vypis(String text){
		String odpoved = "\n" + "----------------------------------------------------" + "\n";
		odpoved = odpoved + text;
		textVystup.appendText(odpoved);
		textVstup.setText("");
	}
		
	public void inicializuj(IHra hra) {
		this.hra = hra;
		textVystup.appendText(hra.vratUvitani());
		plan = hra.getHerniPlan();
		dataMistnosti = new SeznamMistnosti(plan, this);
		seznamMistnosti.setItems(dataMistnosti.getMistnosti());
		prikazyMistnost.getItems().addAll("jdi", "odemkni", "prozkoumej");
		prikazyMistnost.getSelectionModel().selectFirst();
		
		dataPredmetu = new VypisPredmetu(plan, this);
		seznamPredmetu.setItems(dataPredmetu.getPredmety());
		prikazyPredmety.getItems().addAll("vezmi", "použij", "odemkni", "prozkoumej");
		prikazyPredmety.getSelectionModel().selectFirst();
		
		dataInventar = new VypisInventare(plan, this);
		seznamInventare.setItems(dataInventar.getPredmety());
		prikazyInventar.getItems().addAll("použij", "polož", "prozkoumej");
		prikazyInventar.getSelectionModel().selectFirst();

		dataBytosti = new VypisBytosti(plan, this);
		seznamBytosti.setItems(dataBytosti.getBytosti());
		
		dataPostava = new VypisPostava(plan, this);
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

	@Override
	public void uprav() {
		// TODO Auto-generated method stub
		hrac.setLayoutX(plan.getAktualniLokace().getPoziceX());
        hrac.setLayoutY(plan.getAktualniLokace().getPoziceY());
        System.out.println(plan.getAktualniLokace().getPoziceX());
        System.out.println(plan.getAktualniLokace().getPoziceY());
	}
}

