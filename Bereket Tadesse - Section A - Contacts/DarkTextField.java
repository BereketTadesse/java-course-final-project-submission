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
import javax.swing.border.LineBorder;
import java.awt.*;

class DarkTextField extends JTextField {
    private static Color DEFAULT_COLOR = new Color(72,72,72);
    private static Color FONT_COLOR = new Color(146, 146, 146);
    public static Color DEFAULT_BORDER_COLOR = new Color(56, 56, 56);

    DarkTextField() {
        adjustColor(DEFAULT_COLOR);
    }

    DarkTextField(String text) {
        this();
        setText(text);
    }

    DarkTextField(Color color) {
        adjustColor(color);
    }

    DarkTextField(String text, Color color) {
        this(color);
        setText(text);
    }

    private void adjustColor(Color color) {
        setBackground(color);
        setBorder(new LineBorder(DEFAULT_BORDER_COLOR));
        setForeground(FONT_COLOR);
    }
}
