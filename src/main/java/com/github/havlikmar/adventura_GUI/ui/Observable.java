package com.github.havlikmar.adventura_GUI.ui;

public interface Observable {
	public void pridejPosluchace(Observer observer);
	public void odeberPosluchace(Observer observer);
	public void oznamPosluchaci();
}