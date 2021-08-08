package de.musin.bsinfo.notenspiegel;

import de.musin.bsinfo.notenspiegelcontroller.NotenspiegelController;
import de.musin.bsinfo.notenspiegelmodel.NotenspiegelModel;
import de.musin.bsinfo.notenspiegelview.NotenspiegelView;

/**
 * @author michel0p
 * 
 */
public class Notenspiegel {

	// ********************************************************************************************
	// *** Instanzfelder ***
	// ********************************************************************************************

	private NotenspiegelModel nm;
	private NotenspiegelView nv;
	private NotenspiegelController nc;

	// ********************************************************************************************
	// *** Konstruktor ***
	// ********************************************************************************************

	/**
	 * Konstruktor der Klasse {@link Notenspiegel}
	 */
	public Notenspiegel() {

		if (this.nm == null && this.nv == null && this.nc == null) {
			// this.nm = new NotenspiegelModel(1);
			// this.nm = new NotenspiegelModel(2);
			this.nm = new NotenspiegelModel(3);
			this.nc = new NotenspiegelController(this.nm);
			this.nv = new NotenspiegelView(this.nm, this.nc);
		}
	}

	// ********************************************************************************************
	// *** Getter ***
	// ********************************************************************************************

	// keine benötigt !

	// ********************************************************************************************
	// *** Setter ***
	// ********************************************************************************************

	// keine benötigt !

	// ********************************************************************************************
	// *** Weitere Methoden ***
	// ********************************************************************************************

	/**
	 * @param args
	 *            Argumente
	 */
	public static void main(String[] args) {
		new Notenspiegel();
	}

}
