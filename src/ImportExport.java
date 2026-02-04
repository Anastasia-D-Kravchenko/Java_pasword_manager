import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ImportExport extends ShowTable {
    private static boolean isLikelyEncoded(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        try {
            HashGenerator.decode(password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    protected static void importData() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Import Password Data");
        chooser.setFileFilter(new FileNameExtensionFilter("Text and CSV Files", "txt", "csv"));
        int userSlt = chooser.showOpenDialog(Frame);

        if (userSlt == JFileChooser.APPROVE_OPTION) {
            File toImport = chooser.getSelectedFile();
            try (Scanner scanner = new Scanner(toImport)) {
                int importedCount = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");

                    if (parts.length == 5) {
                        String[] rowData = new String[5];
                        for (int i = 0; i < parts.length; i++) {
                            String value = parts[i].trim();
                            if (value.startsWith("\"") && value.endsWith("\"")) {
                                rowData[i] = value.trim().substring(1, value.length() - 1);
                            } else {
                                rowData[i] = value;
                            }
                        }

                        if (isLikelyEncoded(rowData[2])) {
                            try {
                                rowData[2] = HashGenerator.decode(rowData[2]);
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(Frame, "Error encoding password during import for entry: " + rowData[0] + "\n" + e.getMessage(), "Import Error", JOptionPane.WARNING_MESSAGE);
                                continue;
                            }
                        }
                        Model.addRow(rowData);
                        storage.addEntry(rowData[0], rowData[1], rowData[2], rowData[3], rowData[4]);
                        importedCount++;
                    } else {
                        System.err.println("Skipping malformed line during import: " + line);
                    }
                }
                UpdateTable.updateTable();
                JOptionPane.showMessageDialog(Frame, importedCount + " entries imported successfully.", "Import", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(Frame, "Error importing data: " + ex.getMessage(), "Import Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Frame, "An unexpected error occurred during import: " + ex.getMessage(), "Import Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }

    protected static void exportData() {
        String[] options = {"Encoded Passwords", "Plain Text Passwords"};
        int choice = JOptionPane.showOptionDialog(Frame, "How would you like to export passwords?", "Export Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == -1) return;
        boolean exportAsPlainText = (choice == 1);

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Export Password Data");
        chooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        int userSlt = chooser.showSaveDialog(Frame);

        if (userSlt == JFileChooser.APPROVE_OPTION) {
            File fileToExport = chooser.getSelectedFile();
            if (!fileToExport.getName().toLowerCase().endsWith(".csv")) {
                fileToExport = new File(fileToExport.getAbsolutePath() + ".csv");
            }

            try (FileWriter writer = new FileWriter(fileToExport)) {
                for (int i = 0; i < Model.getRowCount(); i++) {
                    for (int j = 0; j < Model.getColumnCount(); j++) {
                        String value;
                        if (j == 2) {
                            Entry entry = storage.getEntry(i);
                            if (entry != null) {
                                if (exportAsPlainText) {
                                    value = HashGenerator.decode(entry.getPassword());
                                } else {
                                    value = entry.getPassword();
                                }
                            } else {
                                value = "";
                            }
                        } else {
                            Object obj = Model.getValueAt(i, j);
                            value = (obj != null) ? obj.toString() : "";
                        }
                        writer.write("\"" + value.replace("\"", "\"\"") + "\"");
                        if (j < Model.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }
                    writer.write(System.lineSeparator());
                }
                JOptionPane.showMessageDialog(Frame, "Data exported to " + fileToExport.getName(), "Export", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(Frame, "Error exporting data: " + ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Frame, "An unexpected error occurred during export: " + ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
            }
        }
    }
}