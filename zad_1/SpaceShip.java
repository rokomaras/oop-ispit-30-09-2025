package SOLUTIONS.src.zad_1;

import java.util.Calendar;
import java.util.Objects;
import java.util.Random;
import java.text.SimpleDateFormat;

/**
 * Klasa SpaceShip predstavlja svemirski brod s jedinstvenim ID-om, imenom i datumom lansiranja.
 * 
 * OOP Principi:
 * - ENKAPSULACIJA: Svi atributi su privatni (private), pristup preko getter/setter metoda
 * - POLIMORFIZAM: Implementira Comparable<SpaceShip> sučelje, override equals(), hashCode(), toString()
 * - APSTRAKCIJA: Skriva složenost generiranja datuma i parsiranja podataka iz datoteke
 * 
 * Implements Comparable<SpaceShip> za sortiranje brodova prema datumu lansiranja
 */
public class SpaceShip implements Comparable<SpaceShip> {
    // ENKAPSULACIJA: Privatni atributi - zaštita unutarnjeg stanja objekta
    private int id;                    // Jedinstveni identifikator broda
    private static int cntId = 99;     // Statički brojač za generiranje ID-ova
    private String spaceShipName;      // Ime svemirskog broda
    private Calendar launchDate;       // Datum lansiranja broda

    // ENKAPSULACIJA: Javne konstante definirane kao final - sigurno dijeljenje podataka
    public static final Calendar MIN_LAUNCH_DATE;  // Minimalni mogući datum lansiranja (2025)
    public static final Calendar MAX_LAUNCH_DATE;  // Maksimalni mogući datum lansiranja (2075)

    /**
     * Statički inicijalizacijski blok - izvršava se jednom pri učitavanju klase.
     * 
     * OOP Princip:
     * - ENKAPSULACIJA: Inicijalizacija statičkih konstanti u kontroliranom okruženju
     * - Osigurava da konstante imaju valjane vrijednosti prije bilo kakve upotrebe
     */
    static {
        MIN_LAUNCH_DATE = Calendar.getInstance();
        MIN_LAUNCH_DATE.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        MIN_LAUNCH_DATE.set(Calendar.MILLISECOND, 0);

        MAX_LAUNCH_DATE = Calendar.getInstance();
        MAX_LAUNCH_DATE.set(2075, Calendar.JANUARY, 1, 0, 0, 0);
        MAX_LAUNCH_DATE.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Konstruktor za kreiranje novog svemirskog broda s generiranim ID-om i nasumičnim datumom.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Inicijalizacija privatnih atributa kroz konstruktor
     * - APSTRAKCIJA: Automatski generira datum lansiranja (sakriva logiku generiranja)
     * 
     * @param spaceShipName Ime svemirskog broda
     */
    public SpaceShip(String spaceShipName) {
        this.id = ++cntId;  // Automatski inkrement statičkog brojača za jedinstveni ID
        this.spaceShipName = spaceShipName;
        generateNewLaunchDate();  // Generiranje nasumičnog datuma lansiranja
    }

    /**
     * Konstruktor za kreiranje svemirskog broda iz podataka iz datoteke.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Parsiranje i inicijalizacija privatnih atributa
     * - APSTRAKCIJA: Sakriva složenost parsiranja CSV formata
     * - POLIMORFIZAM: Overloading konstruktora (različiti parametri)
     * 
     * @param fileLine Linija teksta iz datoteke u formatu "ID,Ime,Datum"
     * @param fromFile Boolean flag koji označava da se koristi ovaj konstruktor za datoteku
     * @throws IllegalArgumentException ako format linije nije valjan
     */
    public SpaceShip(String fileLine, boolean fromFile) {
        // Parsiranje CSV podataka - razdvajanje po zarezu
        String[] parts = fileLine.split(",");
        if (parts.length < 3)
            throw new IllegalArgumentException("Invalid file line: " + fileLine);

        // Inicijalizacija atributa iz parsiranih podataka
        this.id = Integer.parseInt(parts[0].trim());
        this.spaceShipName = parts[1].trim();

        // Parsiranje datuma u formatu yyyy-MM-dd
        String[] dateParts = parts[2].trim().split("-");
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[2]), 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        this.launchDate = cal;
    }

