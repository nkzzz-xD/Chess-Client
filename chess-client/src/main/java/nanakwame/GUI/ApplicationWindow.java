package nanakwame.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class ApplicationWindow extends JFrame {

    private static final int DEFAULT_WINDOW_WIDTH = 1000;
    //get the toolbar height instead and use it to get the window height
    private static final int DEFAULT_WINDOW_HEIGHT = 850;
    // private static ApplicationWindow instance;

    public ApplicationWindow() {
        setLayout(new FlowLayout());  
        setSize(new Dimension(DEFAULT_WINDOW_WIDTH,DEFAULT_WINDOW_HEIGHT));
        add(GameBoard.getInstance()/*, BorderLayout.CENTER*/);
        Textures.loadTexturesFromZip("default-textures.zip",true);
        setVisible(true);
        // instance = this;
    }

    // public static ApplicationWindow getInstance(){
    //     return instance;
    // }
}
