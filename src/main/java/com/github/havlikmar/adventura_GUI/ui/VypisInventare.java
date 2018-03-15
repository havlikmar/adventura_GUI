package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.Lokace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VypisInventare implements Observer{
	private HerniPlan plan;
	private ObservableList<String> observableList;
	private Controller controller;
	
	public VypisInventare(HerniPlan plan, Controller controller) {
		this.plan = plan;
		this.controller = controller;
		List<String> list = new ArrayList<String>();
		observableList = FXCollections.observableList(list);
		plan.getBatoh().pridejPosluchace(this);
		uprav();
	}
		
	public ObservableList getPredmety() {
		return observableList;
	}
	
	@Override
	public void uprav() {
		// TODO Auto-generated method stub
		observableList.removeAll(observableList);		
		for (String nazevPredmetu : plan.getBatoh().getPredmety().keySet()) {
            if (plan.getBatoh().getPredmet(nazevPredmetu).isViditelny()) {
            	observableList.add(nazevPredmetu);
            }
		}
	}

}
