package nanakwame;

import javax.swing.SwingUtilities;
import nanakwame.GUI.ApplicationWindow;
import nanakwame.GUI.Images;

public class Main {
    public static void main(String[] args) {
        Images.loadTexturesFromZip("default-textures.zip",true);
        SwingUtilities.invokeLater(ApplicationWindow :: new);
    }
}