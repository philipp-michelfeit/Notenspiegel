package eu.bsinfo.notenspiegelview;

import eu.bsinfo.notenspiegelcontroller.NotenspiegelController;
import eu.bsinfo.notenspiegelmodel.NotenspiegelModel;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author michel0p
 */
public class NotenspiegelView extends JFrame implements ActionListener {

    // ********************************************************************************************
    // *** Instanzfelder ***
    // ********************************************************************************************

    private static final long serialVersionUID = 1L;

    private NotenspiegelController nc;
    private NotenspiegelModel nm;

    private JTable tbNoten;
    private JScrollPane spScrollPane;
    private JButton btAddFach;
    private JButton btAddNote;

    private FachDialog diaFach;
    private NotenDialog diaNoten;

    // ********************************************************************************************
    // *** Konstruktor ***
    // ********************************************************************************************

    /**
     * @param nm NotenspiegelModel
     * @param nc NotenspiegelController
     */
    public NotenspiegelView(NotenspiegelModel nm, NotenspiegelController nc) {

        // Aufruf des Konstruktors der Oberklasse (JFrame)
        super("Notenspiegel");

        this.setLayout(new GridBagLayout());

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

        GridBagConstraints gbc = new GridBagConstraints();

        // Instanzierung von Model und Controller
        this.nc = nc;
        this.nm = nm;

        // Instanzierung der Dialoge
        this.diaFach = new FachDialog(this);
        this.diaNoten = new NotenDialog(this);

        // Instanzierung der Felder
        this.tbNoten = new JTable(this.nm);
        this.spScrollPane = new JScrollPane(this.tbNoten);
        this.btAddFach = new JButton("Neues Fach");
        this.btAddNote = new JButton("Neue Note ");

        // Den Buttons ActionListener hinzufügen
        this.btAddNote.addActionListener(this);
        this.btAddFach.addActionListener(this);

        // JScrollPane hinzufügen
        gbc = this.makeGBC(0, 0, 2, 1, 1, 1);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(this.spScrollPane, gbc);

        // Button AddFach hinzufügen
        gbc = this.makeGBC(0, 1, 1, 1, 1, 1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        // gbc.insets = new Insets(1, 1, 1, 1);
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(this.btAddFach, gbc);

        // Button AddNote hinzufügen
        gbc = this.makeGBC(1, 1, 1, 1, 1, 1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        // gbc.insets = new Insets(1, 1, 1, 1);
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(this.btAddNote, gbc);

        // WindowListener hinzufügen
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                stop();
            }
        });

        // Standard-Schließ-Operation festlegen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Fenstergröße ändern erlauben / verbieten
        this.setResizable(true);

        // Fenster automatisch positionieren
        this.pack();

        // Minimal mögliche Fenstergröße setzen
        this.setMinimumSize(new Dimension(600, 200));

        // Fenster in der Bildschirmmitte positionieren
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);

        // Sichtbarkeit des Fensters einstellen
        this.setVisible(true);
    }

    // ********************************************************************************************
    // *** Getter ***
    // ********************************************************************************************

    /**
     *
     */
    public void stop() {
        nc.stop();
    }

    /**
     * @return NotenspiegelController
     */
    public NotenspiegelController getNotenspiegelController() {
        return this.nc;
    }

    /**
     * @return NotenspiegelModel
     */
    public NotenspiegelModel getNotenspiegelModel() {
        return this.nm;
    }

    /**
     * @return JScrollPane
     */
    public JScrollPane getScrollPane() {
        return this.spScrollPane;
    }

    /**
     * @return JTable
     */
    public JTable getTable() {
        return this.tbNoten;
    }

    /**
     * @return JButton
     */
    public JButton getBtAddFach() {
        return this.btAddFach;
    }

    /**
     * @return JButton
     */
    public JButton getBtAddNote() {
        return this.btAddNote;
    }

    // ********************************************************************************************
    // *** Setter ***
    // ********************************************************************************************

    // keine benötigt !

    // ********************************************************************************************
    // *** Weitere Methoden ***
    // ********************************************************************************************

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
        gbc.insets = new Insets(0, 0, 0, 0);

        return gbc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton bt = (JButton) e.getSource();

        if (bt == this.btAddFach) {

            this.diaFach.setVisible(true);
            if (!this.diaFach.isAbbruch()) {
                String name = this.diaFach.getName();
                if (!name.equals("")) {
                    this.nc.addFach(name);
                } else {
                    JOptionPane.showMessageDialog(this, "Fach nicht korrekt angegeben",
                            "Notenspiegel", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (bt == this.btAddNote) {

            if (this.tbNoten.getSelectedRow() != -1) {
                String name = this.tbNoten.getValueAt(this.tbNoten.getSelectedRow(), 0).toString();
                this.diaNoten.setName(name);
                this.diaNoten.setVisible(true);
                if (!this.diaNoten.isAbbruch()) {
                    int wert = this.diaNoten.getWert();
                    int gewicht = this.diaNoten.getGewicht();
                    this.nc.addNote(name, wert, gewicht);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Kein Fach ausgewählt", "Notenspiegel",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
