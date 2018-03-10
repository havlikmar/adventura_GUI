package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.Lokace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SeznamMistnosti implements Observer{
	private HerniPlan plan;
	private ObservableList<String> observableList;
	private Controller controller;
		
	public SeznamMistnosti(HerniPlan plan, Controller controller) {
		this.plan = plan;
		this.controller = controller;
		List<String> list = new ArrayList<String>();
		observableList = FXCollections.observableList(list);
		plan.pridejPosluchace(this);
		uprav(plan.getAktualniLokace());
	}
		
	public ObservableList getMistnosti() {
		return observableList;
	}
			
	@Override
	public void uprav(Lokace lokace) {
		observableList.removeAll(observableList);
		for (Lokace mistnost : plan.getAktualniLokace().getVychody()) {
			if (plan.getAktualniLokace().vratSousedniLokaci(mistnost.getNazev()).isDosazitelny()) {
				observableList.add(mistnost.getNazev());
			}
		}
	}
}


