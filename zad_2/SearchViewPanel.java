package SOLUTIONS.src.zad_2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SearchViewPanel extends JPanel {
    JTextArea searchArea;

    public SearchViewPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8,20,8,20));
        searchArea = new JTextArea(2, 40);
        searchArea.setEditable(false);
        searchArea.setBackground(Color.LIGHT_GRAY);
        add(new JLabel("Prikaz pretrage:"), BorderLayout.NORTH);
        add(new JScrollPane(searchArea), BorderLayout.CENTER);
        setEnabled(false);
    }

    public void showSearchData(String txt) {
        searchArea.setText(txt);
    }

    public void setPanelActive(boolean active) {
        setEnabled(active);
        searchArea.setEnabled(active);
    }
}