package SOLUTIONS.src.zad_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Glavna klasa GUI aplikacije - glavni prozor za upravljanje korisnicima po platformama.
 * 
 * OOP Principi:
 * - NASLJEĐIVANJE: Nasljeđuje JFrame klasu (extends JFrame) - "is-a" odnos
 * - ENKAPSULACIJA: Privatni atributi i metode, kontrolirani pristup podacima
 * - KOMPOZICIJA: Sadrži FormPanel, GeneralViewPanel, SearchViewPanel objekte ("has-a" odnos)
 * - POLIMORFIZAM: Koristi event listener interface-e (ActionListener), lambda izraze
 * - APSTRAKCIJA: Sakriva složenost GUI layouta, serijalizacije i upravljanja podacima
 */
public class MainFrame extends JFrame {
    // ENKAPSULACIJA: Privatni atributi - mapa korisnika po platformi, GUI paneli
    private Map<String, Set<User>> userMap = new HashMap<>();  // Korisnici grupirani po platformi
    private FormPanel formPanel;           // Panel za unos podataka
    private GeneralViewPanel generalPanel; // Panel za prikaz svih korisnika
    private SearchViewPanel searchPanel;   // Panel za prikaz rezultata pretrage

    /**
     * Konstruktor koji inicijalizira i postavlja glavni prozor aplikacije.
     * 
     * OOP Principi:
     * - NASLJEĐIVANJE: Koristi metode iz JFrame klase (setTitle, setSize, setDefaultCloseOperation, itd.)
     * - KOMPOZICIJA: Kreira i integrira višestruke panel objekte
     * - POLIMORFIZAM: Koristi lambda izraze za implementaciju ActionListener sučelja
     * - DELEGACIJA: Prepušta specifične zadatke specijaliziranim metodama (saveData, loadData, itd.)
     */
    public MainFrame() {
        // NASLJEĐIVANJE: Pozivanje metoda naslijeđenih iz JFrame klase
        setTitle("User Platform Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);  // Centriranje prozora

        // Kreiranje menija - primjer GUI kompozicije
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save data");
        JMenuItem loadItem = new JMenuItem("Load data");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // KOMPOZICIJA: Kreiranje komponenti GUI-ja
        formPanel = new FormPanel();
        generalPanel = new GeneralViewPanel();
        searchPanel = new SearchViewPanel();
        searchPanel.setPanelActive(false);  // Inicijalno neaktivan panel pretrage

        // Layout management - organizacija GUI komponenti
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(generalPanel, BorderLayout.NORTH);
        centerPanel.add(searchPanel, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        /**
         * Event listener za Submit gumb - dodaje novog korisnika.
         * 
         * OOP Principi:
         * - POLIMORFIZAM: Lambda izraz implementira ActionListener sučelje
         * - ENKAPSULACIJA: Koristi javne metode FormPanel-a za pristup podacima
         * - DELEGACIJA: Prepušta validaciju FormPanel-u (getFormUser)
         */
        formPanel.btnSubmit.addActionListener(e -> {
            // Dohvaćanje korisnika iz forme - DELEGACIJA
            User user = formPanel.getFormUser();
            if (user != null) {
                // Osiguravanje da postoji skup za platformu
                userMap.putIfAbsent(user.getPlatform(), new HashSet<>());
                
                // Pokušaj dodavanja korisnika - HashSet.add() vraća false ako korisnik već postoji
                if (userMap.get(user.getPlatform()).add(user)) {
                    System.out.println("Dodajem korisnika: " + user);
                    showAllUsers();       // Ažuriranje prikaza
                    formPanel.clearForm(); // Čišćenje forme
                } else {
                    // Korisnik već postoji (equals() metoda User klase vraća true)
                    System.out.println("Pokušaj dodavanja duplikata korisnika: " + user);
                    JOptionPane.showMessageDialog(this, "Korisnik već postoji!", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Neuspješan unos - neispravni podaci.");
            }
        });

        /**
         * Event listener za Cancel gumb - briše sadržaj forme.
         * 
         * OOP Principi:
         * - POLIMORFIZAM: Lambda izraz implementira ActionListener
         * - DELEGACIJA: Prepušta čišćenje forme FormPanel klasi
         */
        formPanel.btnCancel.addActionListener(e -> {
            System.out.println("Brisanje forme za unos.");
            formPanel.clearForm();
        });

        /**
         * Event listener za Save menu item - sprema podatke u binarnu datoteku.
         * 
         * OOP Principi:
         * - POLIMORFIZAM: Lambda izraz implementira ActionListener
         * - DELEGACIJA: Poziva privatnu metodu saveData()
         */
        saveItem.addActionListener(e -> {
            System.out.println("Spremam podatke u users.bin...");
            saveData();
        });
        
        /**
         * Event listener za Load menu item - učitava podatke iz binarne datoteke.
         * 
         * OOP Principi:
         * - POLIMORFIZAM: Lambda izraz implementira ActionListener
         * - DELEGACIJA: Poziva privatnu metodu loadData()
         */
        loadItem.addActionListener(e -> {
            System.out.println("Učitavam podatke iz users.bin...");
            loadData();
        });
        
        /**
         * Event listener za Exit menu item - zatvara aplikaciju.
         * 
         * OOP Principi:
         * - POLIMORFIZAM: Lambda izraz implementira ActionListener
         */
        exitItem.addActionListener(e -> {
            System.out.println("Izlaz iz aplikacije.");
            System.exit(0);
        });

        /**
         * Event listener za gumb brisanja pretrage.
         * 
         * OOP Principi:
         * - POLIMORFIZAM: Lambda izraz implementira ActionListener
         * - DELEGACIJA: Koristi metode SearchViewPanel-a
         */
        formPanel.btnSearchClear.addActionListener(e -> {
            System.out.println("Brisanje polja za pretragu.");
            formPanel.tfSearch.setText("");
            searchPanel.showSearchData("");
            searchPanel.setPanelActive(false);
            System.out.println("Pretraga obrisana.");
        });

        /**
         * Event listener za text field pretrage (Enter tipka).
         * 
         * OOP Principi:
         * - POLIMORFIZAM: Lambda izraz implementira ActionListener
         * - DELEGACIJA: Poziva metodu performSearch()
         */
        formPanel.tfSearch.addActionListener(e -> {
            System.out.println("Pokrećem pretragu za OS: " + formPanel.tfSearch.getText().trim());
            performSearch();
        });

        // Inicijalni prikaz svih korisnika (prazna lista na početku)
        showAllUsers();
        System.out.println("Aplikacija pokrenuta.");
    }

    /**
     * Prikazuje sve korisnike u glavnom panelu prikaza.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Privatna metoda - kontrolira kako se podaci prikazuju
     * - DELEGACIJA: Koristi GeneralViewPanel za prikaz
     * - POLIMORFIZAM: Koristi toString() metodu User objekta (override-ana metoda)
     */
    private void showAllUsers() {
        StringBuilder sb = new StringBuilder();
        
        // Iteracija kroz sve platforme i njihove korisnike
        for (var entry : userMap.entrySet()) {
            for (User u : entry.getValue()) {
                sb.append(u).append("\n");  // Poziva User.toString() - POLIMORFIZAM
            }
        }
        
        // DELEGACIJA: Prepušta prikaz GeneralViewPanel-u
        generalPanel.showData(sb.toString());
        System.out.println("Prikaz svih korisnika:");
        System.out.println(sb.toString());
    }

    /**
     * Izvršava pretragu korisnika po platformi.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Privatna metoda - kontrolira logiku pretrage
     * - DELEGACIJA: Koristi SearchViewPanel za prikaz rezultata
     * - POLIMORFIZAM: Koristi toString() metodu User objekta
     */
    private void performSearch() {
        String os = formPanel.tfSearch.getText().trim();
        
        // Validacija unosa
        if (os.isEmpty()) {
            String msg = "Unesite naziv platforme za pretragu.";
            searchPanel.showSearchData(msg);
            searchPanel.setPanelActive(true);
            System.out.println("Pretraga nije pokrenuta - polje prazno.");
            System.out.println(msg);
            return;
        }
        
        // Pretraga korisnika za zadanu platformu
        if (userMap.containsKey(os)) {
            StringBuilder sb = new StringBuilder();
            // Iteracija kroz sve korisnike za zadanu platformu
            for (User u : userMap.get(os)) {
                sb.append(u).append("\n");  // Poziva User.toString() - POLIMORFIZAM
            }
            searchPanel.showSearchData(sb.toString());
            searchPanel.setPanelActive(true);
            System.out.println("Pronađeni korisnici za OS: " + os);
            System.out.println(sb.toString());
        } else {
            // Platforma nema korisnika
            String msg = "Nema korisnika za OS: " + os;
            searchPanel.showSearchData(msg);
            searchPanel.setPanelActive(true);
            System.out.println(msg);
        }
    }

    /**
     * Sprema podatke u binarnu datoteku koristeći serijalizaciju.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Privatna metoda - sakriva detalje serijalizacije
     * - APSTRAKCIJA: Sakriva složenost ObjectOutputStream i FileOutputStream operacija
     * - Koristi Serializable sučelje implementirano u User klasi
     * 
     * Serijalizacija omogućava spremanje Java objekata u binarni format.
     */
    private void saveData() {
        try {
            // Kreiranje direktorija ako ne postoji
            File dataDir = new File("data");
            if (!dataDir.exists()) dataDir.mkdir();
            
            // Serijalizacija HashMap objekta u binarnu datoteku
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/users.bin"));
            oos.writeObject(userMap);  // Sprema cijelu mapu (HashMap je Serializable)
            oos.close();
            System.out.println("Podaci uspješno spremljeni.");
        } catch (Exception ex) {
            // Obrada grešaka
            System.out.println("Greška kod spremanja podataka!");
            JOptionPane.showMessageDialog(this, "Greška kod spremanja!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Učitava podatke iz binarne datoteke koristeći deserijalizaciju.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Privatna metoda - sakriva detalje deserijalizacije
     * - APSTRAKCIJA: Sakriva složenost ObjectInputStream i FileInputStream operacija
     * - POLIMORFIZAM: instanceof provjera tipa učitanog objekta
     * 
     * Deserijalizacija rekonstruira Java objekte iz binarnog formata.
     */
    private void loadData() {
        try {
            // Deserijalizacija objekta iz binarne datoteke
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/users.bin"));
            Object obj = ois.readObject();
            
            // POLIMORFIZAM: instanceof provjera tipa prije castanja
            if (obj instanceof HashMap) {
                userMap = (HashMap<String, Set<User>>) obj;  // Type casting
                showAllUsers();  // Prikaz učitanih podataka
                System.out.println("Podaci uspješno učitani.");
            }
            ois.close();
        } catch (Exception ex) {
            // Obrada grešaka (datoteka ne postoji ili je neispravna)
            System.out.println("Greška kod učitavanja podataka!");
            JOptionPane.showMessageDialog(this, "Greška kod učitavanja!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}