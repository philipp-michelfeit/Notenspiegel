package de.musin.bsinfo.notenspiegelview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

class NotenDialog extends JDialog implements ActionListener {

	// ********************************************************************************************
	// *** Instanzfelder ***
	// ********************************************************************************************

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lbNote;
	private JComboBox<String> cbNoten;
	private JLabel lbGewicht;
	private ButtonGroup bg;
	private JRadioButton rb1;
	private JRadioButton rb2;
	private JButton btOk;
	private JButton btAbbrechen;

	private JFrame owner;

	private int wert;
	private int gewicht;
	private String name;
	private boolean abbruch;

	// ********************************************************************************************
	// *** Konstruktor ***
	// ********************************************************************************************

	public NotenDialog(JFrame owner) {

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

		// Instanzierung der Felder
		this.lbNote = new JLabel("Note:");
		this.cbNoten = new JComboBox<String>(new String[] { "1", "2", "3", "4", "5", "6" });
		this.lbGewicht = new JLabel("Gewicht:");
		this.rb1 = new JRadioButton("1", true);
		this.rb2 = new JRadioButton("2", false);
		this.bg = new ButtonGroup();
		this.bg.add(this.rb1);
		this.bg.add(this.rb2);

		this.btOk = new JButton("OK");
		this.btAbbrechen = new JButton("Abbrechen");

		this.owner = owner;

		this.wert = 0;
		this.gewicht = 0;
		this.name = "";
		this.abbruch = false;

		// Den Buttons einen ActionListener hinzufügen
		this.btOk.addActionListener(this);
		this.btAbbrechen.addActionListener(this);

		// Label Note hinzufügen
		gbc = this.makeGBC(0, 0, 1, 1, 1, 1);
		this.add(this.lbNote, gbc);

		// ComboBox Noten hinzufügen
		gbc = this.makeGBC(1, 0, 2, 1, 1, 1);
		this.cbNoten.setEditable(false);
		this.cbNoten.setSelectedIndex(0);
		this.add(this.cbNoten, gbc);

		// Label Gewicht hinzufügen
		gbc = this.makeGBC(0, 1, 1, 1, 1, 1);
		this.add(this.lbGewicht, gbc);

		// Radio Button 1 hinzufügen
		gbc = this.makeGBC(1, 1, 1, 1, 1, 1);
		this.add(this.rb1, gbc);

		// Radio Button 2 hinzufügen
		gbc = this.makeGBC(1, 2, 1, 1, 1, 1);
		this.add(this.rb2, gbc);

		// Button Ok hinzufügen
		gbc = this.makeGBC(1, 3, 1, 1, 1, 1);
		this.add(this.btOk, gbc);

		// Button Abbrechen hinzufügen
		gbc = this.makeGBC(2, 3, 1, 1, 1, 1);
		this.add(this.btAbbrechen, gbc);

		this.pack();

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

	public boolean isAbbruch() {
		return this.abbruch;
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
			this.setTitle("Neue Note für " + this.name);
			this.cbNoten.setSelectedIndex(0);
			this.wert = 1;
			this.rb1.setSelected(true);
			this.gewicht = 1;
			this.setLocation(this.owner.getX() + (this.owner.getWidth() - this.getWidth()) / 2,
					this.owner.getY() + (this.owner.getHeight() - this.getHeight()) / 2);
			this.abbruch = true;
			super.setVisible(true);
		} else {
			Scanner scan = new Scanner(this.cbNoten.getSelectedItem().toString());
			this.wert = scan.nextInt();
			this.gewicht = (this.rb1.isSelected()) ? 1 : 2;
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
