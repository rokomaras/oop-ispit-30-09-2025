package SOLUTIONS.src.zad_2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Panel za unos korisničkih podataka i pretragu.
 * 
 * OOP Principi:
 * - NASLJEĐIVANJE: Nasljeđuje JPanel klasu (extends JPanel) - "is-a" odnos
 * - ENKAPSULACIJA: Package-private atributi pristupni iz MainFrame-a, javne metode za interakciju
 * - KOMPOZICIJA: Sadrži Swing komponente (JTextField, JComboBox, JCheckBox, JButton)
 * - APSTRAKCIJA: Sakriva složenost layout managementa i validacije unosa
 */
public class FormPanel extends JPanel {
    // Package-private atributi - dostupni iz MainFrame klase
    JTextField tfUserName, tfEmail;       // Text field-ovi za unos
    JComboBox<String> cbPlatform;         // Combo box za odabir platforme
    JCheckBox cbMon, cbTue, cbThu, cbFri; // Checkbox-ovi za odabir dana
    JButton btnCancel, btnSubmit;         // Gumbi za akcije
    JTextField tfSearch;                  // Text field za pretragu
    JButton btnSearchClear;               // Gumb za brisanje pretrage

    /**
     * Konstruktor koji inicijalizira i postavlja GUI komponente panela.
     * 
     * OOP Principi:
     * - NASLJEĐIVANJE: Koristi metode iz JPanel klase (setLayout, setBorder, add)
     * - KOMPOZICIJA: Kreira i organizira višestruke GUI komponente
     * - APSTRAKCIJA: Sakriva složenost layout managementa (BorderLayout, GridLayout, FlowLayout)
     */
    public FormPanel() {
        // NASLJEĐIVANJE: Postavljanje layout-a i granice kroz metode iz JPanel
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15,20,5,20));

        // Lijevi panel - unos imena, emaila i dana
        JPanel leftPanel = new JPanel(new GridLayout(0,1,4,4));
        tfUserName = new JTextField(14);
        tfEmail = new JTextField(14);
        leftPanel.add(new JLabel("User name:"));
        leftPanel.add(tfUserName);
        leftPanel.add(new JLabel("E-mail:"));
        leftPanel.add(tfEmail);

        // Panel za checkboxove dana
        JPanel daysPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));
        cbMon = new JCheckBox("PON.");
        cbTue = new JCheckBox("UTO.");
        cbThu = new JCheckBox("ČET.");
        cbFri = new JCheckBox("PET.");
        daysPanel.add(cbMon);
        daysPanel.add(cbTue);
        daysPanel.add(cbThu);
        daysPanel.add(cbFri);
        leftPanel.add(daysPanel);

        // Desni panel - odabir platforme i pretraga
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
        // Panel za odabir platforme
        cbPlatform = new JComboBox<>(new String[] {"Linux OS", "Win OS", "Mac OS"});
        JPanel platformPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));
        platformPanel.add(new JLabel("Platform:"));
        platformPanel.add(cbPlatform);
        rightPanel.add(platformPanel);

        // Panel za pretragu
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));
        searchPanel.add(new JLabel("Search field:"));
        tfSearch = new JTextField("Search...", 12);
        btnSearchClear = new JButton("X");
        searchPanel.add(tfSearch);
        searchPanel.add(btnSearchClear);
        rightPanel.add(searchPanel);

        // Panel za gumbe (Cancel i Submit)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));
        btnCancel = new JButton("Cancel");
        btnSubmit = new JButton("Submit");
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSubmit);

        // Organizacija svih panela u glavni layout
        JPanel topPanel = new JPanel(new GridLayout(1,2,10,10));
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.EAST);
    }

    /**
     * Dohvaća korisnika iz forme s validacijom.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Javna metoda koja čita privatno stanje GUI komponenti
     * - APSTRAKCIJA: Sakriva detalje prikupljanja i validacije podataka iz forme
     * - Vraća null ako podaci nisu valjani (validacija)
     * 
     * @return User objekt s podacima iz forme ili null ako podaci nisu valjani
     */
    public User getFormUser() {
        // Prikupljanje podataka iz text field-ova
        String name = tfUserName.getText().trim();
        String email = tfEmail.getText().trim();
        String platform = (String) cbPlatform.getSelectedItem();
        
        // Prikupljanje odabranih dana iz checkboxova
        Set<String> days = new HashSet<>();
        if (cbMon.isSelected()) days.add("PON.");
        if (cbTue.isSelected()) days.add("UTO.");
        if (cbThu.isSelected()) days.add("ČET.");
        if (cbFri.isSelected()) days.add("PET.");
        
        // Validacija - provjerava jesu li sva obavezna polja popunjena
        if (name.isEmpty() || email.isEmpty() || platform == null) return null;
        
        // Kreiranje i vraćanje User objekta
        return new User(name, email, platform, days);
    }

    /**
     * Briše sadržaj svih polja forme.
     * 
     * OOP Principi:
     * - ENKAPSULACIJA: Javna metoda koja modificira privatno stanje GUI komponenti
     * - APSTRAKCIJA: Sakriva detalje resetiranja svake komponente
     */
    public void clearForm() {
        // Resetiranje text field-ova
        tfUserName.setText("");
        tfEmail.setText("");
        
        // Resetiranje combo box-a na prvu opciju
        cbPlatform.setSelectedIndex(0);
        
        // Deselektiranje svih checkboxova
        cbMon.setSelected(false);
        cbTue.setSelected(false);
        cbThu.setSelected(false);
        cbFri.setSelected(false);
    }
}