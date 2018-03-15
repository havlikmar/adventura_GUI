package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.Lokace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VypisPostava implements Observer{
	private HerniPlan plan;
	private ObservableList<String> observableList;
	private Controller controller;
	
	public VypisPostava(HerniPlan plan, Controller controller) {
		this.plan = plan;
		this.controller = controller;
		List<String> list = new ArrayList<String>();
		observableList = FXCollections.observableList(list);
		
		plan.pridejPosluchace(this);
		uprav();
	}
		
	public ObservableList getPostavy() {
		return observableList;
	}
	
	@Override
	public void uprav() {
		// TODO Auto-generated method stub
		observableList.removeAll(observableList);		
		for (String nazevPostavy : plan.getAktualniLokace().getPostavy().keySet()) {
			observableList.add(nazevPostavy);            
		}
	}

}
