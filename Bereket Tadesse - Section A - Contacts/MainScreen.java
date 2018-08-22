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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;

public class MainScreen {
    private static JFrame frame = new JFrame("Contacts");
    private static Container contentPane = frame.getContentPane();

    static final Color DARKER_GRAY = new Color(48, 48, 48);
    static final Color MAIN_PANEL_COLOR = new Color(56, 56, 56);
    static final Color LINK_COLOR = new Color(49, 115, 175);

    private static JPanel titleContainer = new JPanel();
    static JPanel panel = new JPanel(new BorderLayout());

    private JPanel addContactButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private DarkButton addContactBtn = new DarkButton("New contact", DarkButton.GREEN);

    private User user = new User();
    static DarkList contactsList;
    static Contact[] allContacts;
    private static String[] allContactNames;
    private static DefaultListModel listModel = new DefaultListModel();

    static final int DELETE = 0;
    static final int ADD = 1;


    MainScreen() {
        init();

        panel.setBackground(MAIN_PANEL_COLOR);

        titleContainer.setBackground(DARKER_GRAY);
        titleContainer.setLayout(new GridBagLayout());
        titleContainer.setLayout(new FlowLayout(FlowLayout.LEFT));

        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(titleContainer, BorderLayout.NORTH);
        contentPane.add(addContactButtonContainer, BorderLayout.SOUTH);

        URL imgurl = getClass().getResource("files/images/icon(32x32_light).png");
        if (imgurl != null) {
            ImageIcon icon = new ImageIcon(imgurl);
            frame.setIconImage(icon.getImage());
        }
        frame.setSize(720, 720);
        frame.setLocation(323, 24);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void init() {
        initWelcomeMessage();
        initBottomBar();
        initContactList();
    }

    private void initBottomBar() {
        addContactButtonContainer.setBackground(new Color(40, 40, 40));
        addContactButtonContainer.setBorder(new EmptyBorder(10, 20, 10, 20));
        addContactBtn.addMouseListener(new NewContactButtonAction());
        addContactButtonContainer.add(addContactBtn);
    }

    private static void addListListener() {
        contactsList.addListSelectionListener(new ListSelectionAction());
    }

    private static void initContactList() {
        if (!FileHandler.maxFileExists()) {
            panel.add(new ContactDetailsPane(false), BorderLayout.CENTER);
            return;
        }
        Contact.availableContacts = FileHandler.readMaxFile();
        if (Contact.availableContacts == 0) {
            panel.add(new ContactDetailsPane(false), BorderLayout.CENTER);
            return;
        }
        allContacts = FileHandler.readAllContacts();
        generateContactNames();
        generateContactList(false);
    }

    private static void generateContactNames() {
        allContactNames = new String[allContacts.length];
        for (int i = 0; i < allContacts.length; i++) {
            allContactNames[i] = allContacts[i].getFname();
            if (!allContacts[i].getLname().isEmpty())
                allContactNames[i] += " " + allContacts[i].getLname();
        }
    }

    @SuppressWarnings("unchecked")
    private static void generateContactList(boolean isUpdate) {
        DarkScrollPane listScrollPane;
        if (isUpdate)
            listModel.addElement(allContactNames[allContactNames.length - 1]);
        else {
            listModel = new DefaultListModel();
            sortContactList();
            for (int i = 0; i < allContactNames.length; i++)
                listModel.addElement(allContactNames[i]);
        }
        contactsList = new DarkList();
        contactsList.setModel(listModel);
        contentPane.setVisible(false);
        listScrollPane = new DarkScrollPane(contactsList);
        contactsList.ensureIndexIsVisible(contactsList.getSelectedIndex());
        contentPane.add(listScrollPane, BorderLayout.WEST);
        contentPane.setVisible(true);
        addListListener();
        panel.removeAll();
        panel.add(new ContactDetailsPane(allContacts[0]));
    }

    private static void sortContactList() {
        String temp;
        String currentContactName, nextContactName;
        Contact tempContact;
        for (int i = 0; i < allContactNames.length; i++) {
            for (int j = i + 1; j < allContactNames.length; j++) {
                if (allContactNames[i].compareTo(allContactNames[j]) > 0) {
                    temp = allContactNames[i];
                    allContactNames[i] = allContactNames[j];
                    allContactNames[j] = temp;
                }
            }
        }
        for (int i = 0; i < allContacts.length; i++) {
            for (int j = i + 1; j < allContacts.length; j++) {
                currentContactName = allContacts[i].getFname() + " " + allContacts[i].getLname();
                nextContactName = allContacts[j].getFname() + " " + allContacts[j].getLname();
                if (currentContactName.compareTo(nextContactName) > 0) {
                    tempContact = allContacts[i];
                    allContacts[i] = allContacts[j];
                    allContacts[j] = tempContact;
                }
            }
        }
    }

    private static int getIndex(Contact newContact) {
        int position = 0;
        for (int i = 0; i < allContactNames.length; i++) {
            if (allContactNames[i].equals(newContact.getFname() + " " + newContact.getLname())) {
                position = i;
                break;
            }
        }
        // System.out.println(position);
        return position;
    }

    @SuppressWarnings("unchecked")
    static void updateContactList(Contact contact, int action) {
        Contact[] newContactsList;
        int position;
        if (action == ADD) {
            if (Contact.getAvailableContacts() > 1) {
                newContactsList = new Contact[allContacts.length + 1];
                for (int i = 0; i < allContacts.length; i++)
                    newContactsList[i] = allContacts[i];
                newContactsList[newContactsList.length - 1] = contact;
                allContacts = newContactsList;
                generateContactNames();
                sortContactList();
                position = getIndex(contact);
                listModel.add(position, allContactNames[position]);
            }
            else
                initContactList();
        }
        else if (action == DELETE) {
            newContactsList = new Contact[allContacts.length - 1];
            position = getIndex(contact);
            for (int i = 0; i < allContacts.length; i++) {
                if (i < position)
                    newContactsList[i] = allContacts[i];
                else if (i == position)
                    continue; // Should have avoided this, but it works
                else
                    newContactsList[i - 1] = allContacts[i];
            }
            contactsList.setSelectedIndex(position + 1);
            listModel.remove(position);
            allContacts = newContactsList;
            generateContactNames();
            sortContactList();
            Contact.availableContacts--;
            panel.removeAll();
        }
    }

    private void initWelcomeMessage() {
        if (FileHandler.userFileExists())
            FileHandler.readUserFile(user);
        else {
            new UserFileError();
            frame.dispose();
            new WelcomePane();
            return;
        }
        ColoredLabel welcomeText = new ColoredLabel("Hi, " + user.getFname() + "!");
        welcomeText.setBold();
        welcomeText.setFontSize(24);
        welcomeText.setForeground(new Color(200, 200, 200));
        welcomeText.setBorder(new EmptyBorder(10, 13, 10, 0));
        titleContainer.add(welcomeText);
    }

}

class ListSelectionAction implements ListSelectionListener {
    private int selection;
    private static int previousSelection = -1;
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            selection = MainScreen.contactsList.getSelectedIndex();
            MainScreen.panel.setVisible(false);
            MainScreen.panel.removeAll();

            if (selection != previousSelection && selection < MainScreen.allContacts.length)
                MainScreen.panel.add(new ContactDetailsPane(MainScreen.allContacts[selection]), BorderLayout.CENTER);
            MainScreen.panel.setVisible(true);
        }
     }

}

class NewContactButtonAction extends MouseClickListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        new NewContactDialog();
    }
}