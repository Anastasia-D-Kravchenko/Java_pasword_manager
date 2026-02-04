import javax.swing.*;
import java.awt.*;

public class PasswdStrengh extends ShowTable {
    protected static void passwdStrength() {
        JPanel mainPanel = new JPanel(new GridLayout(0, 1));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel enterLabel = new JLabel("Enter Password:");
        JTextField passwordField = new JTextField(20);
        JButton checkButton = new JButton("X");

        inputPanel.add(enterLabel);
        inputPanel.add(passwordField);
        inputPanel.add(checkButton);

        JLabel strengthLabel = new JLabel("Strength: ", JLabel.LEFT);

        checkButton.addActionListener(e -> {
            String password = passwordField.getText();
            String strength = analyzePasswordStrength(password);
            strengthLabel.setText("Strength: " + strength);
        });

        passwordField.addActionListener(e -> {
            String password = passwordField.getText();
            String strength = analyzePasswordStrength(password);
            strengthLabel.setText("Strength: " + strength);
        });

        mainPanel.add(inputPanel);
        mainPanel.add(strengthLabel);

        JOptionPane.showMessageDialog(Frame, mainPanel, "Password Strength Meter", JOptionPane.INFORMATION_MESSAGE);
    }

    protected static String analyzePasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return "VERY Weak";
        }
        int length = password.length();
        boolean hasUpper = !password.equals(password.toLowerCase()); // original == lower case => same => doesn't have upper
        boolean hasLower = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9\\s].*");

        int score = 0;
        if (length >= 8) score++;
        if (hasUpper) score++;
        if (hasLower) score++;
        if (hasDigit) score++;
        if (hasSpecial) score++;

        if (score <= 1) return "Very Weak";
        if (score == 2) return "Weak";
        if (score == 3) return "Moderate";
        if (score == 4) return "Strong";
        return "Very Strong";
    }

    protected static void generatorOptions() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        JTextField lengthField = new JTextField("12");
        JCheckBox upperCaseCheckBox = new JCheckBox("Include Uppercase", true);
        JCheckBox lowerCaseCheckBox = new JCheckBox("Include Lowercase", true);
        JCheckBox digitsCheckBox = new JCheckBox("Include Digits", true);
        JCheckBox specialCharsCheckBox = new JCheckBox("Include Special Characters", false);

        panel.add(new JLabel("Length:"));
        panel.add(lengthField);
        panel.add(upperCaseCheckBox);
        panel.add(lowerCaseCheckBox);
        panel.add(digitsCheckBox);
        panel.add(specialCharsCheckBox);

        int result = JOptionPane.showConfirmDialog(Frame, panel, "Password Generator Options", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int length = Integer.parseInt(lengthField.getText());
                boolean includeUpper = upperCaseCheckBox.isSelected();
                boolean includeLower = lowerCaseCheckBox.isSelected();
                boolean includeDigits = digitsCheckBox.isSelected();
                boolean includeSpecial = specialCharsCheckBox.isSelected();
                PasswdManager.setPasswordOptions(length, includeUpper, includeLower, includeDigits, includeSpecial);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Frame, "Invalid length entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
