package SOLUTIONS.src.zad_2;

import javax.swing.*;

/**
 * Glavna klasa za pokretanje Swing GUI aplikacije za upravljanje korisnicima.
 * 
 * OOP Principi:
 * - ENKAPSULACIJA: Koristi objekte (MainFrame) s privatnim podacima
 * - APSTRAKCIJA: Sakriva složenost inicijalizacije GUI-ja
 */
public class Main {
    /**
     * Glavna metoda koja pokreće Swing aplikaciju.
     * 
     * OOP Principi:
     * - APSTRAKCIJA: Sakriva detalje thread managementa i inicijalizacije GUI-ja
     * - DELEGACIJA: Prepušta kreiranje i prikaz prozora MainFrame klasi
     * 
     * Best practices:
     * - Postavlja system look and feel za bolju integraciju s OS-om
     * - Koristi SwingUtilities.invokeLater() za thread-safe GUI inicijalizaciju
     *   (Swing komponente moraju biti kreirane na Event Dispatch Thread-u)
     */
    public static void main(String[] args) {
        // Pokušaj postavljanja system look and feel-a za native izgled aplikacije
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ }
        
        // Thread-safe pokretanje GUI-ja na Event Dispatch Thread-u
        // Lambda izraz kreira novi MainFrame i čini ga vidljivim
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}