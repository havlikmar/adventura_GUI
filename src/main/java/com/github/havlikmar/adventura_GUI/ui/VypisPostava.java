package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;
import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Třída VypisPostava představuje tvorbu seznamu postav pro GUI
 * 
 * @author     Martin Havlík
 * @version    17.3.2018
 */
public class VypisPostava implements Observer{
	private HerniPlan plan;
	private ObservableList<String> observableList;
	
	/**
     * Konstruktor pro vytvoření seznamu postav
     * 
     * @param    plan Herní plán
     */
	public VypisPostava(HerniPlan plan) {
		this.plan = plan;
		List<String> list = new ArrayList<String>();
		observableList = FXCollections.observableList(list);
		plan.pridejPosluchace(this);
		uprav();
	}
	
	/**
	 * Getter pro získání seznamu postav
	 * 
	 * @return    výpis prvků seznamu postav
	 */
	public ObservableList<String> getPostavy() {
		return observableList;
	}
	
	/**
	 * Metoda pro aktualizaci seznamu prvků postav při změně lokace
	 * 
	 */
	@Override
	public void uprav() {
		observableList.removeAll(observableList);		
		for (String nazevPostavy : plan.getAktualniLokace().getPostavy().keySet()) {
			observableList.add(nazevPostavy);            
		}
	}

}
