package SOLUTIONS.src.zad_1;

import java.io.*;
import java.util.*;

/**
 * Pomoćna (utility) klasa za operacije s datotekama za Fleet.
 * 
 * OOP Principi:
 * - ENKAPSULACIJA: Privatni konstruktor sprječava instanciranje (utility klasa)
 * - APSTRAKCIJA: Sakriva složenost I/O operacija i parsiranja podataka
 * - SINGLE RESPONSIBILITY: Odgovorna samo za operacije čitanja datoteka i parsiranja
 * 
 * Ova klasa služi kao pomoćnik (helper) za Fleet klasu, odvojujući logiku
 * rada s datotekama od poslovne logike upravljanja flotom.
 */
public class AUX_FLEET_CLS {
    /**
     * Privatni konstruktor sprječava instanciranje klase.
     * 
     * OOP Princip:
     * - ENKAPSULACIJA: Utility klasa ne treba instance, sve metode su statičke
     * - Design pattern: Utility/Helper class pattern
     */
    private AUX_FLEET_CLS() {}

    /**
     * Čita sve linije iz datoteke i vraća ih kao listu stringova.
     * 
     * OOP Principi:
     * - APSTRAKCIJA: Sakriva detalje I/O operacija (BufferedReader, try-with-resources)
     * - Automatsko upravljanje resursima (try-with-resources - zatvara BufferedReader)
     * 
     * @param filePath Putanja do datoteke za čitanje
     * @return Lista stringova, svaki string predstavlja jednu liniju iz datoteke
     * @throws IOException Ako dođe do greške pri čitanju datoteke
     */
    public static List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        
        // Try-with-resources automatski zatvara BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Čitanje linije po linije
            while ((line = br.readLine()) != null) {
                // Preskakanje praznih linija
                if (!line.trim().isEmpty())
                    lines.add(line.trim());
            }
        }
        return lines;
    }

    /**
     * Kreira SpaceShip objekt iz string podataka.
     * 
     * OOP Principi:
     * - APSTRAKCIJA: Sakriva detalje kreiranja SpaceShip objekta iz stringa
     * - DELEGACIJA: Delegira parsiranje konstruktoru SpaceShip(String, boolean)
     * 
     * @param fileLine String s podacima o brodu u CSV formatu
     * @return Novi SpaceShip objekt kreiran iz podataka
     */
    public static SpaceShip createSpaceShipsFromFileString(String fileLine) {
        // Delegiranje kreiranja SpaceShip konstruktoru koji parsira CSV format
        return new SpaceShip(fileLine, true);
    }
}