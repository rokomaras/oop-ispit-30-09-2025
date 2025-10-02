# Opis Koda - OOP Ispit Projekta

## Pregled Projekta

Ovaj projekt sastoji se od dva glavna zadatka koji demonstriraju ključne principe objektno-orijentiranog programiranja (OOP) u Javi:

1. **Zadatak 1 (zad_1)** - Sustav za upravljanje flotom svemirskih brodova
2. **Zadatak 2 (zad_2)** - GUI aplikacija za upravljanje korisnicima po platformama

---

## Zadatak 1: Fleet Management System (zad_1)

### 1.1 Main.java

**Svrha**: Glavna klasa aplikacije koja pokreće sustav upravljanja flotom svemirskih brodova.

**OOP Principi**:
- **Enkapsulacija**: Koristi objekte (SpaceShip, Fleet) s privatnim podacima kojima se pristupa preko javnih metoda
- **Apstrakcija**: Sakriva složenost operacija učitavanja iz datoteke i upravljanja flotom

**Ključne operacije**:
1. Kreiranje svemirskog broda "Enterprise" koristeći konstruktor
2. Postavljanje datuma lansiranja koristeći statičku konstantu MAX_LAUNCH_DATE
3. Kreiranje flote "Federation Fleet" i dodavanje broda u nju
4. Učitavanje dodatnih brodova iz datoteke `data/FederationBoats.txt`
5. Prikaz flote prije i nakon učitavanja iz datoteke

**Primjer koda**:
```java
SpaceShip enterprise = new SpaceShip("Enterprise");
enterprise.setLaunchDate(SpaceShip.MAX_LAUNCH_DATE);
Fleet federationFleet = new Fleet("Federation Fleet");
federationFleet.addSpaceShip(enterprise);
```

---

### 1.2 SpaceShip.java

**Svrha**: Predstavlja svemirski brod s jedinstvenim ID-om, imenom i datumom lansiranja.

**OOP Principi**:

#### 1. Enkapsulacija
- Svi atributi su privatni: `id`, `spaceShipName`, `launchDate`
- Pristup podacima samo preko getter/setter metoda
- Statički brojač `cntId` za automatsko generiranje ID-ova
- Javne konstante `MIN_LAUNCH_DATE` i `MAX_LAUNCH_DATE` za sigurno dijeljenje podataka

#### 2. Polimorfizam
- **Implementacija sučelja**: `implements Comparable<SpaceShip>` za sortiranje
- **Method Overriding**: 
  - `compareTo()` - usporedba brodova za sortiranje (prvo po datumu, zatim po ID-u)
  - `equals()` - provjera jednakosti dva broda
  - `hashCode()` - hash funkcija konzistentna s equals()
  - `toString()` - formatiranje broda u string reprezentaciju

#### 3. Apstrakcija
- `generateNewLaunchDate()` sakriva složenost generiranja nasumičnog datuma
- Konstruktor za parsiranje CSV podataka sakriva detalje parsiranja

**Ključne metode**:

```java
// Konstruktor s automatskim generiranjem ID-a i datuma
public SpaceShip(String spaceShipName)

// Konstruktor za učitavanje iz datoteke (CSV format)
public SpaceShip(String fileLine, boolean fromFile)

// Generiranje nasumičnog datuma između MIN i MAX
public void generateNewLaunchDate()

// Usporedba brodova (Comparable)
public int compareTo(SpaceShip o)
```

**Statički blok inicijalizacije**:
```java
static {
    MIN_LAUNCH_DATE = Calendar.getInstance();
    MIN_LAUNCH_DATE.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
    MAX_LAUNCH_DATE = Calendar.getInstance();
    MAX_LAUNCH_DATE.set(2075, Calendar.JANUARY, 1, 0, 0, 0);
}
```

**Primjena Comparable**:
Brodovi se sortiraju primarno po datumu lansiranja, a sekundarno po ID-u ako su datumi jednaki:
```java
public int compareTo(SpaceShip o) {
    if (this.launchDate.equals(o.launchDate)) {
        return Integer.compare(this.id, o.id);
    }
    return this.launchDate.compareTo(o.launchDate);
}
```

---

### 1.3 Fleet.java

**Svrha**: Predstavlja flotu svemirskih brodova s automatskim sortiranjem.

**OOP Principi**:

#### 1. Enkapsulacija
- Privatni atributi: `fleetName`, `fleetSet`
- Kontrolirani pristup podacima preko javnih metoda

