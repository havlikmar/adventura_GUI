package com.github.havlikmar.adventura_GUI.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.havlikmar.adventura_GUI.logika.HerniPlan;
import com.github.havlikmar.adventura_GUI.logika.Lokace;
import com.github.havlikmar.adventura_GUI.logika.Predmet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VypisPredmetu implements Observer{
	private HerniPlan plan;
	private ObservableList<String> observableList;
	private Controller controller;
	
	public VypisPredmetu(HerniPlan plan, Controller controller) {
		this.plan = plan;
		this.controller = controller;
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
		uprav(plan.getAktualniLokace());
	}
		
	public ObservableList getPredmety() {
		return observableList;
	}
	
	@Override
	public void uprav(Lokace lokace) {
		// TODO Auto-generated method stub
		observableList.removeAll(observableList);		
		for (String nazevPredmetu : plan.getAktualniLokace().getPredmety().keySet()) {
            if (plan.getAktualniLokace().getPredmet(nazevPredmetu).isViditelny()) {
            	observableList.add(nazevPredmetu);
            }
		}
	}

}
