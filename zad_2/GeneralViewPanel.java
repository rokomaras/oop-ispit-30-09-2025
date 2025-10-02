package SOLUTIONS.src.zad_2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panel za prikaz svih korisnika u aplikaciji.
 * 
 * OOP Principi:
 * - NASLJEĐIVANJE: Nasljeđuje JPanel klasu (extends JPanel) - "is-a" odnos
 * - ENKAPSULACIJA: Package-private atribut (dataArea) pristupan iz MainFrame-a
 * - KOMPOZICIJA: Sadrži JTextArea i JScrollPane komponente
 * - APSTRAKCIJA: Sakriva detalje JTextArea i layout managementa
 */
public class GeneralViewPanel extends JPanel {
    // Package-private atribut - dostupan iz MainFrame klase
    JTextArea dataArea;  // Text area za prikaz podataka

    /**
     * Konstruktor koji inicijalizira panel za prikaz podataka.
     * 
     * OOP Principi:
     * - NASLJEĐIVANJE: Koristi metode iz JPanel klase (setLayout, setBorder, add)
     * - KOMPOZICIJA: Kreira i organizira JTextArea i JScrollPane
     * - APSTRAKCIJA: Sakriva detalje postavljanja scrollable text area-e
     */
    public GeneralViewPanel() {
        // NASLJEĐIVANJE: Postavljanje layout-a i granice kroz metode iz JPanel
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8,20,4,20));
        
        // KOMPOZICIJA: Kreiranje text area za prikaz (3 reda, 40 stupaca)
        dataArea = new JTextArea(3, 40);
        dataArea.setEditable(false);  // Ne dozvoljava uređivanje (samo prikaz)
        
        // Dodavanje label-a i scrollable text area-e
        add(new JLabel("Prikaz podataka:"), BorderLayout.NORTH);
        add(new JScrollPane(dataArea), BorderLayout.CENTER);  // JScrollPane omogućava scroll
    }

    /**
     * Prikazuje tekst u text area-i.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Javna metoda koja modificira privatno stanje text area-e
     * - APSTRAKCIJA: Sakriva detalje postavljanja teksta u JTextArea
     * 
     * @param txt Tekst za prikaz
     */
    public void showData(String txt) {
        dataArea.setText(txt);
    }
}