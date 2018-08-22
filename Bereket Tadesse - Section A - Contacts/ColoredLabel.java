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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class ColoredLabel extends JLabel {
    private Font currentFont = new Font("Sans Serif", Font.PLAIN, 12);

    ColoredLabel() {
        setForeground(Color.LIGHT_GRAY);
        this.setFont(currentFont);
    }

    ColoredLabel(String text) {
        this();
        setText(text);
    }

    private ColoredLabel(Color color) {
        setForeground(color);
        this.setFont(currentFont);
    }

    ColoredLabel(String text, Color color) {
        this(color);
        setText(text);
    }

    void setFontSize(int newSize) {
        currentFont = new Font(currentFont.getFontName(), currentFont.getStyle(), newSize);
        this.setFont(currentFont);
    }

    void setBold() {
        currentFont = new Font(currentFont.getFontName(), Font.BOLD , currentFont.getSize());
        this.setFont(currentFont);
    }
}

class ColoredLink extends ColoredLabel {
    private static Color LINK_HOVER_COLOR = new Color(100, 155, 180);

    ColoredLink(String text) {
        super(text, MainScreen.LINK_COLOR);
        addMouseListener(new LinkHoverListener());
    }

    private class LinkHoverListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) {
            setForeground(LINK_HOVER_COLOR);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setForeground(MainScreen.LINK_COLOR);
        }

        @Override
        public void mousePressed(MouseEvent e) { }

        @Override
        public void mouseReleased(MouseEvent e) { }
    }
}