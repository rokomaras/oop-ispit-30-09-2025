package SOLUTIONS.src.zad_2;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e){ }
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}