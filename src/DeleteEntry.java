import javax.swing.*;
import java.util.List;

public class DeleteEntry extends ShowTable{
    protected static void deleteEntry(int row) {
        if (row >= 0 && row < Model.getRowCount()) {
            int confirm = JOptionPane.showConfirmDialog(Frame, "Are you sure you want to DELETE this entry?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                List<Entry> entries = storage.getAllEntries();
                if (row < entries.size()) {
                    entries.remove(row);
                    storage.saveAllEntries();
                    UpdateTable.updateTable();
                }
            }
        }
    }
}
