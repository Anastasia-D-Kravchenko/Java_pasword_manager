import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SortBy extends PasswdTable {
    protected static void sortBy() {
        String[] columns = {"Title", "User Name", "URL", "Notes"};
        JComboBox<String> columnComboBox = new JComboBox<>(columns);
        JCheckBox ascendingCheckBox = new JCheckBox("Ascending", true);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select Column to Sort By:"));
        panel.add(columnComboBox);
        panel.add(ascendingCheckBox);

        int result = JOptionPane.showConfirmDialog(Frame, panel, "Sort By", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String selectedColumn = (String) columnComboBox.getSelectedItem();
            boolean ascending = ascendingCheckBox.isSelected();
            sortTable(selectedColumn, ascending);
        }
    }
    protected static void sortTable(String column, boolean ascending) {
        int columnIndex = -1;
        for (int i = 0; i < Model.getColumnCount(); i++) {
            if (Model.getColumnName(i).equals(column)) {
                columnIndex = i;
                break;
            }
        }

        if (columnIndex != -1) {
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(Model);
            Table.setRowSorter(sorter);
            List<RowSorter.SortKey> sortKeys = new ArrayList<>();
            sortKeys.add(new RowSorter.SortKey(columnIndex, ascending ? SortOrder.ASCENDING : SortOrder.DESCENDING));
            sorter.setSortKeys(sortKeys);
            sorter.sort();
        }
    }
}