#### 2. Kompozicija/Agregacija
- Fleet **ima** (has-a) kolekciju SpaceShip objekata
- Koristi `TreeSet<SpaceShip>` za automatsko sortiranje

#### 3. Apstrakcija
- `addSpaceShipsFromFile()` sakriva složenost čitanja datoteke
- `displayFleet()` sakriva detalje iteracije i ispisa

#### 4. Delegacija
- Delegira operacije čitanja datoteke i parsiranja pomoćnoj klasi `AUX_FLEET_CLS`

**Ključne metode**:

```java
// Dodaje brod u flotu (TreeSet automatski sortira)
public void addSpaceShip(SpaceShip ship)

// Učitava brodove iz datoteke
public void addSpaceShipsFromFile(String filePath)

// Prikazuje sve brodove u sortiranom redoslijedu
public void displayFleet()
```

**TreeSet automatsko sortiranje**:
Zahvaljujući `Comparable<SpaceShip>` implementaciji, brodovi se automatski sortiraju pri dodavanju u TreeSet:
```java
private TreeSet<SpaceShip> fleetSet = new TreeSet<>();
fleetSet.add(ship); // Automatski sortira prema compareTo()
```

---

### 1.4 AUX_FLEET_CLS.java

**Svrha**: Pomoćna (utility) klasa za operacije s datotekama.

**OOP Principi**:

#### 1. Enkapsulacija
- **Privatni konstruktor** - sprječava instanciranje (utility klasa)
- Sve metode su statičke - ne treba instance klase

#### 2. Apstrakcija
- Sakriva složenost I/O operacija (BufferedReader, FileReader)
- Try-with-resources automatski upravlja resursima

#### 3. Single Responsibility Principle
- Odgovorna samo za operacije čitanja datoteka i parsiranja
- Odvaja logiku rada s datotekama od poslovne logike Fleet klase

**Ključne metode**:

```java
// Čita sve linije iz datoteke
public static List<String> readLinesFromFile(String filePath)

// Kreira SpaceShip iz string podataka
public static SpaceShip createSpaceShipsFromFileString(String fileLine)
```

**Design Pattern**: Utility/Helper class pattern
```java
private AUX_FLEET_CLS() {} // Privatni konstruktor
```

---

## Zadatak 2: User Platform Manager GUI (zad_2)

### 2.1 Main.java

**Svrha**: Pokretanje Swing GUI aplikacije za upravljanje korisnicima.

**OOP Principi**:
- **Apstrakcija**: Sakriva detalje thread managementa i inicijalizacije GUI-ja
- **Delegacija**: Prepušta kreiranje prozora MainFrame klasi

**Thread-safe GUI inicijalizacija**:
```java
javax.swing.SwingUtilities.invokeLater(() -> {
    new MainFrame().setVisible(true);
});
```

Swing komponente moraju biti kreirane na Event Dispatch Thread-u (EDT) - `invokeLater()` osigurava to.

---

### 2.2 User.java

**Svrha**: Predstavlja korisnika s korisničkim imenom, emailom, platformom i danima aktivnosti.

**OOP Principi**:

#### 1. Enkapsulacija
- Svi atributi su privatni: `userName`, `email`, `platform`, `days`
- Pristup podacima samo preko getter metoda
- **Defensive Copy**: konstruktor i getDays() vraćaju kopiju HashSet-a

#### 2. Polimorfizam
- **Implementacija sučelja**: `implements Serializable` za serijalizaciju
- **Method Overriding**:
  - `equals()` - usporedba korisnika po username i email (jedinstvena identifikacija)
  - `hashCode()` - hash funkcija konzistentna s equals()
  - `toString()` - formatiranje korisnika u string

#### 3. Apstrakcija
- Sakriva unutarnju implementaciju (koristi HashSet za dane)

**Defensive Copy princip**:
```java
// U konstruktoru - kreira novu kopiju
this.days = new HashSet<>(days);

// U getter-u - vraća novu kopiju
public Set<String> getDays() { 
    return new HashSet<>(days); 
}
```
Ovo sprječava vanjske izmjene internog stanja objekta.

**Equals i HashCode**:
Korisnici se identificiraju po username i email (ne uspoređuju se platform i days):
```java
@Override
public boolean equals(Object o) {
    User user = (User) o;
    return Objects.equals(userName, user.userName) &&
           Objects.equals(email, user.email);
}

@Override
public int hashCode() {
    return Objects.hash(userName, email);
}
```

**Serializable sučelje**:
Omogućava spremanje User objekata u binarne datoteke koristeći ObjectOutputStream.

