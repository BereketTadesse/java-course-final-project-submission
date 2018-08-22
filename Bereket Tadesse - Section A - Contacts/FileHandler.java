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

import java.io.*;
import java.nio.charset.Charset;

import static java.lang.Integer.valueOf;

public class FileHandler {
    private static String filesDir = "files";
    private static String mainFilesDir = "files/main";
    private static String fnameFile = "files/main/userdata/fname.dat"; // For some reason, the file stream starts from a
    private static String lnameFile = "files/main/userdata/lname.dat"; // different directory to ImageIcon
    private static String contactDir = "files/main/contacts";
    private static String maxFile = contactDir + "/max.dat";
    private static String lifetimeFile = contactDir + "/lifetime.dat";

    public static boolean userFileExists() {
        directoriesExist();
        try {
            File file = new File(fnameFile);
            return file.exists();
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static void directoriesExist() {
        try {
            File rootFolder = new File(filesDir);
            File mainFolder = new File(mainFilesDir);
            File userFolder = new File(mainFilesDir + "/userdata");
            File contactFolder = new File(mainFilesDir + "/contacts");

            if (!rootFolder.exists())
                rootFolder.mkdir();
            if (!mainFolder.exists())
                mainFolder.mkdir();
            if (!userFolder.exists())
                userFolder.mkdir();
            if (!contactFolder.exists())
                contactFolder.mkdir();
        }
        catch (Exception e) {
            new UserFileError();
        }
    }

    public static boolean readUserFile(User user) {
            try (InputStream fnameInputStream = new FileInputStream(fnameFile);
                 InputStream lnameInputStream = new FileInputStream(lnameFile)
            ) {
                byte[] bytes;
                bytes = fnameInputStream.readAllBytes();
                user.setFname(new String(bytes, Charset.defaultCharset()));
                bytes = lnameInputStream.readAllBytes();
                user.setLname(new String(bytes, Charset.defaultCharset()));
            }
            catch (IOException E) {
                return false;
            }
            return true;
    }


    public static boolean createUserFile(User user) {

        try (OutputStream fnameStream = new FileOutputStream(fnameFile);
             OutputStream lnameStream = new FileOutputStream(lnameFile)
        ) {
            byte[] bytes;
            bytes = user.getFname().getBytes();
            fnameStream.write(bytes);
            if (! user.getLname().isEmpty()) {
                bytes = user.getLname().getBytes();
                lnameStream.write(bytes);
            }
            return true;
        }
        catch (IOException exception) {
            // exception.printStackTrace();
            new FileCreationError();
            return false;
        }
    }

    public static boolean saveContactFile(Contact contact) {
        try (OutputStream contactStream = new FileOutputStream(generateContactFileName(contact))) {
            File contactFile = new File(generateContactFileName(contact));
            ObjectOutputStream objSave = new ObjectOutputStream(contactStream);
            if (contactFile.exists()) {
                objSave.writeObject(contact);
                objSave.flush();
                objSave.close();
                saveMaxFile(Contact.getAvailableContacts());
                saveLifetimeFile();
                return true;
            }
            else
                return false;
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static Contact readContactFile(int id) {
        try (InputStream contactInStream = new FileInputStream(generateContactFileName(id))) {
            File cfile = new File(generateContactFileName(id));
            if (cfile.exists()) {
                ObjectInputStream objStream = new ObjectInputStream(contactInStream);
                // Contact contact;
                return (Contact) objStream.readObject();
            }
            else
                return null;
        }
        catch (Exception exception) {
            // exception.printStackTrace();
            // new ContactReadError();
            return null;
        }
    }

    public static void deleteContactFile(int id) {
        try  {
            File cfile = new File(generateContactFileName(id));
            if (cfile.exists()) {
                if (cfile.delete())
                    saveMaxFile(Contact.availableContacts);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            new ContactReadError();
        }
    }

    private static void saveMaxFile(int availableContacts) {
        Integer availableContactsWrapper = valueOf(availableContacts);

        try (OutputStream maxFileStream = new FileOutputStream(maxFile)) {
            File maxFileCheck = new File(maxFile);
            ObjectOutputStream maxFileObjStream = new ObjectOutputStream(maxFileStream);
            if (maxFileCheck.exists()) {
                maxFileObjStream.writeObject(availableContactsWrapper);
                maxFileObjStream.flush();
                maxFileObjStream.close();
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    static int readMaxFile() {
        int availableContacts = Contact.getAvailableContacts();
        Integer availableContactsWrapper;
        try (InputStream maxFileInStream = new FileInputStream(maxFile)) {
            ObjectInputStream maxFileObjInStream = new ObjectInputStream(maxFileInStream);
            availableContactsWrapper = (Integer) maxFileObjInStream.readObject();
            availableContacts = availableContactsWrapper;
            // System.out.println(availableContacts);
        }
        catch (IOException exception) {
            exception.printStackTrace();
            new ContactReadError();
        }
        finally {
            return availableContacts;
        }
    }

    static void saveLifetimeFile() {
        Integer lifetimeContactsWrapper = valueOf(Contact.getLifeTimeContacts());

        try (OutputStream lifetimeFileStream = new FileOutputStream(lifetimeFile)) {
            File lifetimeFileCheck = new File(lifetimeFile);
            ObjectOutputStream maxFileObjStream = new ObjectOutputStream(lifetimeFileStream);
            if (lifetimeFileCheck.exists()) {
                maxFileObjStream.writeObject(lifetimeContactsWrapper);
                maxFileObjStream.flush();
                maxFileObjStream.close();
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    static int readLifetimeFile() {
        int lifetimeContacts = Contact.getLifeTimeContacts();
        Integer lifetimeContactsWrapper;
        try (InputStream lifetimeFileInStream = new FileInputStream(lifetimeFile)) {
            ObjectInputStream lifetimeFileObjInStream = new ObjectInputStream(lifetimeFileInStream);
            lifetimeContactsWrapper = (Integer) lifetimeFileObjInStream.readObject();
            lifetimeContacts = lifetimeContactsWrapper;
            // System.out.println(availableContacts);
        }
        catch (IOException exception) {
            exception.printStackTrace();
            new ContactReadError();
        }
        finally {
            return lifetimeContacts;
        }
    }

    static boolean maxFileExists() {
        try {
            File file = new File(maxFile);
            return file.exists();
        }
        catch (Exception exception) {
            return false;
        }
    }

    static boolean lifetimeFileExists() {
        try {
            File file = new File(lifetimeFile);
            return file.exists();
        }
        catch (Exception e) {
            return false;
        }
    }

    static Contact[] readAllContacts() {
        int numberOfContacts, lifetimeContacts;
        int index = 0;
        numberOfContacts = FileHandler.readMaxFile();
        lifetimeContacts = FileHandler.readLifetimeFile();
        // System.out.println(numberOfContacts);
        Contact[] allContacts = new Contact[numberOfContacts];
        for (int i = 0; i < numberOfContacts && index < lifetimeContacts; i++) {
            allContacts[i] = readContactFile(Contact.getInitialContactID() + index);
            if (allContacts[i] == null)
                i--;
            index++;
        }
        Contact.lifeTimeContacts = readLifetimeFile();
        return allContacts;
    }

    public static String generateContactFileName(Contact contact) {
        return generateContactFileName(contact.getId());
        // return contactDir + "/" + Integer.toString(contact.getId()) + ".cfile";
    }

    public static String generateContactFileName(int id) {
        return contactDir + "/" + Integer.toString(id) + ".cfile";
    }
}
