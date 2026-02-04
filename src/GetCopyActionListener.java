import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;

public class GetCopyActionListener extends PasswdTable {
    protected static ActionListener getCopyActionListener(int row) {
        return _ -> {
            Entry entry = storage.getEntry(row);
            if (entry != null) {
                String decodedPassword = HashGenerator.decode(entry.getPassword());
                StringSelection stringSelection = new StringSelection(decodedPassword);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(Table, "Password copied to clipboard.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(Table, "Error retrieving password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}