---

### 2.3 MainFrame.java

**Svrha**: Glavni prozor GUI aplikacije za upravljanje korisnicima po platformama.

**OOP Principi**:

#### 1. Nasljeđivanje
- **extends JFrame** - MainFrame nasljeđuje sve metode i atribute JFrame klase
- "is-a" odnos: MainFrame **je** JFrame
- Koristi naslijeđene metode: `setTitle()`, `setSize()`, `setDefaultCloseOperation()`

#### 2. Kompozicija
- **has-a** odnos: MainFrame **ima** FormPanel, GeneralViewPanel, SearchViewPanel
- Kreira i integrira višestruke panel objekte

#### 3. Enkapsulacija
- Privatni atributi: `userMap`, `formPanel`, `generalPanel`, `searchPanel`
- Privatne metode: `showAllUsers()`, `performSearch()`, `saveData()`, `loadData()`

#### 4. Polimorfizam
- **Lambda izrazi**: Implementiraju ActionListener sučelje
- **instanceof provjera**: Provjera tipa pri deserijalizaciji

#### 5. Apstrakcija
- Sakriva složenost GUI layouta, serijalizacije i upravljanja podacima

**Ključne strukture podataka**:
```java
private Map<String, Set<User>> userMap = new HashMap<>();
```
- HashMap grupira korisnike po platformi (ključ = platforma, vrijednost = Set korisnika)
- HashSet sprječava duplikate korisnika (koristi equals() i hashCode())

**Event Listeners (Polimorfizam s lambda izrazima)**:

Lambda izrazi su kompaktan način implementacije funkcionalnih sučelja:
```java
// Submit button - dodavanje korisnika
formPanel.btnSubmit.addActionListener(e -> {
    User user = formPanel.getFormUser();
    if (user != null) {
        userMap.putIfAbsent(user.getPlatform(), new HashSet<>());
        if (userMap.get(user.getPlatform()).add(user)) {
            showAllUsers();
            formPanel.clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Korisnik već postoji!");
        }
    }
});
```

**Serijalizacija i Deserijalizacija**:

```java
// Spremanje (serijalizacija)
private void saveData() {
    ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream("data/users.bin"));
    oos.writeObject(userMap);
}

// Učitavanje (deserijalizacija)
private void loadData() {
    ObjectInputStream ois = new ObjectInputStream(
        new FileInputStream("data/users.bin"));
    Object obj = ois.readObject();
    if (obj instanceof HashMap) {  // Polimorfizam - instanceof provjera
        userMap = (HashMap<String, Set<User>>) obj;
    }
}
```

**Delegacija**:
MainFrame delegira specifične zadatke specijaliziranim panel klasama:
- FormPanel - unos i validacija podataka
- GeneralViewPanel - prikaz svih korisnika
- SearchViewPanel - prikaz rezultata pretrage

---

### 2.4 FormPanel.java

**Svrha**: Panel za unos korisničkih podataka i pretragu.

**OOP Principi**:

#### 1. Nasljeđivanje
- **extends JPanel** - FormPanel nasljeđuje JPanel klasu
- Koristi naslijeđene metode: `setLayout()`, `setBorder()`, `add()`

#### 2. Kompozicija
- **has-a** odnos: FormPanel **ima** JTextField, JComboBox, JCheckBox, JButton komponente
- Organizira višestruke Swing komponente

#### 3. Enkapsulacija
- Package-private atributi - dostupni iz MainFrame-a, ali ne izvan paketa
- Javne metode `getFormUser()` i `clearForm()` za interakciju

#### 4. Apstrakcija
- `getFormUser()` sakriva složenost prikupljanja i validacije podataka
- `clearForm()` sakriva detalje resetiranja svih komponenti

**Layout Management**:
Koristi kombinaciju različitih layout managera:
- **BorderLayout** - glavna organizacija panela
- **GridLayout** - lijevi panel (polja za unos)
- **FlowLayout** - checkboxovi, gumbi
- **BoxLayout** - desni panel (vertikalno slaganje)

**Validacija unosa**:
```java
public User getFormUser() {
    String name = tfUserName.getText().trim();
    String email = tfEmail.getText().trim();
    String platform = (String) cbPlatform.getSelectedItem();
    
    // Validacija - vraća null ako podaci nisu valjani
    if (name.isEmpty() || email.isEmpty() || platform == null) 
        return null;
    
    return new User(name, email, platform, days);
}
```

---

### 2.5 GeneralViewPanel.java

**Svrha**: Panel za prikaz svih korisnika u aplikaciji.

