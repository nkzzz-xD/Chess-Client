package nanakwame.GUI.TitleScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class TitleScreenButton extends JButton {
    private static final Color BACKGROUND_COLOR = Color.GRAY;
    private static final Color FOREGROUND_COLOR = Color.WHITE;
    private static final Color HOVER_COLOR = Color.LIGHT_GRAY;
    private static final Color PRESSED_COLOR = Color.DARK_GRAY;    // Change to your desired pressed color
    
    public TitleScreenButton(String text, ActionListener actionListener) {
        super(text);
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        setFont(TitleScreen.titleFont.deriveFont(30f));
        setPreferredSize(new Dimension(500, 40));

         // Ensure the text is centered both horizontally and vertically
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);

        // Set custom margin (internal padding) to ensure the text is centered correctly
        setMargin(new Insets(0, 0, 0, 0));
        // Remove default borders and fill
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(HOVER_COLOR); // Change background on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(BACKGROUND_COLOR); // Reset background color
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(PRESSED_COLOR); // Change background on press
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(HOVER_COLOR); // Reset to hover color on release
            }
        });
        addActionListener(actionListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color and draw a rounded rectangle
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);  // Rounded corners (30px radius)

        // Get the font metrics to calculate text positioning
        FontMetrics fm = g2d.getFontMetrics(getFont());
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();

        // Calculate x and y positions to center the text
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2;

        // Draw the text in the center
        g2d.setColor(getForeground());
        g2d.drawString(getText(), x, y);

        g2d.dispose();
    }
}
