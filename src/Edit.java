import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Edit extends ShowTable {

    protected static void editEntryQuick(int row) {
        if (row >= 0 && row < Model.getRowCount()) {
            String currentEncodedPasswd = (String) Model.getValueAt(row, 2);
            String currentDecodedPasswd = HashGenerator.decode(currentEncodedPasswd);
            String title = (String) Model.getValueAt(row, 0);

            JPanel panel = new JPanel(new FlowLayout());
            JTextField passwdField = new JTextField(15);
            passwdField.setText(currentDecodedPasswd);
            JButton generate = new JButton("Generate");
            generate.addActionListener(e -> passwdField.setText(PasswdManager.generateStrongPasswd()));
            panel.add(new JLabel("New Password:"));

            panel.add(passwdField);
            panel.add(generate);

            int result = JOptionPane.showConfirmDialog(Table, panel, "Quick Edit Password for:\n" + title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String newPassword = passwdField.getText();
                if (newPassword != null) {
                    String hashedNewPassword = HashGenerator.encode(newPassword);
                    Model.setValueAt(hashedNewPassword, row, 2);

                    List<Entry> entries = storage.getAllEntries();
                    if (row < entries.size()) {
                        Entry entry = entries.get(row);
                        entries.set(row, new Entry(entry.getSource(), entry.getUsername(), hashedNewPassword, entry.getUrl(), entry.getNotes()));
                        storage.saveAllEntries();
                    }else{
                        JOptionPane.showMessageDialog(Table, "No entry found");
                    }
                }
            }
        }
    }

    protected static void editEntry(int row) {
        if (row >= 0 && row < Model.getRowCount()) {
            String title = (String) Model.getValueAt(row, 0);
            String username = (String) Model.getValueAt(row, 1);
            String decodedPassword = HashGenerator.decode((String) Model.getValueAt(row, 2));
            String url = (String) Model.getValueAt(row, 3);
            String notes = (String) Model.getValueAt(row, 4);

            JFrame editFrame = new JFrame("Edit Password Entry");
            editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editFrame.setSize(400, 500);
            editFrame.setLocationRelativeTo(Frame);

            JPanel northPanel = new JPanel();
            northPanel.setLayout(new GridLayout(2, 1));



            JPanel inputPanel = new JPanel(new GridLayout(5, 2));
            int width = 50;
            int hight = 25;
            inputPanel.setBorder(BorderFactory.createEmptyBorder(hight, width, 10, width));

            JLabel titleLabel = new JLabel("Title:");
            JTextField titleField = new JTextField(title, 10);
            JLabel usernameLabel = new JLabel("User Name:");
            JTextField usernameField = new JTextField(username, 10);
            JLabel passwordLabel = new JLabel("Password:");
            JTextField passwordField = new JTextField(decodedPassword, 10);
            JLabel urlLabel = new JLabel("URL:");
            JTextField urlField = new JTextField(url, 10);

            jPanelConstractor.jPanel(inputPanel, titleLabel, titleField, usernameLabel, usernameField, passwordLabel, passwordField, urlLabel, urlField);



            JPanel notesPanel = new JPanel(new BorderLayout());
            notesPanel.setBorder(BorderFactory.createEmptyBorder(hight, width, 0, width));

            JLabel notesLabel = new JLabel("Notes:");
            notesLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

            JTextArea notesArea = new JTextArea(notes,5, 15);
            notesArea.setLineWrap(true);
            notesArea.setWrapStyleWord(true);

            JScrollPane notesScrollPane = new JScrollPane(notesArea);
            notesPanel.add(notesLabel, BorderLayout.NORTH);
            notesPanel.add(notesScrollPane, BorderLayout.CENTER);



            JPanel buttonPanel = new GeneratePasswordButton(passwordField);



            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(e -> {
                String newTitle = titleField.getText();
                String newUsername = usernameField.getText();
                String newPassword = passwordField.getText();
                String newUrl = urlField.getText();
                String newNotes = notesArea.getText();

                String encodedNewPassword = HashGenerator.encode(newPassword);

                Model.setValueAt(newTitle, row, 0);
                Model.setValueAt(newUsername, row, 1);
                Model.setValueAt(encodedNewPassword, row, 2);
                Model.setValueAt(newUrl, row, 3);
                Model.setValueAt(newNotes, row, 4);

                List<Entry> entries = storage.getAllEntries();
                if (row < entries.size()) {
                    entries.set(row, new Entry(newTitle, newUsername, encodedNewPassword, newUrl, newNotes));
                    storage.saveAllEntries();
                }

                editFrame.dispose();
            });


            buttonPanel.add(saveButton);

            northPanel.add(inputPanel);
            northPanel.add(notesPanel);

            editFrame.add(northPanel, BorderLayout.NORTH);
            editFrame.add(buttonPanel, BorderLayout.SOUTH);
            editFrame.setVisible(true);
        }
    }
}
