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

public class ListModel extends AbstractListModel {
    String[] names;

    ListModel() {

    }

    ListModel(String[] nameList) {
        names = nameList;
        sort();
    }

    public int getSize() {
        return names.length;
    }

    public Object getElementAt(int index) {
        return (String)names[index];
    }

    public void addElement(String element) {
        String[] newList = new String[names.length + 1];
        for (int i = 0; i < names.length; i++)
            newList[i] = names[i];
        newList[names.length] = element;
        sort();
    }

    public void sort() {
        String temp;
        for (int i = 0; i < names.length; i++) {
            for (int j = i + 1; j < names.length; j++) {
                if (names[i].compareTo(names[j]) > 0) {
                    temp = names[i];
                    names[i] = names[j];
                    names[j] = temp;
                }
            }
        }
        fireContentsChanged(this, 0, names.length);
    }
}