    /**
     * Generira nasumični datum lansiranja između MIN_LAUNCH_DATE i MAX_LAUNCH_DATE.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Privatna metoda koja modificira interno stanje (launchDate)
     * - APSTRAKCIJA: Sakriva složenost generiranja nasumičnog datuma
     */
    public void generateNewLaunchDate() {
        // Izračun raspona datuma u milisekundama
        long minMillis = MIN_LAUNCH_DATE.getTimeInMillis();
        long maxMillis = MAX_LAUNCH_DATE.getTimeInMillis();
        
        // Generiranje nasumične vrijednosti u zadanom rasponu
        long randomMillis = minMillis + (long) (new Random().nextDouble() * (maxMillis - minMillis));
        
        // Postavljanje nasumičnog datuma
        Calendar randomDate = Calendar.getInstance();
        randomDate.setTimeInMillis(randomMillis);
        this.launchDate = randomDate;
    }

    /**
     * Postavlja datum lansiranja broda.
     * 
     * OOP Princip:
     * - ENKAPSULACIJA: Kontrolirani pristup privatnom atributu launchDate
     * 
     * @param date Novi datum lansiranja
     */
    public void setLaunchDate(Calendar date) {
        this.launchDate = date;
    }

    /**
     * Vraća ID broda.
     * 
     * OOP Princip:
     * - ENKAPSULACIJA: Getter metoda za pristup privatnom atributu (read-only pristup)
     * 
     * @return Jedinstveni identifikator broda
     */
    public int getId() {
        return id;
    }

    /**
     * Vraća ime broda.
     * 
     * OOP Princip:
     * - ENKAPSULACIJA: Getter metoda za pristup privatnom atributu
     * 
     * @return Ime svemirskog broda
     */
    public String getSpaceShipName() {
        return spaceShipName;
    }

    /**
     * Vraća datum lansiranja broda.
     * 
     * OOP Princip:
     * - ENKAPSULACIJA: Getter metoda za pristup privatnom atributu
     * 
     * @return Datum lansiranja
     */
    public Calendar getLaunchDate() {
        return launchDate;
    }

    /**
     * Vraća string reprezentaciju svemirskog broda.
     * 
     * OOP Princip:
     * - POLIMORFIZAM: Override metode iz Object klase
     * - APSTRAKCIJA: Sakriva detalje formatiranja datuma
     * 
     * @return Formatirani string s podacima o brodu
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(launchDate.getTime());
        return String.format("SpaceShip ID: %d, SpaceShip Name: %s, Launch Date: %s", id, spaceShipName, dateStr);
    }

    /**
     * Uspoređuje ovaj brod s drugim brodom za sortiranje.
     * 
     * OOP Principi:
     * - POLIMORFIZAM: Implementacija Comparable<SpaceShip> sučelja
     * - Omogućava automatsko sortiranje u kolekcijama (TreeSet u Fleet klasi)
     * 
     * Logika sortiranja:
     * 1. Primarno po datumu lansiranja (ranije prvo)
     * 2. Sekundarno po ID-u ako su datumi jednaki
     * 
     * @param o Drugi SpaceShip objekt za usporedbu
     * @return Negativan broj ako je ovaj brod "manji", 0 ako su jednaki, pozitivan ako je "veći"
     */
    @Override
    public int compareTo(SpaceShip o) {
        // Ako su datumi jednaki, sortiraj po ID-u
        if (this.launchDate.equals(o.launchDate)) {
            return Integer.compare(this.id, o.id);
        }
        // Inače sortiraj po datumu lansiranja
        return this.launchDate.compareTo(o.launchDate);
    }

    /**
     * Provjerava jednakost dva SpaceShip objekta.
     * 
     * OOP Princip:
     * - POLIMORFIZAM: Override metode equals() iz Object klase
     * - Dva broda su jednaka ako imaju isti ID, ime i datum lansiranja
     * 
     * @param o Objekt za usporedbu
     * @return true ako su objekti jednaki, inače false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Ista referenca
        if (!(o instanceof SpaceShip)) return false;  // Provjera tipa
        SpaceShip that = (SpaceShip) o;
        // Usporedba svih relevantnih atributa
        return id == that.id &&
                Objects.equals(spaceShipName, that.spaceShipName) &&
                Objects.equals(launchDate, that.launchDate);
    }

    /**
     * Vraća hash kod objekta.
     * 
     * OOP Princip:
     * - POLIMORFIZAM: Override metode hashCode() iz Object klase
     * - Mora biti konzistentan s equals() metodom (contract: ako equals() vraća true, hashCode() mora biti isti)
     * - Omogućava korištenje objekta u hash-baziranim kolekcijama (HashMap, HashSet)
     * 
     * @return Hash kod baziran na id, spaceShipName i launchDate
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, spaceShipName, launchDate);
    }
}