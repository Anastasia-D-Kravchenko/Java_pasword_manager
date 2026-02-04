import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetMenu extends ShowTable {
    protected static JMenu getMenu() {
        JMenu entryMenu = new JMenu("Entry");
        JMenuItem addEntryMenuItem = new JMenuItem("Add New Entry...");
        addEntryMenuItem.addActionListener(_ -> RunManager.runPasswordManager(-1));
        entryMenu.add(addEntryMenuItem);

        JMenuItem editEntryMenuItem = new JMenuItem("Edit Entry...");
        editEntryMenuItem.addActionListener(_ -> {
            if (Table.getSelectedRow() != -1) { // Returns the index of the first selected row
                Edit.editEntry(Table.getSelectedRow());
            } else { // -1 if no row is selected
                JOptionPane.showMessageDialog(Frame, "Please select an entry to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        entryMenu.add(editEntryMenuItem);

        JMenuItem deleteEntryMenuItem = new JMenuItem("Delete Entry");
        deleteEntryMenuItem.addActionListener(_ -> {
            if (Table.getSelectedRow() != -1) {
                DeleteEntry.deleteEntry(Table.getSelectedRow());
            } else {
                JOptionPane.showMessageDialog(Frame, "Please select an entry to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        entryMenu.add(deleteEntryMenuItem);

        JMenuItem copyUsernameMenuItem = new JMenuItem("Copy Username");
        copyUsernameMenuItem.addActionListener(_ -> {
            if (Table.getSelectedRow() != -1) {
                CopyToClipboard.copyToClipboard(Table.getSelectedRow(), 0);
            } else {
                JOptionPane.showMessageDialog(Frame, "Please select an entry to copy the username.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        entryMenu.add(copyUsernameMenuItem);

        JMenuItem copyPasswordMenuItem = new JMenuItem("Copy Password");
        copyPasswordMenuItem.addActionListener(_ -> {
            if (Table.getSelectedRow() != -1) {
                ActionListener listener = GetCopyActionListener.getCopyActionListener(Table.getSelectedRow());
                listener.actionPerformed(new ActionEvent(copyPasswordMenuItem, ActionEvent.ACTION_PERFORMED, "copyPassword"));
            } else {
                JOptionPane.showMessageDialog(Frame, "Please select an entry to copy the password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenuItem copyUrlMenuItem = new JMenuItem("Copy URL");
        copyUrlMenuItem.addActionListener(_ -> {
            if (Table.getSelectedRow() != -1) {
                CopyToClipboard.copyToClipboard(Table.getSelectedRow(), 2);
            } else {
                JOptionPane.showMessageDialog(Frame, "Please select an entry to copy the URL.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        entryMenu.add(copyUrlMenuItem);
        return entryMenu;
    }
}
