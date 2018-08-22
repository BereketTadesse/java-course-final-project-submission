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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// Since there are other ways of handling mouse inputs that are not clicks,
// it's simpler to override every method from MouseListener that's not "mouseClicked" once through an abstract class
// instead of doing it every time a listener is added
public abstract class MouseClickListener implements MouseListener {
    @Override
    public abstract void mouseClicked(MouseEvent e);

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}