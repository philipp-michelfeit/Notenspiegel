package de.musin.bsinfo.notenspiegelmodel;

import java.io.Serializable;
import java.util.ArrayList;

class Fach implements Serializable {

	// ********************************************************************************************
	// *** Instanzfelder ***
	// ********************************************************************************************

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private double durchschnitt;
	private ArrayList<Note> noten;

	// ********************************************************************************************
	// *** Konstruktor ***
	// ********************************************************************************************

	public Fach(String name) {
		this.name = name;
		this.durchschnitt = 0.0;
		this.noten = new ArrayList<Note>();
	}

	// ********************************************************************************************
	// *** Getter ***
	// ********************************************************************************************

	public String getName() {
		return this.name;
	}

	public double getDurchschnitt() {
		return this.durchschnitt;
	}

	public ArrayList<Note> getNoten() {

		ArrayList<Note> tmp = new ArrayList<Note>();

		for (int i = 0; i < this.noten.size(); i++) {
			tmp.add(this.noten.get(i));
		}
		return tmp;
	}

	// ********************************************************************************************
	// *** Setter ***
	// ********************************************************************************************

	// keine benötigt !

	// ********************************************************************************************
	// *** Weitere Methoden ***
	// ********************************************************************************************

	public void addNote(Note n) {
		
		this.noten.add(n);
		this.durchschnitt = 0.0;

		double gewichtssumme = 0.0;
		
		for (int i = 0; i < this.noten.size(); i++) {
			Note tmp = this.noten.get(i);
			this.durchschnitt = this.durchschnitt + tmp.getWert() * tmp.getGewicht();
			gewichtssumme = gewichtssumme + tmp.getGewicht();
		}
		this.durchschnitt = this.durchschnitt / gewichtssumme;
	}
}
