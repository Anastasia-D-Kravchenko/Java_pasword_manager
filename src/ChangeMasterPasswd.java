import javax.swing.*;
import java.awt.*;

public class ChangeMasterPasswd extends PasswdTable {
    protected static void changeMasterPasswd() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JPasswordField oldPasswd = new JPasswordField();
        JPasswordField newPasswd = new JPasswordField();
        JPasswordField confirmNewPasswd = new JPasswordField();

        panel.add(new JLabel("Enter old master password:"));
        panel.add(oldPasswd);
        panel.add(new JLabel("Enter new master password:"));
        panel.add(newPasswd);
        panel.add(new JLabel("Confirm new master password:"));
        panel.add(confirmNewPasswd);

        int result = JOptionPane.showConfirmDialog(Frame, panel, "Change Master Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String oldPassword = new String(oldPasswd.getPassword());
            String newPassword = new String(newPasswd.getPassword());
            String confirmPassword = new String(confirmNewPasswd.getPassword());

            changeMasterPassword(oldPassword, newPassword, confirmPassword);
        }
    }
    private static void changeMasterPassword(String oldPassword, String newPassword, String confirmPassword) {
        if (!oldPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
            if (HashGenerator.hash(oldPassword).equals(PasswdManager.MASTER_PASSWORD_HASH)) {
                if (newPassword.equals(confirmPassword)) {
                    try {
                        String newMasterPasswordHash = HashGenerator.hash(newPassword);
                        if (PasswdManager.authenticate(oldPassword)) {
                            PasswdManager.MASTER_PASSWORD_HASH = newMasterPasswordHash;

                            JOptionPane.showMessageDialog(Frame, "Master password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(Frame, "Old password authentication failed.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Frame, "Error hashing new password: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Frame, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(Frame, "Incorrect old master password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(Frame, "Please fill in all password fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}