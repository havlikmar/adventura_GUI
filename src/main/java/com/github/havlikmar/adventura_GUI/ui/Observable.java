package com.github.havlikmar.adventura_GUI.ui;

/**
 * Interface Observable představuje interface Observable.
 * Slouží pro zachycení sledovaného prvku a k oznámení posluchači.
 * 
 * @author     Martin Havlík
 * @version    17.3.2018
 */
public interface Observable {
	
	/**
	 * Metoda pro přidání posluchače k odběru
	 * 
	 *  @param	observer posluchač
	 */
	public void pridejPosluchace(Observer observer);
	
	/**
	 * Metoda pro odebrání posluchače z odběru
	 * 
	 *  @param	observer posluchač
	 */
	public void odeberPosluchace(Observer observer);
	
	/**
	 * Metoda pro oznámenín posluchačů o změně
	 * 
	 */
	public void oznamPosluchaci();
}