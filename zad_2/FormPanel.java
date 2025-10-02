package SOLUTIONS.src.zad_2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class FormPanel extends JPanel {
    JTextField tfUserName, tfEmail;
    JComboBox<String> cbPlatform;
    JCheckBox cbMon, cbTue, cbThu, cbFri;
    JButton btnCancel, btnSubmit;
    JTextField tfSearch;
    JButton btnSearchClear;

    public FormPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15,20,5,20));

        JPanel leftPanel = new JPanel(new GridLayout(0,1,4,4));
        tfUserName = new JTextField(14);
        tfEmail = new JTextField(14);
        leftPanel.add(new JLabel("User name:"));
        leftPanel.add(tfUserName);
        leftPanel.add(new JLabel("E-mail:"));
        leftPanel.add(tfEmail);

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

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        cbPlatform = new JComboBox<>(new String[] {"Linux OS", "Win OS", "Mac OS"});
        JPanel platformPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));
        platformPanel.add(new JLabel("Platform:"));
        platformPanel.add(cbPlatform);
        rightPanel.add(platformPanel);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));
        searchPanel.add(new JLabel("Search field:"));
        tfSearch = new JTextField("Search...", 12);
        btnSearchClear = new JButton("X");
        searchPanel.add(tfSearch);
        searchPanel.add(btnSearchClear);
        rightPanel.add(searchPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));
        btnCancel = new JButton("Cancel");
        btnSubmit = new JButton("Submit");
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSubmit);

        JPanel topPanel = new JPanel(new GridLayout(1,2,10,10));
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.EAST);
    }

    public User getFormUser() {
        String name = tfUserName.getText().trim();
        String email = tfEmail.getText().trim();
        String platform = (String) cbPlatform.getSelectedItem();
        Set<String> days = new HashSet<>();
        if (cbMon.isSelected()) days.add("PON.");
        if (cbTue.isSelected()) days.add("UTO.");
        if (cbThu.isSelected()) days.add("ČET.");
        if (cbFri.isSelected()) days.add("PET.");
        if (name.isEmpty() || email.isEmpty() || platform == null) return null;
        return new User(name, email, platform, days);
    }

    public void clearForm() {
        tfUserName.setText("");
        tfEmail.setText("");
        cbPlatform.setSelectedIndex(0);
        cbMon.setSelected(false);
        cbTue.setSelected(false);
        cbThu.setSelected(false);
        cbFri.setSelected(false);
    }
}