package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.Lokace;
import com.github.havlikmar.adventura_GUI.logika.Predmet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Třída VypisPredmetu představuje tvorbu seznamu předmětů v lokaci pro GUI
 * 
 * @author     Martin Havlík
 * @version    17.3.2018
 */
public class VypisPredmetu implements Observer{
	private HerniPlan plan;
	private ObservableList<String> observableList;
	
	/**
     * Konstruktor pro vytvoření seznamu předmětů
     * 
     * @param    plan Herní plán
     */
	public VypisPredmetu(HerniPlan plan) {
		this.plan = plan;
		List<String> list = new ArrayList<String>();
		observableList = FXCollections.observableList(list);

		plan.pridejPosluchace(this);
		for (Lokace lokace : plan.getLokace()) {
            lokace.pridejPosluchace(this);
		}
		
		for (Lokace lokace : plan.getLokace()) {
            for (Predmet predmet: lokace.getPredmety().values()){
            predmet.pridejPosluchace(this);
            }
		}
		uprav();
	}
		
	/**
	 * Getter pro získání seznamu předmětů
	 * 
	 * @return    výpis prvků seznamu předmětů
	 */
	public ObservableList<String> getPredmety() {
		return observableList;
	}
	
	/**
	 * Metoda pro aktualizaci seznamu prvků seznamu předmětů při změně lokace
	 * 
	 */
	@Override
	public void uprav() {
		observableList.removeAll(observableList);		
		for (String nazevPredmetu : plan.getAktualniLokace().getPredmety().keySet()) {
            if (plan.getAktualniLokace().getPredmet(nazevPredmetu).isViditelny()) {
            	observableList.add(nazevPredmetu);
            }
		}
	}

}
