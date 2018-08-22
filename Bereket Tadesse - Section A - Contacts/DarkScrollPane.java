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
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class DarkScrollPane extends JScrollPane {
    DarkScrollPane() {
        initColor();
    }

    DarkScrollPane(JList list) {
        super(list);
        initColor();
    }

    private void initColor() {
        setBackground(MainScreen.DARKER_GRAY);
        setForeground(Color.LIGHT_GRAY);
        getVerticalScrollBar().setUI(new DarkScrollBar());
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setBorder(new EmptyBorder(0, 20, 0, 0));

    }
}

class DarkScrollBar extends BasicScrollBarUI {
    private static final int PREFERED_WIDTH = 1;
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static JButton noButton = new JButton();
    private static final Dimension NO_SIZE = new Dimension(0,0);

    DarkScrollBar() {
        initButton();
    }

    private void initButton() {
        noButton.setMaximumSize(NO_SIZE);
        noButton.setMinimumSize(NO_SIZE);
        noButton.setPreferredSize(NO_SIZE);
    }

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(80, 80, 80);
        this.thumbDarkShadowColor = TRANSPARENT;
        this.trackColor = MainScreen.DARKER_GRAY;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return noButton;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return noButton;
    }

    /*
    @Override
    protected void installComponents() {

        incrButton = createIncreaseButton(NORTH);
        decrButton = createDecreaseButton(SOUTH);
        scrollbar.setEnabled(scrollbar.isEnabled());

    }
    */
}