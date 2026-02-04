import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class AdvancedFindActionListener extends ShowTable implements ActionListener {

    private final JTable table;
    private final JFrame frame;
    private final DefaultTableModel model;

    public AdvancedFindActionListener(JTable table, JFrame frame, DefaultTableModel model) {
        this.table = table;
        this.frame = frame;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        JTextField searchField = new JTextField();
        JComboBox<String> columnComboBox = new JComboBox<>(new String[]{"Title", "User Name", "URL", "Notes", "All"});
        JCheckBox isCaseSensitive = new JCheckBox("Case Sensitive");

        panel.add(new JLabel("Search Text:"));
        panel.add(searchField);
        panel.add(new JLabel("Search In:"));
        panel.add(columnComboBox);
        panel.add(isCaseSensitive);
        panel.add(new JLabel());

        int result = JOptionPane.showConfirmDialog(frame, panel, "Advanced Find", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String searchText = searchField.getText();
            String selectedColumn = (String) columnComboBox.getSelectedItem();
            boolean caseSensitive = isCaseSensitive.isSelected();

            if (!searchText.isEmpty()) {
                @SuppressWarnings("unchecked") // it was yellow
                TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
                if (sorter == null) {
                    sorter = new TableRowSorter<>(model);
                    table.setRowSorter(sorter);
                }

                RowFilter<DefaultTableModel, Object> rf = null;
                try {
                    assert selectedColumn != null;
                    if (selectedColumn.equals("All")) {
                        rf = RowFilter.regexFilter(String.valueOf(caseSensitive ? Pattern.compile(Pattern.quote(searchText)) : Pattern.compile(Pattern.quote(searchText), Pattern.CASE_INSENSITIVE)), 0, 1, 2, 3);
                    } else {
                        int columnIndex = -1;
                        columnIndex = switch (selectedColumn) {
                            case "Title" -> 0;
                            case "User Name" -> 1;
                            case "URL" -> 2;
                            case "Notes" -> 3;
                            default -> columnIndex;
                        };
                        if (columnIndex != -1) {
                            rf = RowFilter.regexFilter(String.valueOf(caseSensitive ? Pattern.compile(Pattern.quote(searchText)) : Pattern.compile(Pattern.quote(searchText), Pattern.CASE_INSENSITIVE)), columnIndex);
                        }
                    }
                    sorter.setRowFilter(rf);
                } catch (java.util.regex.PatternSyntaxException pse) {
                    JOptionPane.showMessageDialog(frame, "Invalid search pattern.", "Advanced Find", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                @SuppressWarnings("unchecked")
                TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
                if (sorter != null) {
                    sorter.setRowFilter(null);
                }
            }
        }
    }
}