**OOP Principi**:

#### 1. Nasljeđivanje
- **extends JPanel** - GeneralViewPanel nasljeđuje JPanel klasu

#### 2. Kompozicija
- **has-a** odnos: sadrži JTextArea i JScrollPane komponente

#### 3. Enkapsulacija
- Package-private atribut `dataArea` - dostupan iz MainFrame-a
- Javna metoda `showData()` za postavljanje teksta

#### 4. Apstrakcija
- Sakriva detalje JTextArea i scroll funkcionalnosti

**Ključne karakteristike**:
```java
dataArea = new JTextArea(3, 40);
dataArea.setEditable(false);  // Samo za prikaz, ne za uređivanje
add(new JScrollPane(dataArea));  // Dodaje scroll funkcionalnost
```

---

### 2.6 SearchViewPanel.java

**Svrha**: Panel za prikaz rezultata pretrage korisnika.

**OOP Principi**:

#### 1. Nasljeđivanje
- **extends JPanel** - SearchViewPanel nasljeđuje JPanel klasu

#### 2. Kompozicija
- **has-a** odnos: sadrži JTextArea i JScrollPane komponente

#### 3. Enkapsulacija
- Package-private atribut `searchArea`
- Javne metode `showSearchData()` i `setPanelActive()` za kontrolu

#### 4. Apstrakcija
- Sakriva detalje aktivacije/deaktivacije panela

**Vizualna diferencijacija**:
```java
searchArea.setBackground(Color.LIGHT_GRAY);  // Siva pozadina
setEnabled(false);  // Inicijalno neaktivan
```

**Kontrola stanja**:
```java
public void setPanelActive(boolean active) {
    setEnabled(active);
    searchArea.setEnabled(active);
}
```

---

## Sažetak OOP Principa po Projektu

### Zadatak 1 (Fleet Management)

| OOP Princip | Implementacija |
|------------|----------------|
| **Enkapsulacija** | Privatni atributi u SpaceShip i Fleet, pristup preko getter/setter |
| **Polimorfizam** | Comparable sučelje, override toString(), equals(), hashCode() |
| **Apstrakcija** | Sakrivanje logike generiranja datuma, parsiranja CSV, I/O operacija |
| **Kompozicija** | Fleet sadrži TreeSet<SpaceShip> objekata |
| **Delegacija** | Fleet delegira I/O operacije AUX_FLEET_CLS klasi |

### Zadatak 2 (User Manager GUI)

| OOP Princip | Implementacija |
|------------|----------------|
| **Nasljeđivanje** | MainFrame extends JFrame, svi paneli extend JPanel |
| **Enkapsulacija** | Privatni atributi u User i MainFrame, defensive copy u User |
| **Polimorfizam** | Serializable sučelje, lambda izrazi, override equals()/hashCode() |
| **Apstrakcija** | Sakrivanje layout managementa, serijalizacije, validacije |
| **Kompozicija** | MainFrame sadrži FormPanel, GeneralViewPanel, SearchViewPanel |
| **Delegacija** | MainFrame delegira specifične zadatke specijaliziranim panelima |

---

## Ključni Design Patterns

### 1. Utility Class Pattern (AUX_FLEET_CLS)
- Privatni konstruktor sprječava instanciranje
- Sve metode su statičke
- Koristi se za helper operacije (I/O, parsiranje)

### 2. MVC-sličan Pattern (Zadatak 2)
- **Model**: User, HashMap<String, Set<User>>
- **View**: FormPanel, GeneralViewPanel, SearchViewPanel
- **Controller**: MainFrame (event listeners, business logic)

### 3. Singleton-like Pattern (SpaceShip ID generiranje)
- Statički brojač `cntId` dijele sve instance
- Osigurava jedinstvene ID-ove

---

## Primjeri Korištenja OOP Principa

### Primjer 1: Enkapsulacija s Defensive Copy (User.java)

```java
// Privatni atribut
private Set<String> days;

// Konstruktor - kreira novu kopiju
public User(String userName, String email, String platform, Set<String> days) {
    this.days = new HashSet<>(days);  // Nova kopija
}

// Getter - vraća novu kopiju
public Set<String> getDays() { 
    return new HashSet<>(days);  // Nova kopija
}
```

**Zašto?** Sprječava vanjske izmjene internog stanja objekta.

---

### Primjer 2: Polimorfizam s Comparable (SpaceShip.java)

