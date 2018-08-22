/**
 * HiLCoE School of Computer Science and Technology
 * 
 * Object Oriented Programming (CS224) Final Project
 * 
 * Project Title   : Minimalist Contacts App
 * Submission Date : August 22, 2018
 * 
 * Compilation instructions :
 *      Compile with *.java and use the "Run" class to start the program
 * 
 * Name:     Bereket Tadesse
 * ID:       OX2122
 * Section:  A
 * Email:    b.tadesse.77@gmail.com
 * 
 */

import javax.swing.*;
import java.awt.*;

/**
 * Custom class for the creation of dark themed JButtons
 */
public class DarkButton extends JButton {
    private static final Color FONT_COLOR = new Color(200, 200, 200);
    static final Color DEFAULT_COLOR = new Color(80, 80,80);
    private static final Color DEFAULT_PRESSED_COLOR = new Color(96, 96, 96);
    private static final Color DEFAULT_DISABLED_COLOR = new Color(72, 72, 72);
    private static final Color GREEN_COLOR = new Color(80, 128,80);
    private static final Color GREEN_PRESSED_COLOR = new Color(96, 160,96);
    private static final Color RED_COLOR = new Color(128, 80,80);
    private static final Color RED_PRESSED_COLOR = new Color(160, 96,96);
    private static final Color BLUE_COLOR = new Color(64, 96, 128 );
    private static final Color BLUE_PRESSED_COLOR = new Color(64, 128, 160 );

    static final int GREEN = 1;
    static final int RED = 2;
    static final int BLUE = 3;


    private DarkButton() {
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBackground(DEFAULT_COLOR);
        setForeground(FONT_COLOR);
    }

    DarkButton(String text) {
        this();
        setText(text);
    }

    // Green, red or blue distinguished buttons
    DarkButton(String text, int selectedColor) {
        setText(text);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.LIGHT_GRAY);

        if (selectedColor == GREEN)
            setBackground(GREEN_COLOR);
        else if (selectedColor == RED)
            setBackground(RED_COLOR);
        else
            setBackground(BLUE_COLOR);
    }

    // Paints the button the appropriate " *_PRESSED " color when clicked
    // Treats the button as a Graphics object
    public void paintComponent(Graphics buttonGraphics) {
        if (getModel().isPressed()) {
            if (getBackground().equals(GREEN_COLOR))
                buttonGraphics.setColor(GREEN_PRESSED_COLOR);
            else if (this.getBackground().equals(RED_COLOR))
                buttonGraphics.setColor(RED_PRESSED_COLOR);
            else if (getBackground().equals(BLUE_COLOR))
                buttonGraphics.setColor(BLUE_PRESSED_COLOR);
            else
                buttonGraphics.setColor(DEFAULT_PRESSED_COLOR);
        }
        else
            buttonGraphics.setColor(getBackground());

        buttonGraphics.fillRect(0, 0, getWidth(), getHeight());

        if (getModel().isRollover())
            setForeground(Color.white);
        else
            setForeground(FONT_COLOR);

        if (!isEnabled())
            setBackground(DEFAULT_DISABLED_COLOR);

        super.paintComponent(buttonGraphics);
    }
}
