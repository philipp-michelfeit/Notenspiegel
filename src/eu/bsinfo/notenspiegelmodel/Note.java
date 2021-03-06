package eu.bsinfo.notenspiegelmodel;

import java.io.Serializable;

class Note implements Serializable {

	// ********************************************************************************************
	// *** Instanzfelder ***
	// ********************************************************************************************

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private int wert;
	private int gewicht;

	// ********************************************************************************************
	// *** Konstruktor ***
	// ********************************************************************************************

	public Note(int w, int g) {
		this.wert = w;
		this.gewicht = g;
	}

	// ********************************************************************************************
	// *** Getter ***
	// ********************************************************************************************

	public int getWert() {
		return this.wert;
	}

	public int getGewicht() {
		return this.gewicht;
	}

	// ********************************************************************************************
	// *** Setter ***
	// ********************************************************************************************

	// keine benötigt !

	// ********************************************************************************************
	// *** Weitere Methoden ***
	// ********************************************************************************************

	@Override
	public String toString() {
		return this.wert + " (" + this.gewicht + ")";
	}

}
