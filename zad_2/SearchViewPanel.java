package SOLUTIONS.src.zad_2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panel za prikaz rezultata pretrage korisnika.
 * 
 * OOP Principi:
 * - NASLJEĐIVANJE: Nasljeđuje JPanel klasu (extends JPanel) - "is-a" odnos
 * - ENKAPSULACIJA: Package-private atribut (searchArea) pristupan iz MainFrame-a
 * - KOMPOZICIJA: Sadrži JTextArea i JScrollPane komponente
 * - APSTRAKCIJA: Sakriva detalje JTextArea i layout managementa
 */
public class SearchViewPanel extends JPanel {
    // Package-private atribut - dostupan iz MainFrame klase
    JTextArea searchArea;  // Text area za prikaz rezultata pretrage

    /**
     * Konstruktor koji inicijalizira panel za prikaz pretrage.
     * 
     * OOP Principi:
     * - NASLJEĐIVANJE: Koristi metode iz JPanel klase (setLayout, setBorder, add, setEnabled)
     * - KOMPOZICIJA: Kreira i organizira JTextArea i JScrollPane
     * - APSTRAKCIJA: Sakriva detalje postavljanja scrollable text area-e
     */
    public SearchViewPanel() {
        // NASLJEĐIVANJE: Postavljanje layout-a i granice kroz metode iz JPanel
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8,20,8,20));
        
        // KOMPOZICIJA: Kreiranje text area za prikaz rezultata (2 reda, 40 stupaca)
        searchArea = new JTextArea(2, 40);
        searchArea.setEditable(false);  // Ne dozvoljava uređivanje (samo prikaz)
        searchArea.setBackground(Color.LIGHT_GRAY);  // Siva pozadina za razlikovanje od glavnog prikaza
        
        // Dodavanje label-a i scrollable text area-e
        add(new JLabel("Prikaz pretrage:"), BorderLayout.NORTH);
        add(new JScrollPane(searchArea), BorderLayout.CENTER);  // JScrollPane omogućava scroll
        
        setEnabled(false);  // Inicijalno neaktivan panel
    }

    /**
     * Prikazuje tekst u search area-i.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Javna metoda koja modificira privatno stanje text area-e
     * - APSTRAKCIJA: Sakriva detalje postavljanja teksta u JTextArea
     * 
     * @param txt Tekst za prikaz
     */
    public void showSearchData(String txt) {
        searchArea.setText(txt);
    }

    /**
     * Aktivira ili deaktivira panel.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Javna metoda koja kontrolira stanje panela
     * - APSTRAKCIJA: Sakriva detalje enable/disable logike
     * 
     * @param active true za aktiviranje, false za deaktiviranje
     */
    public void setPanelActive(boolean active) {
        setEnabled(active);
        searchArea.setEnabled(active);
    }
}