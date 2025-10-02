package SOLUTIONS.src.zad_2;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 * Klasa User predstavlja korisnika s korisničkim imenom, emailom, platformom i danima aktivnosti.
 * 
 * OOP Principi:
 * - ENKAPSULACIJA: Svi atributi su privatni, pristup preko getter metoda
 * - POLIMORFIZAM: Implementira Serializable sučelje, override equals(), hashCode(), toString()
 * - APSTRAKCIJA: Sakriva unutarnju implementaciju (koristi HashSet za dane)
 * 
 * Implements Serializable za mogućnost serijalizacije (spremanje u binarne datoteke)
 */
public class User implements Serializable {
    // ENKAPSULACIJA: Privatni atributi - zaštita unutarnjeg stanja objekta
    private String userName;    // Korisničko ime
    private String email;       // Email adresa korisnika
    private String platform;    // Platforma (OS) koju korisnik koristi
    private Set<String> days;   // Skup dana kada je korisnik aktivan

    /**
     * Konstruktor za kreiranje korisnika.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Inicijalizacija privatnih atributa kroz konstruktor
     * - DEFENSIVE COPY: Kreira novi HashSet od proslijeđenih dana (sprječava vanjske izmjene)
     * 
     * @param userName Korisničko ime
     * @param email Email adresa
     * @param platform Operativni sistem/platforma
     * @param days Skup dana aktivnosti
     */
    public User(String userName, String email, String platform, Set<String> days) {
        this.userName = userName;
        this.email = email;
        this.platform = platform;
        // Defensive copy - kreira novu kopiju kako vanjski kod ne bi mogao mijenjati interno stanje
        this.days = new HashSet<>(days);
    }

    /**
     * Vraća korisničko ime.
     * 
     * OOP Princip:
     * - ENKAPSULACIJA: Getter metoda za pristup privatnom atributu
     * 
     * @return Korisničko ime
     */
    public String getUserName() { return userName; }
    
    /**
     * Vraća email adresu.
     * 
     * OOP Princip:
     * - ENKAPSULACIJA: Getter metoda za pristup privatnom atributu
     * 
     * @return Email adresa
     */
    public String getEmail() { return email; }
    
    /**
     * Vraća platformu/OS.
     * 
     * OOP Princip:
     * - ENKAPSULACIJA: Getter metoda za pristup privatnom atributu
     * 
     * @return Naziv platforme
     */
    public String getPlatform() { return platform; }
    
    /**
     * Vraća kopiju skupa dana aktivnosti.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Getter metoda za pristup privatnom atributu
     * - DEFENSIVE COPY: Vraća novu kopiju HashSet-a (sprječava vanjske izmjene internog stanja)
     * 
     * @return Nova kopija skupa dana
     */
    public Set<String> getDays() { return new HashSet<>(days); }

    /**
     * Provjerava jednakost dva User objekta.
     * 
     * OOP Princip:
     * - POLIMORFIZAM: Override metode equals() iz Object klase
     * - Dva korisnika su jednaka ako imaju isto korisničko ime i email
     * - Ne uspoređuje platformu i dane - korisnik se identificira po imenu i emailu
     * 
     * @param o Objekt za usporedbu
     * @return true ako su korisnici jednaki, inače false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Ista referenca
        if (!(o instanceof User)) return false;  // Provjera tipa
        User user = (User) o;
        // Usporedba po korisničkom imenu i emailu (jedinstvena identifikacija)
        return Objects.equals(userName, user.userName) &&
                Objects.equals(email, user.email);
    }

    /**
     * Vraća hash kod objekta.
     * 
     * OOP Princip:
     * - POLIMORFIZAM: Override metode hashCode() iz Object klase
     * - Mora biti konzistentan s equals() (koristi iste atribute: userName, email)
     * - Omogućava korištenje User objekata u HashSet i HashMap kolekcijama
     * 
     * @return Hash kod baziran na userName i email
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName, email);
    }

    /**
     * Vraća string reprezentaciju korisnika.
     * 
     * OOP Princip:
     * - POLIMORFIZAM: Override metode toString() iz Object klase
     * - Formatira sve podatke o korisniku u čitljiv string
     * 
     * @return Formatirani string s podacima o korisniku
     */
    @Override
    public String toString() {
        return String.format("User: %s, Email: %s, Platform: %s, Days: %s",
                userName, email, platform, days);
    }
}