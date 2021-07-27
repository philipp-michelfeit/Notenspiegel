package de.musin.bsinfo.notenspiegelview;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

class FachDialog extends JDialog implements ActionListener {

	// ********************************************************************************************
	// *** Instanzfelder ***
	// ********************************************************************************************

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbName;
	private JTextField tfEingabe;
	private JButton btOk;
	private JButton btAbbrechen;

	private JFrame owner;

	private String name;
	private boolean abbruch;

	// ********************************************************************************************
	// *** Konstruktor ***
	// ********************************************************************************************

	public FachDialog(JFrame owner) {
		
		super(owner, true);

		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		// Standard-LAF festlegen
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

		// Instanziierung der Felder
		this.lbName = new JLabel("Name:");
		this.tfEingabe = new JTextField();
		this.btOk = new JButton("OK");
		this.btAbbrechen = new JButton("Abbrechen");
		
		this.owner = owner;
		
		this.name = "";
		this.abbruch = false;

		// Den Buttons einen ActionListener hinzufügen
		this.btOk.addActionListener(this);
		this.btAbbrechen.addActionListener(this);

		// Label Name hinzufügen
		gbc = this.makeGBC(0, 0, 1, 1, 1, 1);
		this.add(this.lbName, gbc);

		// Textfeld hinzufügen
		gbc = this.makeGBC(1, 0, 2, 1, 1, 1);
		this.add(this.tfEingabe, gbc);

		// Ok-Button hinzufügen
		gbc = this.makeGBC(1, 1, 1, 1, 1, 1);
		this.add(this.btOk, gbc);

		// Abbrechen-Button hinzufügen
		gbc = this.makeGBC(2, 1, 1, 1, 1, 1);
		this.add(this.btAbbrechen, gbc);

		this.pack();

	}

	// ********************************************************************************************
	// *** Getter ***
	// ********************************************************************************************

	public boolean isAbbruch() {
		return this.abbruch;
	}
	
	public String getName() {
		return this.name;
	}

	// ********************************************************************************************
	// *** Setter ***
	// ********************************************************************************************

	// Owner liefert vor Anzeige den Namen des Faches
	public void setName(String name) {
		this.name = name;
	}

	// ********************************************************************************************
	// *** Weitere Methoden ***
	// ********************************************************************************************

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btOk) {
			this.abbruch = false;
		}
		this.setVisible(false);

	}

	public void setVisible(boolean visible) {
		
		if (visible) {
			this.setTitle("Neues Fach");
			this.setLocation(this.owner.getX() + (this.owner.getWidth() - this.getWidth()) / 2,
					this.owner.getY() + (this.owner.getHeight() - this.getHeight()) / 2); 
			this.tfEingabe.setText("");
			this.abbruch = true;
			super.setVisible(true);

		} else {
			String str = this.tfEingabe.getText().trim();
			this.name = str;
			super.setVisible(false);
		}
	}

	// Factory-Methode, um ein Standard-GridBagConstraints-Objekt zu erzeugen
	private GridBagConstraints makeGBC(int gridx, int gridy, int gridwidth, int gridheight,
			int weightx, int weighty) {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.fill = GridBagConstraints.BOTH; // Standard-Werte
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = new Insets(1, 1, 1, 1);

		return gbc;
	}

}
