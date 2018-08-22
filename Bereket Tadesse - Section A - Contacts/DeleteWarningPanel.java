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
import java.net.URL;

public class DeleteWarningPanel extends JDialog {
    private Container contentPane = getContentPane();
    private static JPanel panel = new JPanel(new GridBagLayout());
    private static GridBagConstraints panelConstraints = new GridBagConstraints();

    private static ColoredLabel deleteTitle = new ColoredLabel("Delete contact?", ErrorDialog.ERROR_TEXT_COLOR);
    private static ColoredLabel areYouSure = new ColoredLabel("Are you sure?");
    private static ColoredLabel canNotBeRecovered = new ColoredLabel("(Deleted contacts can't be recovered)");

    private static DarkButton deleteButton = new DarkButton("Delete", DarkButton.RED);
    private static DarkButton cancelButton = new DarkButton("Cancel");

    DeleteWarningPanel() {
        init();
        contentPane.add(panel);
        URL imgurl = getClass().getResource("files/images/icon(32x32_light).png");
        if (imgurl != null) {
            ImageIcon icon = new ImageIcon(imgurl);
            setIconImage(icon.getImage());
        }
        setTitle("Confirm delete");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(533, 250);
        pack();
        setVisible(true);
    }

    private void init() {
        contentPane.setBackground(MainScreen.DARKER_GRAY);
        contentPane.setForeground(Color.LIGHT_GRAY);
        initPanel();
        initActionListeners();
    }

    private void initPanel() {
        panel.setBackground(MainScreen.DARKER_GRAY);
        panel.setForeground(Color.LIGHT_GRAY);

        deleteTitle.setFontSize(20);
        deleteTitle.setBold();

        panelConstraints.gridx = 0;
        panelConstraints.gridy = 0;
        panelConstraints.insets = new Insets(20, 20, 0, 20);
        panelConstraints.gridwidth = 2;
        panelConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(deleteTitle, panelConstraints);

        panelConstraints.insets.top = 20;
        areYouSure.setFontSize(12);
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 1;
        panelConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(areYouSure, panelConstraints);

        canNotBeRecovered.setFontSize(11);
        panelConstraints.insets.top = 10;
        panelConstraints.insets.bottom = 30;
        areYouSure.setFontSize(12);
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 2;
        panel.add(canNotBeRecovered, panelConstraints);

        panelConstraints.insets.top = 0;
        panelConstraints.insets.left = 30;
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 3;
        panelConstraints.anchor = GridBagConstraints.EAST;
        panelConstraints.gridwidth = 1;
        panel.add(deleteButton, panelConstraints);

        panelConstraints.insets.left = 0;
        panelConstraints.gridx = 1;
        panelConstraints.gridy = 3;
        panelConstraints.gridwidth = 1;
        panelConstraints.anchor = GridBagConstraints.WEST;
        panel.add(cancelButton, panelConstraints);
    }

    private void initActionListeners() {
        deleteButton.addMouseListener(new DeleteButtonAction());
        cancelButton.addMouseListener(new CancelButtonAction());
    }

    class DeleteButtonAction extends MouseClickListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            String deleteIdString = ContactDetailsPane.id.getText().substring(1);
            int deleteId = Integer.parseInt(deleteIdString);
            for (int i = 0; i < MainScreen.allContacts.length; i++)
                if (MainScreen.allContacts[i].getId() == deleteId)
                    MainScreen.updateContactList(MainScreen.allContacts[i], MainScreen.DELETE);
            FileHandler.deleteContactFile(deleteId);
            dispose();
        }
    }

    class CancelButtonAction extends MouseClickListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            dispose();
        }
    }
}
