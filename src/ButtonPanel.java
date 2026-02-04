import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends GraphicalApp {
    protected static JPanel buttonPanel(JFrame mainFrame, JTextField sourceField, JTextField usernameField,
                                        JTextField passwordField, JTextField urlField, JTextArea notesArea, int position) {

        JButton addButton = new JButton("Add Password");
        addButton.setPreferredSize(new Dimension(150, 30));
        addButton.addActionListener(e -> {
            String source = sourceField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String url = urlField.getText();
            String notes = notesArea.getText();

            if (source.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Title and password are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                storage.addEntry(source, username, password, url, notes);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            UpdateTable.updateTable(); // in case of adding without updating in addEntry, update is called separately
            JOptionPane.showMessageDialog(mainFrame, "Password added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            sourceField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            urlField.setText("");
            notesArea.setText("");
        });

        JPanel buttonPanel = new GeneratePasswordButton(passwordField);

        buttonPanel.add(addButton);

        return buttonPanel;
    }
}

//        JButton closeButton = new JButton("Close");
//        closeButton.addActionListener(_ -> mainFrame.dispose());
//        buttonPanel.add(closeButton);