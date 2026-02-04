import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save extends PasswdTable {
    protected static void saveFile() {
        try {
            File file = new File(Storage.STORAGE);
            if (file.exists()) { // clears the file's contents
                new FileWriter(file, false).close();
            }

            for (int i = 0; i < Model.getRowCount(); i++) {
                String title = (String) Model.getValueAt(i, 0);
                String username = (String) Model.getValueAt(i, 1);
                String password = (String) Model.getValueAt(i, 2);
                String url = (String) Model.getValueAt(i, 3);
                String notes = (String) Model.getValueAt(i, 4);

                Entry entry = new Entry(title, username, password, url, notes);
                storage.saveEntry(entry);
            }
            JOptionPane.showMessageDialog(Frame, "File saved successfully.", "File Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Frame, "Error saving file: " + ex.getMessage(), "File Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    protected static void saveFileAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Password File As");
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int userSlt = chooser.showSaveDialog(Frame);

        if (userSlt == JFileChooser.APPROVE_OPTION) {
            File toSave = chooser.getSelectedFile(); // /Users/anastasiiakravchenko/PJATK/experiment/passwords.txt
            if (!toSave.getName().toLowerCase().endsWith(".txt")) toSave = new File(toSave.getAbsolutePath() + ".txt");

            try (FileWriter writer = new FileWriter(toSave)) {
                for (int i = 0; i < Model.getRowCount(); i++) {
                    for (int j = 0; j < Model.getColumnCount(); j++) {
                        writer.write(Model.getValueAt(i, j).toString());
                        if (j < Model.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }
                    writer.write(System.lineSeparator());
                }
                JOptionPane.showMessageDialog(Frame, "File saved as " + toSave.getName(), "File Save As", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(Frame, "Error saving file: " + ex.getMessage(), "File Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
