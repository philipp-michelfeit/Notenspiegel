package eu.bsinfo.notenspiegelmodel;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.table.AbstractTableModel;
import javax.xml.stream.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author michel0p
 *
 */
public class NotenspiegelModel extends AbstractTableModel {

	// ********************************************************************************************
	// *** Instanzfelder ***
	// ********************************************************************************************

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<String, Fach> faecher;

	// ********************************************************************************************
	// *** Konstruktor ***
	// ********************************************************************************************

	/**
	 * Konstruktor der Klasse {@link NotenspiegelModel}
	 *
	 * @param modus
	 *            Modus
	 */
	@SuppressWarnings("unchecked")
	public NotenspiegelModel(int modus) {

		this.faecher = new HashMap<String, Fach>();

		switch (modus) {
		case 1:

			// Deserialisierung von this.faecher aus Textdatei
			try {

				File file = new File("D:/Notenspiegel/michel0p/save/notenspiegel.txt");
				BufferedReader in = new BufferedReader(new FileReader(file));

				if (file.length() == 0) {
					break;
				} else {
					Scanner scan = new Scanner(in.readLine());
					int size = scan.nextInt();
					for (int i = 0; i < size; i++) {
						String name = in.readLine();
						this.faecher.put(name, new Fach(name));
						scan = new Scanner(in.readLine());
						int anzNoten = scan.nextInt();
						for (int j = 0; j < anzNoten; j++) {
							String wert = in.readLine(); // Wert
							String gewicht = in.readLine(); // Gewicht
							this.faecher.get(name).addNote(
									new Note(Integer.parseInt(wert), Integer.parseInt(gewicht)));
						}
					}
				}
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 2:

			// Binäre Deserialisierung von this.faecher aus XML-Datei (mit XML-Parser)
			try {

				File file = new File("D:/Notenspiegel/michel0p/save/notenspiegel.xml");
				FileInputStream in = new FileInputStream(file);
				XMLInputFactory factory = XMLInputFactory.newInstance();
				XMLStreamReader parser = factory.createXMLStreamReader(in);

				// Parsen des XML-Dokuments

				Fach f = null;

				while (parser.hasNext()) {

					if (parser.getEventType() == XMLStreamConstants.START_ELEMENT) {

						if (parser.getLocalName().equals("fach")) {
							String name = parser.getAttributeValue(0);
							f = new Fach(name);
							this.faecher.put(name, f);
						}

						if (parser.getLocalName().equals("note")) {

							int w = Integer.parseInt(parser.getAttributeValue(0));
							int g = Integer.parseInt(parser.getAttributeValue(1));
							Note n = new Note(w, g);
							f.addNote(n);

						}
					}

					parser.next();

				}

				parser.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 3:

			// Binäre Deserialisierung von Object-Graphen
			try {

				// File file = new
				// File("/home/MEDENT-ISMANING/michel0p/save/notenspiegel.dat");
				File file = new File("D:/Notenspiegel/michel0p/save/notenspiegel.bin");
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fileIn);

				this.faecher = (HashMap<String, Fach>) in.readObject();
				in.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

	}

	// ********************************************************************************************
	// *** Getter ***
	// ********************************************************************************************

	@Override
	public int getRowCount() {

		return this.faecher.size();
	}

	@Override
	public int getColumnCount() {
		int cc = 0;
		for (Fach f : this.faecher.values()) {
			if (f.getNoten().size() > cc) {
				cc = f.getNoten().size();
			}
		}
		return cc <= 8 ? 10 : cc + 2;
	}

	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Name";
		} else if (columnIndex == 1) {
			return "Schnitt";
		} else {
			return "Note " + (columnIndex - 1);
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		ArrayList<Fach> faecher = new ArrayList<Fach>(this.faecher.values());

		DecimalFormat df = new DecimalFormat("#0.00");

		if (columnIndex == 0) {
			return faecher.get(rowIndex).getName();
		} else if (columnIndex == 1) {
			return df.format(faecher.get(rowIndex).getDurchschnitt());
		} else if (faecher.get(rowIndex).getNoten().size() > 0
				&& columnIndex - 2 < faecher.get(rowIndex).getNoten().size()) {
			return faecher.get(rowIndex).getNoten().get(columnIndex - 2).toString();
		} else {
			return "";
		}
	}

	// ********************************************************************************************
	// *** Setter ***
	// ********************************************************************************************

	// keine benötigt !

	// ********************************************************************************************
	// *** Weitere Methoden ***
	// ********************************************************************************************

	/**
	 * Methode zum Erzeugen einer PDF-Datei mit iText
	 */
	public void makePDF() {

		// PDF erzeugen
		try {

			// Neue PDF-Datei im DIN-A4 Querformat
			Document doc = new Document(PageSize.A4.rotate());

			FileOutputStream out = new FileOutputStream(
					"D:/Notenspiegel/michel0p/save/notenspiegel.pdf");

			// Neue PDFWriter-Instanz holen
			PdfWriter.getInstance(doc, out);

			// Dokument zum Schreiben öffnen
			doc.open();

			// Neue Tabelle mit der entsprechenden Spaltenanzahl erzeugen
			PdfPTable table = new PdfPTable(this.getColumnCount());
			table.setWidthPercentage(100);
			int[] widths = new int[this.getColumnCount()];
			for (int i = 0; i < this.getColumnCount(); i++) {
				widths[i] = (i > 0) ? 1 : 2; // Erste Spalte hat doppelte Breite
			}
			// Spaltenbreite setzen
			table.setWidths(widths);
			// Anzahl der Zeilen setzen, die für jede neue Seite wiederholt
			// werden
			table.setHeaderRows(2);

			// Zelle Notenspiegel erzeugen, konfigurieren und der Tabelle
			// hinzufügen
			PdfPCell cell = new PdfPCell(new Phrase("Notenspiegel"));
			cell.setColspan(this.getColumnCount());
			cell.setBorderWidth(0);
			cell.setPaddingTop(10);
			cell.setPaddingBottom(10);
			table.addCell(cell);

			// Header-Zellen erzeugen, konfigurieren und der Tabelle hinzufügen
			for (int i = 0; i < this.getColumnCount(); i++) {
				cell = new PdfPCell(new Phrase(this.getColumnName(i)));
				cell.setPaddingTop(5);
				cell.setPaddingBottom(5);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new BaseColor(221, 221, 221));
				table.addCell(cell);
			}

			// Inhalt aus this.faecher in Tabelle schreiben
			for (int i = 0; i < this.getRowCount(); i++) {

				for(int j = 0; j < this.getColumnCount(); j++) {

					cell = new PdfPCell(new Phrase((String)this.getValueAt(i, j)));
					cell.setPaddingTop(5);
					cell.setPaddingBottom(5);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

				}
			}

			// Hinzufügen der Tabelle
			doc.add(table);

			// Dokument schließen
			doc.close();

		} catch (DocumentException e) {
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (SecurityException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param name
	 *            Name des Faches
	 */
	public void addFach(String name) {
		this.faecher.put(name, new Fach(name));
		this.fireTableDataChanged();
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

		int cc = this.getColumnCount();
		this.faecher.get(name).addNote(new Note(wert, gewicht));

		if (this.getColumnCount() > cc) {
			this.fireTableStructureChanged();
		} else {
			this.fireTableDataChanged();
		}
	}

	/**
	 * Methode zum Speichern der Noten in Textdatei (Serialisierung)
	 * 
	 * @param modus
	 *            Modus
	 */
	public void save(int modus) {
		switch (modus) {
		case 1:
			// Serialisierung von this.faecher in Textdatei
			try {

				File file = new File("D:/Notenspiegel/michel0p/save/notenspiegel.txt");
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				out.println(this.faecher.size());
				for (Fach f : this.faecher.values()) {
					out.println(f.getName());
					out.println(f.getNoten().size());
					for (Note n : f.getNoten()) {
						out.println(n.getWert());
						out.println(n.getGewicht());
					}
				}
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 2:
			// Binäre Serialisierung von this.faecher in XML-Datei
			try {
				File file = new File("D:/Notenspiegel/michel0p/save/notenspiegel.xml");
				FileOutputStream out = new FileOutputStream(file);
				DecimalFormat df = new DecimalFormat();

				// Erzeugt eine neue Instanz von XMLOutputFactory
				XMLOutputFactory factory = XMLOutputFactory.newInstance();

				// Erzeugen einer Instanz der Schnittstelle XMLStreamWriter
				XMLStreamWriter writer = factory.createXMLStreamWriter(out);

				// Schreiben der XML-Deklaration (DTD)
				writer.writeStartDocument();

				// Neue Zeile einfügen
				writer.writeCharacters("\n");

				// Schreiben des Start-Tags faecher
				writer.writeStartElement("faecher");

				// Fach-Tags für die einzelnen Faecher schreiben
				for (Fach f : this.faecher.values()) {

					// Neue Zeile einfügen
					writer.writeCharacters("\n");

					// Horizontalen Tab einfügen
					writer.writeCharacters("\t");

					// Start-Tag für Fach schreiben
					writer.writeStartElement("fach");

					// Attribut name für Fach schreiben
					writer.writeAttribute("name", f.getName());

					// Noten-Tags für die einzelnen Noten schreiben
					for (Note n : f.getNoten()) {

						// Neue Zeile einfügen
						writer.writeCharacters("\n");

						// Horizontalen Tab einfügen
						writer.writeCharacters("\t\t");

						// Empty-Tag für note schreiben
						writer.writeEmptyElement("note");

						// Attribut Wert für note schreiben
						writer.writeAttribute("wert", df.format(n.getWert()));

						// Attribut gewicht für note schreiben
						writer.writeAttribute("gewicht", df.format(n.getGewicht()));

					}

					// Neue Zeile einfügen
					writer.writeCharacters("\n");

					// Horizontalen Tab einfügen
					writer.writeCharacters("\t");

					// End-Tag für Fach schreiben
					writer.writeEndElement();

				}

				// Neue Zeile einfügen
				writer.writeCharacters("\n");

				// Schreiben des End-Tags faecher
				writer.writeEndElement();

				// Schließt alle Start-Tags und Erzeugt korrespondierende
				// End-Tags
				writer.writeEndDocument();

				writer.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 3:

			// Binäre Serialisierung von Objekt-Graphen
			try {

				// File file = new
				// File("/home/MEDENT-ISMANING/michel0p/save/notenspiegel.dat");
				File file = new File("D:/Notenspiegel/michel0p/save/notenspiegel.bin");
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);

				out.writeObject(this.faecher);
				out.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		}
	}
}
