package SOLUTIONS.src.zad_1;

import java.util.TreeSet;
import java.util.List;

/**
 * Klasa Fleet predstavlja flotu svemirskih brodova.
 * 
 * OOP Principi:
 * - ENKAPSULACIJA: Privatni atributi (fleetName, fleetSet) - kontrolirani pristup podacima
 * - KOMPOZICIJA/AGREGACIJA: Fleet sadrži kolekciju SpaceShip objekata (has-a odnos)
 * - APSTRAKCIJA: Sakriva složenost upravljanja kolekcijom i učitavanja iz datoteke
 * 
 * Koristi TreeSet za automatsko sortiranje brodova prema datumu lansiranja
 * (zahvaljujući Comparable implementaciji u SpaceShip klasi)
 */
public class Fleet {
    // ENKAPSULACIJA: Privatni atributi - zaštita internog stanja
    private String fleetName;              // Ime flote
    private TreeSet<SpaceShip> fleetSet;   // Sortirana kolekcija svemirskih brodova

    /**
     * Konstruktor za kreiranje flote.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Inicijalizacija privatnih atributa
     * - TreeSet automatski sortira elemente (koristi compareTo() iz SpaceShip)
     * 
     * @param fleetName Ime flote
     */
    public Fleet(String fleetName) {
        this.fleetName = fleetName;
        this.fleetSet = new TreeSet<>();  // TreeSet sortira brodove automatski
    }

    /**
     * Dodaje svemirski brod u flotu.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Kontrolirani način dodavanja brodova u privatnu kolekciju
     * - APSTRAKCIJA: Sakriva detalje dodavanja u TreeSet
     * 
     * TreeSet automatski sortira brodove pri dodavanju prema Comparable implementaciji
     * 
     * @param ship SpaceShip objekt koji se dodaje u flotu
     */
    public void addSpaceShip(SpaceShip ship) {
        fleetSet.add(ship);  // TreeSet automatski pozicionira brod na pravo mjesto
        System.out.println("SpaceShip added to the fleet.");
    }

    /**
     * Učitava svemirske brodove iz datoteke i dodaje ih u flotu.
     * 
     * OOP Principi:
     * - APSTRAKCIJA: Sakriva složenost čitanja datoteke i parsiranja podataka
     * - DELEGACIJA: Koristi pomoćnu klasu AUX_FLEET_CLS za operacije s datotekom
     * - ENKAPSULACIJA: Interno upravlja dodavanjem brodova u privatnu kolekciju
     * 
     * Proces:
     * 1. Čita sve linije iz datoteke (delegira AUX_FLEET_CLS)
     * 2. Za svaku liniju kreira SpaceShip objekt (delegira AUX_FLEET_CLS)
     * 3. Dodaje svaki brod u flotu
     * 
     * @param filePath Putanja do datoteke s podacima o brodovima
     */
    public void addSpaceShipsFromFile(String filePath) {
        try {
            // Delegiranje čitanja datoteke pomoćnoj klasi
            List<String> lines = AUX_FLEET_CLS.readLinesFromFile(filePath);
            
            // Iteracija kroz sve linije i kreiranje brodova
            for (String line : lines) {
                // Delegiranje kreiranja SpaceShip objekta iz stringa
                SpaceShip ship = AUX_FLEET_CLS.createSpaceShipsFromFileString(line);
                addSpaceShip(ship);  // Dodavanje broda u flotu
            }
            System.out.println("All SpaceShips are added from file: " + filePath);
        } catch (Exception e) {
            // Obrada grešaka pri čitanju datoteke
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    /**
     * Prikazuje sve brodove u floti.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Kontrolirani pristup privatnoj kolekciji brodova
     * - APSTRAKCIJA: Sakriva detalje iteracije kroz TreeSet
     * - POLIMORFIZAM: Koristi toString() metodu SpaceShip objekta (override-ana metoda)
     * 
     * Brodovi se prikazuju automatski sortirani (TreeSet + Comparable)
     */
    public void displayFleet() {
        System.out.println("================================");
        System.out.println("Fleet Name: " + fleetName);
        
        // Iteracija kroz sortirane brodove (TreeSet osigurava red)
        for (SpaceShip ship : fleetSet) {
            System.out.println(ship);  // Poziva toString() metodu SpaceShip objekta
        }
        System.out.println("================================");
    }
}