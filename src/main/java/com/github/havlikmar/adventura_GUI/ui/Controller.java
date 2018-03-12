package com.github.havlikmar.adventura_GUI.ui;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.IHra;
import com.github.havlikmar.adventura_GUI.logika.Lokace;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller extends GridPane implements Observer {	
	@FXML private TextField textVstup;
	@FXML private TextArea  textVystup;
	@FXML private ListView  seznamMistnosti;
	@FXML private ComboBox  prikazyMistnost;
	@FXML private ListView  seznamPredmetu;
	@FXML private ComboBox  prikazyPredmety;
	@FXML private ListView  seznamInventare;
	@FXML private ComboBox  prikazyInventar;
	
	private IHra hra;
	private SeznamMistnosti dataMistnosti;
	private VypisPredmetu dataPredmetu;
	private VypisInventare dataInventar;
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
	
	@FXML public void klikPredmety(MouseEvent arg0) {
		vypis(hra.zpracujPrikaz(prikazyPredmety.getSelectionModel().getSelectedItem() + " " + seznamPredmetu.getSelectionModel().getSelectedItem()));
	}
	
	@FXML public void klikInventar(MouseEvent arg0) {
		vypis(hra.zpracujPrikaz(prikazyInventar.getSelectionModel().getSelectedItem() + " " + seznamInventare.getSelectionModel().getSelectedItem()));
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
		prikazyMistnost.getItems().addAll("jdi", "odemkni");
		prikazyMistnost.getSelectionModel().selectFirst();
		
		dataPredmetu = new VypisPredmetu(plan, this);
		seznamPredmetu.setItems(dataPredmetu.getPredmety());
		prikazyPredmety.getItems().addAll("vezmi", "použij", "odemkni");
		prikazyPredmety.getSelectionModel().selectFirst();
		
		dataInventar = new VypisInventare(plan, this);
		seznamInventare.setItems(dataInventar.getPredmety());
		prikazyInventar.getItems().addAll("použij", "polož");
		prikazyInventar.getSelectionModel().selectFirst();
	}

	@Override
	public void uprav(Lokace lokace) {
		// TODO Auto-generated method stub
		
	}
}

