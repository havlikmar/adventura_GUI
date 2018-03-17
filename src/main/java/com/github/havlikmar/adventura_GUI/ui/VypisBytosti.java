package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.logika.Bytost;
import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.Lokace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Třída VypisBytosti představuje tvorbu seznamu bytostí pro GUI
 * 
 * @author     Martin Havlík
 * @version    17.3.2018
 */
public class VypisBytosti implements Observer{
	private HerniPlan plan;
	private ObservableList<String> observableList;
	
	/**
     * Konstruktor pro vytvoření seznamu bytostí
     * 
     * @param    plan Herní plán
     */
	public VypisBytosti(HerniPlan plan) {
		this.plan = plan;
		List<String> list = new ArrayList<String>();
		observableList = FXCollections.observableList(list);
		plan.pridejPosluchace(this);
		for (Lokace lokace : plan.getLokace()) {
            for (Bytost bytost: lokace.getBytosti().values()){
            bytost.pridejPosluchace(this);
            }
		}
		uprav();
	}
	
	/**
	 * Getter pro získání seznamu bytostí
	 * 
	 * @return    výpis seznamu bytostí
	 */
	public ObservableList<String> getBytosti() {
		return observableList;
	}
	
	/**
	 * Metoda pro aktualizaci seznamu bytostí při změně lokace
	 * 
	 */
	@Override
	public void uprav() {
		observableList.removeAll(observableList);		
		for (String nazevBytosti : plan.getAktualniLokace().getBytosti().keySet()) {
            if (plan.getAktualniLokace().getBytost(nazevBytosti).isViditelny()) {
            	observableList.add(nazevBytosti);
            }
		}
	}

}
