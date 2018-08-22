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

public class NewContactDialog extends JDialog {
    private Container contentPane = getContentPane();
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    private ColoredLabel title = new ColoredLabel("Contact info");

    private ColoredLabel fname = new ColoredLabel("First name:");
    private ColoredLabel lname = new ColoredLabel("Last name:");
    private ColoredLabel phone[] = new ColoredLabel[Contact.MAX_PHONES];
    private DarkTextField fnameField = new DarkTextField();
    private DarkTextField lnameField = new DarkTextField();
    private DarkTextField[] phoneFields = new DarkTextField[Contact.MAX_PHONES];

    private int phoneInputsCount = 1;

    private String notEnoughInfo = "Enter the full name and one phone number";
    private String phoneNumberAddError;
    private ColoredLabel genericErrorText = new ColoredLabel(notEnoughInfo, ErrorDialog.ERROR_TEXT_COLOR);

    private ColoredLink addPhoneLink = new ColoredLink("Add another phone number");

    private DarkButton addContact = new DarkButton("Add contact", DarkButton.BLUE);
    private DarkButton cancel = new DarkButton("Cancel");

    private static Contact contact;

    NewContactDialog() {
        init();
        contentPane.add(panel);
        URL imgurl = getClass().getResource("files/images/icon(32x32_light).png");
        if (imgurl != null) {
            ImageIcon icon = new ImageIcon(imgurl);
            setIconImage(icon.getImage());
        }
        setTitle("New contact");
        setSize(360, 640);
        setLocation(503, 64);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void init() {
        panel.setBackground(MainScreen.DARKER_GRAY);
        initTitle();
        initInputs();
        initButtons();
        addPhoneInputs();
        addLink();
        addInvisibleError();
    }

    private void addInvisibleError() {
        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(20, 60, 0, 0);
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(genericErrorText, constraints);
        genericErrorText.setVisible(false);
    }

    private void addLink() {
        constraints.gridx = 2;
        constraints.gridy = 8;
        constraints.insets = new Insets(5, 0, 10, 30);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridwidth = 1;
        panel.add(addPhoneLink, constraints);
        addPhoneLink.addMouseListener(new PhoneLinkAction());
    }

    private void addPhoneInputs() {
        for (int i = 0; i < Contact.MAX_PHONES; i++) {
            constraints.gridx = 0;
            constraints.gridy = 3 + i;
            constraints.insets = new Insets(0, 30, 10, 10);
            constraints.anchor = GridBagConstraints.EAST;
            constraints.gridwidth = 1;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = 0;
            if (i > 0)
                phone[i] = new ColoredLabel("Phone " + Integer.toString(i + 1) + ":");
            else
                phone[i] = new ColoredLabel("Phone:");
            panel.add(phone[i], constraints);
            if (i > 0)
                phone[i].setVisible(false);

            constraints.gridx = 1;
            constraints.insets = new Insets(0, 10, 10, 30);
            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridwidth = 2;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            phoneFields[i] = new DarkTextField("+");
            panel.add(phoneFields[i], constraints);
            if (i > 0)
                phoneFields[i].setVisible(false);
        }

    }

    private void initButtons() {
        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(50, 50, 30, 10);
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        // constraints.fill = GridBagConstraints.NONE;
        panel.add(addContact, constraints);
        addContact.addMouseListener(new AddContactButtonAction());

        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(50, 10, 30, 30);
        panel.add(cancel, constraints);
        cancel.addMouseListener(new CancelButtonAction());
    }

    private void initInputs() {
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 30, 10, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        panel.add(fname, constraints);

        constraints.gridy = 2;
        panel.add(lname, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 10, 10, 30);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        panel.add(fnameField, constraints);

        constraints.gridy = 2;
        panel.add(lnameField, constraints);
    }

    private void initTitle() {
        title.setFontSize(36);
        title.setBold();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 30, 40, 30);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        panel.add(title, constraints);
    }

    private class PhoneLinkAction extends MouseClickListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            String prevPhoneFieldContent = phoneFields[phoneInputsCount - 1].getText();
            if (prevPhoneFieldContent.isEmpty() || prevPhoneFieldContent.equals("+")) {
                phoneNumberAddError = "Enter Phone #" + Integer.toString(phoneInputsCount) + " before entering Phone #" + Integer.toString(phoneInputsCount + 1);
                genericErrorText.setText(phoneNumberAddError);
                genericErrorText.setVisible(true);
            }
            else if (phoneInputsCount < Contact.MAX_PHONES) {
                genericErrorText.setVisible(false);
                phone[phoneInputsCount].setVisible(true);
                phoneFields[phoneInputsCount].setVisible(true);
                phoneInputsCount++;
            }

            if (phoneInputsCount >= Contact.MAX_PHONES) {
                addPhoneLink.setVisible(false);
            }
        }
    }

    private class CancelButtonAction extends MouseClickListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            dispose();
        }
    }

    private class AddContactButtonAction extends MouseClickListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            String fnameFieldContent = fnameField.getText();
            String lnameFieldContent = lnameField.getText();
            String phoneFieldContent = phoneFields[0].getText();

            if (fnameFieldContent.isEmpty() || lnameFieldContent.isEmpty() ||
                    phoneFieldContent.isEmpty() || phoneFieldContent.equals("+")) {
                genericErrorText.setVisible(false);
                genericErrorText.setText(notEnoughInfo);
                genericErrorText.setVisible(true);
            }
            else {
                genericErrorText.setVisible(false);
                String[] numbers = new String[Contact.MAX_PHONES];
                int actualElements = 0;

                for (int i = 0; i < Contact.MAX_PHONES; i++) {
                    if (!phoneFields[i].getText().isEmpty() && !phoneFields[i].getText().equals("+")) {
                        numbers[i] = phoneFields[i].getText();
                        actualElements++;
                    }
                }

                String[] temp = new String[actualElements];
                for (int i = 0; i < actualElements; i++)
                    temp[i] = numbers[i];
                numbers = temp;
                if (FileHandler.lifetimeFileExists())
                    FileHandler.readLifetimeFile();
                contact = new Contact(fnameField.getText(), lnameField.getText(), numbers);

                if (!FileHandler.saveContactFile(contact))
                    new ContactSaveError();
                else {
                    MainScreen.updateContactList(contact, MainScreen.ADD);
                    // FileHandler.readContactFile(10000);
                    dispose();
                }
            }
        }
    }
}
