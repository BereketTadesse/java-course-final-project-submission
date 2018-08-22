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

class WelcomePane extends JFrame {
    private JPanel panel = new JPanel(new GridBagLayout());
    private ColoredLabel welcome = new ColoredLabel("Welcome!");
    private ColoredLabel tellus = new ColoredLabel("Tell us a bit about yourself");
    private ColoredLabel fname = new ColoredLabel("First Name:");
    private ColoredLabel lname = new ColoredLabel("Last Name:");
    private ColoredLabel fnameError = new ColoredLabel("First name can not be empty", ErrorDialog.ERROR_TEXT_COLOR);
    private DarkButton proceed = new DarkButton("Proceed", DarkButton.GREEN);
    private DarkButton exit = new DarkButton("Exit", DarkButton.RED);
    private GridBagConstraints constraints = new GridBagConstraints();
    private DarkTextField fnameField = new DarkTextField();
    private DarkTextField lnameField = new DarkTextField();

    WelcomePane() {
        Container contentPane = getContentPane();
        initPanel();
        contentPane.add(panel);
        setTitle("Welcome");
        setSize(640, 360);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        URL imgurl = getClass().getResource("files/images/icon(32x32_light).png");
        if (imgurl != null) {
            ImageIcon icon = new ImageIcon(imgurl);
            setIconImage(icon.getImage());
        }
        setLocation(363, 204);
        setVisible(true);
    }

    private void initPanel() {
        panel.setBackground(MainScreen.DARKER_GRAY);
        // panel.setBackground(MainScreen.DARKER_GRAY);
        welcome.setFont(new Font("Sans Serif", Font.BOLD, 70));
        tellus.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        fname.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        lname.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        proceed.setFont(new Font("Sans Serif", Font.BOLD, 13));
        exit.setFont(new Font("Sans Serif", Font.PLAIN, 12));

        adjustTitleConstraints();
        adjustSubtitleConstraints();
        adjustLabelConstraints();
        adjustTextFieldConstraints();
        adjustButtonConstraints();
        addEventHandlers();
        addInvisibleError();
    }

    private void adjustTitleConstraints() {
        constraints.insets = new Insets(10, 10, 2, 10);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        panel.add(welcome, constraints);
    }

    private void adjustSubtitleConstraints() {
        constraints.insets = new Insets(0, 10, 30, 10);
        constraints.gridy = 3;
        panel.add(tellus, constraints);
    }

    private void adjustLabelConstraints() {
        constraints.insets = new Insets(0, 150, 10, 10);
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(fname, constraints);

        constraints.gridy = 6;
        panel.add(lname, constraints);
    }

    private void adjustTextFieldConstraints() {
        constraints.insets = new Insets(0, 10, 10, 150);
        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        panel.add(fnameField, constraints);

        constraints.gridy = 6;
        panel.add(lnameField, constraints);
    }

    private void adjustButtonConstraints(){
        constraints.insets = new Insets(20, 10, 10, 10);
        // constraints.insets.top = 18;
        constraints.gridx = 2;
        constraints.gridy = 8;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        panel.add(proceed, constraints);

        constraints.insets.top = 5;
        constraints.gridy = 9;
        panel.add(exit, constraints);
    }

    private void addInvisibleError() {
        constraints.gridy = 7;
        constraints.gridwidth = 3;
        constraints.insets = new Insets(10, 10, 0, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        fnameError.setFont(new Font("Sans Serif", Font.BOLD, 12));
        panel.add(fnameError, constraints);
        fnameError.setVisible(false);
    }

    private void displayError() {
        fnameError.setVisible(true);
    }

    private void addEventHandlers() {
        proceed.addMouseListener(new ProceedButtonAction());
        exit.addMouseListener(new ExitButtonAction());
    }


    class ProceedButtonAction extends MouseClickListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            String fnameText = fnameField.getText();
            String lnameText = lnameField.getText();

            if (fnameText.isEmpty()) {
                displayError();
            }
            else {
                fnameError.setVisible(false);
                FileHandler.createUserFile(new User(fnameText, lnameText));
                setVisible(false);
                dispose();
                new MainScreen();
            }
        }
    }

    class ExitButtonAction extends MouseClickListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.exit(0);
        }
    }
}