```java
public class SpaceShip implements Comparable<SpaceShip> {
    @Override
    public int compareTo(SpaceShip o) {
        // Primarno sortiranje po datumu
        if (this.launchDate.equals(o.launchDate)) {
            return Integer.compare(this.id, o.id);  // Sekundarno po ID-u
        }
        return this.launchDate.compareTo(o.launchDate);
    }
}

// Korištenje u Fleet.java
private TreeSet<SpaceShip> fleetSet = new TreeSet<>();
// TreeSet automatski koristi compareTo() za sortiranje
```

**Zašto?** Omogućava automatsko sortiranje u kolekcijama.

---

### Primjer 3: Nasljeđivanje i Kompozicija (MainFrame.java)

```java
// NASLJEĐIVANJE: MainFrame "is-a" JFrame
public class MainFrame extends JFrame {
    
    // KOMPOZICIJA: MainFrame "has-a" panele
    private FormPanel formPanel;
    private GeneralViewPanel generalPanel;
    private SearchViewPanel searchPanel;
    
    public MainFrame() {
        // Korištenje naslijeđenih metoda iz JFrame
        setTitle("User Platform Manager");
        setSize(750, 500);
        
        // Kreiranje komponenti (kompozicija)
        formPanel = new FormPanel();
        generalPanel = new GeneralViewPanel();
        searchPanel = new SearchViewPanel();
    }
}
```

**Zašto?** 
- Nasljeđivanje: Ponovno korištenje JFrame funkcionalnosti
- Kompozicija: Fleksibilna organizacija GUI komponenti

---

### Primjer 4: Polimorfizam s Lambda Izrazima (MainFrame.java)

```java
// Lambda izraz implementira ActionListener sučelje
formPanel.btnSubmit.addActionListener(e -> {
    User user = formPanel.getFormUser();
    if (user != null) {
        // Logika dodavanja korisnika
    }
});

// Tradicionalan način (bez lambda izraza)
formPanel.btnSubmit.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        User user = formPanel.getFormUser();
        if (user != null) {
            // Logika dodavanja korisnika
        }
    }
});
```

**Zašto?** Lambda izrazi su kompaktniji i čitljiviji.

---

### Primjer 5: Apstrakcija i Delegacija (Fleet.java)

```java
public void addSpaceShipsFromFile(String filePath) {
    try {
        // DELEGACIJA: Prepušta čitanje AUX_FLEET_CLS klasi
        List<String> lines = AUX_FLEET_CLS.readLinesFromFile(filePath);
        
        for (String line : lines) {
            // DELEGACIJA: Prepušta parsiranje AUX_FLEET_CLS klasi
            SpaceShip ship = AUX_FLEET_CLS.createSpaceShipsFromFileString(line);
            addSpaceShip(ship);
        }
    } catch (Exception e) {
        System.err.println("Error reading from file: " + e.getMessage());
    }
}
```

**Zašto?** 
- Apstrakcija: Sakriva složenost I/O operacija
- Delegacija: Odvaja odgovornosti (Fleet ne zna detalje čitanja datoteka)

---

## Struktura Podataka

### Zadatak 1
- **TreeSet<SpaceShip>**: Automatski sortirani skup brodova
- **List<String>**: Lista linija učitanih iz datoteke

### Zadatak 2
- **HashMap<String, Set<User>>**: Korisnici grupirani po platformi
  - Ključ: Platforma (String)
  - Vrijednost: Set korisnika (HashSet<User>)
- **HashSet<User>**: Sprječava duplikate korisnika (koristi equals()/hashCode())
- **HashSet<String>**: Skup dana aktivnosti korisnika

---

## Zaključak

Oba zadatka demonstriraju sveobuhvatnu primjenu OOP principa:

1. **Enkapsulacija**: Zaštita podataka kroz private atribute i kontrolirani pristup
2. **Nasljeđivanje**: Ponovno korištenje koda kroz extends (JFrame, JPanel)
3. **Polimorfizam**: Sučelja (Comparable, Serializable, ActionListener), override metoda
4. **Apstrakcija**: Sakrivanje složenosti implementacije
5. **Kompozicija/Agregacija**: Objekti sadrže druge objekte (has-a odnos)
6. **Delegacija**: Podjela odgovornosti među klasama

Projekti također demonstriraju:
- Rad s kolekcijama (TreeSet, HashMap, HashSet)
- Serijalizaciju objekata
- GUI razvoj sa Swingom
- I/O operacije (čitanje/pisanje datoteka)
- Event-driven programiranje (event listeners)
- Validaciju podataka
- Design patterns (Utility class, MVC-like)
