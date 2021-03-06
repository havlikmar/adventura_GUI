package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;
import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Třída VypisInventare představuje tvorbu seznamu prvků inventáře pro GUI
 * 
 * @author     Martin Havlík
 * @version    17.3.2018
 */
public class VypisInventare implements Observer{
	private HerniPlan plan;
	private ObservableList<ImageView> observableList;
	
	/**
     * Konstruktor pro vytvoření seznamu prvků inventáře
     * 
     * @param    plan Herní plán
     */
	public VypisInventare(HerniPlan plan) {
		this.plan = plan;
		List<ImageView> list = new ArrayList<ImageView>();
		observableList = FXCollections.observableList(list);
		plan.getBatoh().pridejPosluchace(this);
		uprav();
	}
	
	/**
	 * Getter pro získání seznamu prvků inventáře
	 * 
	 * @return    výpis prvků seznamu inventáře
	 */
	public ObservableList<ImageView> getPredmety() {
		return observableList;
	}
	
	/**
	 * Metoda pro aktualizaci seznamu prvků inventáře při změně lokace
	 * 
	 */
	@Override
	public void uprav() {
		observableList.removeAll(observableList);		
		for (String nazevPredmetu : plan.getBatoh().getPredmety().keySet()) {
        	String URL = plan.getBatoh().getPredmet(nazevPredmetu).getObr();
        	Image obr = new Image(getClass().getResourceAsStream(URL));
        	ImageView image = new ImageView(obr);
        	image.setId(nazevPredmetu);
        	observableList.add(image);
		}
	}

}
