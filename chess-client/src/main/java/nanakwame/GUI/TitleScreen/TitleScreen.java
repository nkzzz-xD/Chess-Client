package nanakwame.GUI.TitleScreen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nanakwame.GUI.ApplicationWindow;
import nanakwame.GUI.GameBoard;
import nanakwame.GUI.Images;

public class TitleScreen extends JPanel{
    private static TitleScreen instance;
    private BufferedImage backgroundImage;
    private int imageWidth = 0;
    private int imageHeight = 0;
    protected static final Font DISPLAY_FONT = new Font("Calibri",Font.BOLD,30);
    protected static Font titleFont;
    //if background image fails to load
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;

    //TODO Add accessibility, langauges and settings in top right

    private static final ActionListener SWITCH_TO_LOCAL_GAME = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ApplicationWindow window = ApplicationWindow.getInstance();
            window.setContentPane(GameBoard.getInstance());
            window.revalidate();
            window.repaint();
        } 
    };

    public TitleScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        int panelPadding = 90;
        setBorder(BorderFactory.createEmptyBorder(panelPadding, panelPadding, panelPadding, panelPadding));
        setBackground(DEFAULT_BACKGROUND_COLOR);
        backgroundImage = Images.getTexture("title-bg");
        if (backgroundImage != null) {
            imageWidth = backgroundImage.getWidth();
            imageHeight = backgroundImage.getHeight();
        }
        titleFont = DISPLAY_FONT.deriveFont(110f);
        try{
            titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/MeatbustersBold.otf")).deriveFont(130f);
            GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
            gE.registerFont(titleFont);
        }
        catch (IOException | FontFormatException e){
            System.err.println("Error loading title font file");
        }
        JLabel titleLabel = new JLabel("CHESS");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(255, 234, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        JPanel miniPanel = new JPanel();
        miniPanel.setOpaque(false);
        miniPanel.setLayout(new CustomFlowLayout(FlowLayout.CENTER, 4000, 50));
        miniPanel.add(new TitleScreenButton("Play against a friend", SWITCH_TO_LOCAL_GAME));
        miniPanel.add(new TitleScreenButton("Play against the computer",null));
        miniPanel.add(new TitleScreenButton("Play online",null));
        add(miniPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Calculate the scaling factors to make the image cover the entire panel
            double scaleX = (double) panelWidth / imageWidth;
            double scaleY = (double) panelHeight / imageHeight;

            // Choose the larger scale to ensure the image covers the entire panel (crop the excess)
            double scale = Math.max(scaleX, scaleY);

            // Calculate the size of the scaled image
            int drawWidth = (int) (scale * imageWidth);
            int drawHeight = (int) (scale * imageHeight);

            // Calculate the offsets to center the image on the panel
            int xOffset = (panelWidth - drawWidth) / 2;
            int yOffset = (panelHeight - drawHeight) / 2;

            // Draw the image, cropped to fill the panel
            g.drawImage(backgroundImage, xOffset, yOffset, drawWidth, drawHeight, this);
        }
    }

    public static TitleScreen getInstance() {
        return instance == null ? instance = new TitleScreen() : instance;
    }

    public class CustomFlowLayout extends FlowLayout {
        public CustomFlowLayout(int align, int hgap, int vgap) {
            super(align,hgap,vgap);
        }
    
        @Override
        public void layoutContainer(Container target) {
            super.layoutContainer(target);  // Call the parent method to do the basic layout
    
            // Remove the vertical gap for the first row
            Component[] components = target.getComponents();
            if (components.length > 0) {
                int y = components[0].getY(); // Get the y-position of the first component
                for (Component component : components) {
                    // Adjust the y-position of the first row components
                    if (component.getY() == y) {
                        component.setLocation(component.getX(), 0);
                    }
                    else {
                        component.setLocation(component.getX(), component.getY() - getVgap());
                    }
                }
            }
        }
    }
}
