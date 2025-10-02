package SOLUTIONS.src.zad_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MainFrame extends JFrame {
    private Map<String, Set<User>> userMap = new HashMap<>();
    private FormPanel formPanel;
    private GeneralViewPanel generalPanel;
    private SearchViewPanel searchPanel;

    public MainFrame() {
        setTitle("User Platform Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);

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

        formPanel = new FormPanel();
        generalPanel = new GeneralViewPanel();
        searchPanel = new SearchViewPanel();
        searchPanel.setPanelActive(false);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(generalPanel, BorderLayout.NORTH);
        centerPanel.add(searchPanel, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        formPanel.btnSubmit.addActionListener(e -> {
            User user = formPanel.getFormUser();
            if (user != null) {
                userMap.putIfAbsent(user.getPlatform(), new HashSet<>());
                if (userMap.get(user.getPlatform()).add(user)) {
                    System.out.println("Dodajem korisnika: " + user);
                    showAllUsers();
                    formPanel.clearForm();
                } else {
                    System.out.println("Pokušaj dodavanja duplikata korisnika: " + user);
                    JOptionPane.showMessageDialog(this, "Korisnik već postoji!", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Neuspješan unos - neispravni podaci.");
            }
        });

        formPanel.btnCancel.addActionListener(e -> {
            System.out.println("Brisanje forme za unos.");
            formPanel.clearForm();
        });

        saveItem.addActionListener(e -> {
            System.out.println("Spremam podatke u users.bin...");
            saveData();
        });
        loadItem.addActionListener(e -> {
            System.out.println("Učitavam podatke iz users.bin...");
            loadData();
        });
        exitItem.addActionListener(e -> {
            System.out.println("Izlaz iz aplikacije.");
            System.exit(0);
        });

        formPanel.btnSearchClear.addActionListener(e -> {
            System.out.println("Brisanje polja za pretragu.");
            formPanel.tfSearch.setText("");
            searchPanel.showSearchData("");
            searchPanel.setPanelActive(false);
            System.out.println("Pretraga obrisana.");
        });

        formPanel.tfSearch.addActionListener(e -> {
            System.out.println("Pokrećem pretragu za OS: " + formPanel.tfSearch.getText().trim());
            performSearch();
        });

        showAllUsers();
        System.out.println("Aplikacija pokrenuta.");
    }

    private void showAllUsers() {
        StringBuilder sb = new StringBuilder();
        for (var entry : userMap.entrySet()) {
            for (User u : entry.getValue()) {
                sb.append(u).append("\n");
            }
        }
        generalPanel.showData(sb.toString());
        System.out.println("Prikaz svih korisnika:");
        System.out.println(sb.toString());
    }

    private void performSearch() {
        String os = formPanel.tfSearch.getText().trim();
        if (os.isEmpty()) {
            String msg = "Unesite naziv platforme za pretragu.";
            searchPanel.showSearchData(msg);
            searchPanel.setPanelActive(true);
            System.out.println("Pretraga nije pokrenuta - polje prazno.");
            System.out.println(msg);
            return;
        }
        if (userMap.containsKey(os)) {
            StringBuilder sb = new StringBuilder();
            for (User u : userMap.get(os)) {
                sb.append(u).append("\n");
            }
            searchPanel.showSearchData(sb.toString());
            searchPanel.setPanelActive(true);
            System.out.println("Pronađeni korisnici za OS: " + os);
            System.out.println(sb.toString());
        } else {
            String msg = "Nema korisnika za OS: " + os;
            searchPanel.showSearchData(msg);
            searchPanel.setPanelActive(true);
            System.out.println(msg);
        }
    }

    private void saveData() {
        try {
            File dataDir = new File("data");
            if (!dataDir.exists()) dataDir.mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/users.bin"));
            oos.writeObject(userMap);
            oos.close();
            System.out.println("Podaci uspješno spremljeni.");
        } catch (Exception ex) {
            System.out.println("Greška kod spremanja podataka!");
            JOptionPane.showMessageDialog(this, "Greška kod spremanja!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/users.bin"));
            Object obj = ois.readObject();
            if (obj instanceof HashMap) {
                userMap = (HashMap<String, Set<User>>) obj;
                showAllUsers();
                System.out.println("Podaci uspješno učitani.");
            }
            ois.close();
        } catch (Exception ex) {
            System.out.println("Greška kod učitavanja podataka!");
            JOptionPane.showMessageDialog(this, "Greška kod učitavanja!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}