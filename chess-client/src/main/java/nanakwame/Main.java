package nanakwame;

import javax.swing.SwingUtilities;
import nanakwame.GUI.ApplicationWindow;

public class Main {
    public static void main(String[] args) {
        // System.out.println("Hello world!");
        // new Board();
        SwingUtilities.invokeLater(ApplicationWindow :: new);
    }
}