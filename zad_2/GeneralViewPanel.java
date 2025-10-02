package SOLUTIONS.src.zad_2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GeneralViewPanel extends JPanel {
    JTextArea dataArea;

    public GeneralViewPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8,20,4,20));
        dataArea = new JTextArea(3, 40);
        dataArea.setEditable(false);
        add(new JLabel("Prikaz podataka:"), BorderLayout.NORTH);
        add(new JScrollPane(dataArea), BorderLayout.CENTER);
    }

    public void showData(String txt) {
        dataArea.setText(txt);
    }
}