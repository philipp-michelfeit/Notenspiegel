package eu.bsinfo.notenspiegelcontroller;

import eu.bsinfo.notenspiegelmodel.NotenspiegelModel;

/**
 * @author michel0p
 * 
 */
public class NotenspiegelController {

	// ********************************************************************************************
	// *** Instanzfelder ***
	// ********************************************************************************************

	private NotenspiegelModel nm;

	// ********************************************************************************************
	// *** Konstruktor ***
	// ********************************************************************************************

	/**
	 * @param nm
	 *            NotenspiegelModel
	 */
	public NotenspiegelController(NotenspiegelModel nm) {
		this.nm = nm;
	}

	// ********************************************************************************************
	// *** Getter ***
	// ********************************************************************************************

	// keine benötigt!

	// ********************************************************************************************
	// *** Setter ***
	// ********************************************************************************************

	// keine benötigt!

	// ********************************************************************************************
	// *** Weitere Methoden ***
	// ********************************************************************************************

	/**
	 * Methode, die vor dem Beenden des Programms aufgerufen wird
	 */
	public final void stop() {

		// this.nm.save(1);
		// this.nm.save(2);
		this.nm.makePDF();
		this.nm.save(3);
		System.exit(0);
	}

	/**
	 * @param name
	 *            Name des Faches
	 */
	public void addFach(String name) {

		// Aufruf der Methode der Klasse NotenspiegelModel
		this.nm.addFach(name);
	}

	/**
	 * @param name
	 *            Note für Fach
	 * @param wert
	 *            Wert
	 * @param gewicht
	 *            Gewicht
	 */
	public void addNote(String name, int wert, int gewicht) {

		// Aufruf der Methode der Klasse NotenspiegelModel
		this.nm.addNote(name, wert, gewicht);
	}

}
