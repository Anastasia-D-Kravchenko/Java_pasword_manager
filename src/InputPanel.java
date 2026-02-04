import javax.swing.*;
import java.awt.*;

public class InputPanel {
    protected static JPanel inputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        int width = 50;
        int hight = 25;
        inputPanel.setBorder(BorderFactory.createEmptyBorder(hight, width, 10, width));

        JLabel sourceLabel = new JLabel("Title:");
        JTextField sourceField = new JTextField(10);
        JLabel usernameLabel = new JLabel("User Name:");
        JTextField usernameField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField(10);
        JLabel urlLabel = new JLabel("URL:");
        JTextField urlField = new JTextField(10);

        jPanelConstractor.jPanel(inputPanel, sourceLabel, sourceField, usernameLabel, usernameField, passwordLabel, passwordField, urlLabel, urlField);

        return inputPanel;
    }
}
