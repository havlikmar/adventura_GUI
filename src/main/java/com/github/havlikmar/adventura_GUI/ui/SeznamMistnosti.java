package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.Lokace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Třída SeznamMistnosti představuje tvorbu seznamu východů pro GUI
 * 
 * @author     Martin Havlík
 * @version    17.3.2018
 */
public class SeznamMistnosti implements Observer{
	private HerniPlan plan;
	private ObservableList<String> observableList;
	
	/**
     * Konstruktor pro vytvoření seznamu východů
     * 
     * @param    plan Herní plán
     */
	public SeznamMistnosti(HerniPlan plan) {
		this.plan = plan;
		List<String> list = new ArrayList<String>();
		observableList = FXCollections.observableList(list);
		plan.pridejPosluchace(this);
		for (Lokace lokace : plan.getLokace()) {
            lokace.pridejPosluchace(this);
		}
		uprav();
	}
		
	/**
	 * Getter pro získání seznamu východů
	 * 
	 * @return    výpis sousedních východů
	 */
	public ObservableList<String> getMistnosti() {
		return observableList;
	}
		
	/**
	 * Metoda pro aktualizaci seznamu východů při změně lokace
	 * 
	 */
	@Override
	public void uprav() {
		observableList.removeAll(observableList);
		for (Lokace mistnost : plan.getAktualniLokace().getVychody()) {
			if (plan.getAktualniLokace().vratSousedniLokaci(mistnost.getNazev()).isDosazitelny()) {
				observableList.add(mistnost.getNazev());
			}
		}
	}
}


