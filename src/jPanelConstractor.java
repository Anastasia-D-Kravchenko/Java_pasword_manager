import javax.swing.*;

public class jPanelConstractor {
    static void jPanel(JPanel inputPanel, JLabel titleLabel, JTextField titleField, JLabel usernameLabel, JTextField usernameField, JLabel passwordLabel, JTextField passwordField, JLabel urlLabel, JTextField urlField) {
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(urlLabel);
        inputPanel.add(urlField);
    }
}
