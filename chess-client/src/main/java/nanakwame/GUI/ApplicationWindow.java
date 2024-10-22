package nanakwame.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import nanakwame.GUI.TitleScreen.TitleScreen;

public class ApplicationWindow extends JFrame {

    private static final int DEFAULT_WINDOW_WIDTH = 1000;
    //get the toolbar height instead and use it to get the window height
    private static final int DEFAULT_WINDOW_HEIGHT = 850;
    private static ApplicationWindow instance;

    //TODO Fix issues with different resolutions.
    public ApplicationWindow() {
        setLayout(new FlowLayout());  
        Dimension defaultSize = new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        setMinimumSize(defaultSize);
        setPreferredSize(defaultSize);
        setSize(defaultSize);
        setTitle("Chess");
        // add(GameBoard.getInstance()/*, BorderLayout.CENTER*/);
        setContentPane(TitleScreen.getInstance());
        try {
            //icon <a href="https://www.flaticon.com/free-icons/chess" title="chess icons">Chess icons created by Freepik - Flaticon</a>
            BufferedImage iconImage = ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
            setIconImage(iconImage);
        } catch (IOException e) {
           System.err.println("Icon image failed to load.");
        }
        setResizable(true);
        setVisible(true);
        instance = this;
    }

    public static ApplicationWindow getInstance(){
        return instance == null ? new ApplicationWindow() : instance;
    }
}
