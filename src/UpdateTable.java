import java.util.List;

public class UpdateTable extends ShowTable{
    protected static void updateTable() {
        Model.setRowCount(0);
        List<Entry> entries = storage.getAllEntries();
        for (Entry entry : entries) {
            Model.addRow(new Object[]{entry.getSource(), entry.getUsername(), entry.getPassword(), entry.getUrl(), entry.getNotes()});
        }
    }
}
