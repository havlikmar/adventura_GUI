package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;
import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Třída VypisInventare představuje tvorbu seznamu prvků inventáře pro GUI
 * 
 * @author     Martin Havlík
 * @version    17.3.2018
 */
public class VypisInventare implements Observer{
	private HerniPlan plan;
	private ObservableList<String> observableList;
	
	/**
     * Konstruktor pro vytvoření seznamu prvků inventáře
     * 
     * @param    plan Herní plán
     */
	public VypisInventare(HerniPlan plan) {
		this.plan = plan;
		List<String> list = new ArrayList<String>();
		observableList = FXCollections.observableList(list);
		plan.getBatoh().pridejPosluchace(this);
		uprav();
	}
	
	/**
	 * Getter pro získání seznamu prvků inventáře
	 * 
	 * @return    výpis prvků seznamu inventáře
	 */
	public ObservableList<String> getPredmety() {
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
					observableList.add(nazevPredmetu);
		}
	}

}
