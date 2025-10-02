package SOLUTIONS.src.zad_1;

/**
 * Glavna klasa aplikacije za upravljanje flotom svemirskih brodova.
 * 
 * OOP Principi:
 * - ENKAPSULACIJA: Koristi objekte (SpaceShip, Fleet) s privatnim podacima
 * - APSTRAKCIJA: Skriva složenost operacija učitavanja iz datoteke i upravljanja flotom
 */
public class Main {
    /**
     * Glavna metoda koja pokreće aplikaciju.
     * 
     * Demonstrira:
     * - Kreiranje objekta SpaceShip (enkapsulacija)
     * - Postavljanje datuma lansiranja preko javne metode (pristup kontroliranim podacima)
     * - Korištenje statičkih konstanti (MAX_LAUNCH_DATE) za sigurno postavljanje vrijednosti
     * - Agregaciju objekata u Fleet (kompozicija - Fleet sadrži SpaceShip objekte)
     * - Učitavanje podataka iz datoteke (apstrakcija - skrivanje detalja I/O operacija)
     */
    public static void main(String[] args) {

        // Kreiranje instance svemirskog broda koristeći konstruktor (enkapsulacija)
        SpaceShip enterprise = new SpaceShip("Enterprise");
        // Postavljanje datuma lansiranja koristeći statičku konstantu
        enterprise.setLaunchDate(SpaceShip.MAX_LAUNCH_DATE);

        // Kreiranje instance flote koja će sadržavati svemirske brodove (agregacija/kompozicija)
        Fleet federationFleet = new Fleet("Federation Fleet");

        // Dodavanje broda u flotu - demonstracija enkapsulacije (flota upravlja svojim sadržajem)
        federationFleet.addSpaceShip(enterprise);

        // Prikaz trenutnog stanja flote
        federationFleet.displayFleet();

        // Učitavanje dodatnih brodova iz datoteke - apstrakcija (detalji čitanja sakriveni)
        String filePath = "data/FederationBoats.txt";
        federationFleet.addSpaceShipsFromFile(filePath);

        // Prikaz ažurirane flote nakon učitavanja iz datoteke
        federationFleet.displayFleet();
    }
}