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

public abstract class ErrorDialog extends JDialog {
    private Container cp = getContentPane();
    private static DarkButton ok = new DarkButton("OK");
    private ImageIcon errorIcon;
    private JLabel icon;
    public static final Color ERROR_TEXT_COLOR = new Color(200, 80, 80);
    private static ColoredLabel error = new ColoredLabel("Error!", ERROR_TEXT_COLOR);
    private ColoredLabel failText = new ColoredLabel();
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    ErrorDialog() {
        URL errorImg = getClass().getResource("files/images/error-icon.png");
        if (errorImg != null) {
            errorIcon = new ImageIcon(errorImg);
            icon = new JLabel(errorIcon);
        }
        else {
            icon = new JLabel("!");
            icon.setForeground(ERROR_TEXT_COLOR);
        }

        init();
        panel.setBackground(MainScreen.DARKER_GRAY);
        cp.add(panel);

        URL imgurl = getClass().getResource("files/images/icon(32x32_light).png");
        if (imgurl != null) {
            ImageIcon icon = new ImageIcon(imgurl);
            setIconImage(icon.getImage());
        }

        setTitle("Error");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(533, 250);
        setVisible(true);
    }

    private void init() {
        error.setFont(new Font("Sans Serif", Font.BOLD, 20));

        // Error icon
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(icon, constraints);

        // Error text
        constraints.gridx = 1;
        constraints.insets = new Insets(30, 0, 0, 20);
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.NONE;
        panel.add(error, constraints);

        // Button
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(ok, constraints);
        ok.addMouseListener(new closeDialog());
    }

    protected void writeFailText(String text) {
        failText.setText(text);
        failText.setFontSize(12);
        constraints.insets = new Insets(-25, 0, 0, 20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        panel.add(failText, constraints);
        pack();
    }

    class closeDialog extends MouseClickListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            dispose();
        }
    }

}

class FileCreationError extends ErrorDialog {
    String errorText = "Could not create file.";// Please make sure you have the proper permissions.";

    FileCreationError() {
        writeFailText(errorText);
    }
}

class UserFileError extends ErrorDialog {
    String errorText = "Userfile missing.";

    UserFileError() { writeFailText(errorText); }
}

class ContactSaveError extends ErrorDialog {
    String errorText = "Unable to save contact";

    ContactSaveError() { writeFailText(errorText); }
}

class ContactReadError extends ErrorDialog {
    String errorText = "Unable to read contact files";

    ContactReadError() { writeFailText(errorText); }
}