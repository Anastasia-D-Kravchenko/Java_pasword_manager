import javax.swing.*;
import java.awt.*;

public class GeneratePasswordButton extends JPanel {
    public GeneratePasswordButton(JTextField passwordField) {
        super(new FlowLayout(FlowLayout.CENTER));
        JButton generatePasswordButton = new JButton("Generate Password");
        generatePasswordButton.setPreferredSize(new Dimension(180, 30));
        generatePasswordButton.addActionListener(_ -> {
            String generatedPassword = PasswdManager.generateStrongPasswd();
            passwordField.setText(generatedPassword);
        });
        this.add(generatePasswordButton);
    }
}