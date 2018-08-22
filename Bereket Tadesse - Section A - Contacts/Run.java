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

public class Run {
    private static User user = new User();

    public static void main(String [] args) {
        if (!FileHandler.userFileExists())
            new WelcomePane();
        else {
            FileHandler.readUserFile(user);
            new MainScreen();
        }
    }
}
