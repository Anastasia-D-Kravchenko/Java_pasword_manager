import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class CopyToClipboard extends ShowTable{
    protected static void copyToClipboard(int row, int col) {
        if (row >= 0 && row < Model.getRowCount() && col >= 0 && col < Model.getColumnCount()) {
            String value = (String) Model.getValueAt(row, col);
            StringSelection stringSelection = new StringSelection(value);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//            ClipboardOwner clipboardOwner = (ClipboardOwner) clipboard; // to be notified if someone tokes ownership
            clipboard.setContents(stringSelection, null);
            JOptionPane.showMessageDialog(Frame, Model.getColumnName(col) + " copied to clipboard.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
