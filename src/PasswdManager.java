import javax.swing.*;
import java.util.Random;

public class PasswdManager {

    protected static String MASTER_PASSWORD_HASH = HashGenerator.hash("password");
    protected static String USER_PASSWORD_HASH = "";

    private static int passwordLength = 12;
    private static boolean includeUppercase = true;
    private static boolean includeLowercase = true;
    private static boolean includeDigits = true;
    private static boolean includeSpecialChars = false;

    private static final String UPPER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGIT_CHARS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+=-`~[]\\{}|;':\",./<>?";
    private static final Random random = new Random();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {"Enter master password:", passwordField};
            int option = JOptionPane.showConfirmDialog(null, message, "Authentication", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                String enteredPassword = new String(passwordField.getPassword());
                if (authenticate(enteredPassword)) {
                    ShowTable.ShowPasswordTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Authentication failed. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        });
    }

    protected static boolean authenticate(String enteredPassword) {
        USER_PASSWORD_HASH = HashGenerator.hash(enteredPassword);
//        return enteredHash.equals(MASTER_PASSWORD_HASH);
        return true;
    }

    public static void setPasswordOptions(int length, boolean upper, boolean lower, boolean digits, boolean special) {
        passwordLength = length;
        includeUppercase = upper;
        includeLowercase = lower;
        includeDigits = digits;
        includeSpecialChars = special;
    }

    protected static String generateStrongPasswd() {
        return generatePassword();
    }

    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        String charPool = "";

        if (includeUppercase) charPool += UPPER_CHARS;
        if (includeLowercase) charPool += LOWER_CHARS;
        if (includeDigits) charPool += DIGIT_CHARS;
        if (includeSpecialChars) charPool += SPECIAL_CHARS; // not by default

        if (charPool.isEmpty()) {
            return "";
        }

        for (int i = 0; i < passwordLength; i++) {
            password.append(charPool.charAt(random.nextInt(charPool.length())));
        }
        return password.toString();
    }
}