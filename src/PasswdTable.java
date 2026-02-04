import javax.swing.*;

public class PasswdTable extends GraphicalApp {
    protected static void showPopupMenu(int x, int y, int row) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem copyUsernameItem = new JMenuItem("Copy User Name");
        copyUsernameItem.addActionListener(_ -> CopyToClipboard.copyToClipboard(row, 1));
        popupMenu.add(copyUsernameItem);

        JMenuItem copyPasswordItem = getJMenuItem(row);
        popupMenu.add(copyPasswordItem);

        JMenuItem urlItem = new JMenuItem("URL(s)");
        urlItem.addActionListener(_ -> CopyToClipboard.copyToClipboard(row, 3));
        popupMenu.add(urlItem);

        JMenuItem otherDataItem = new JMenuItem("Other Data");
        otherDataItem.addActionListener(_ -> CopyToClipboard.copyToClipboard(row, 4));
        popupMenu.add(otherDataItem);

        JMenuItem performAutoTypeItem = new JMenuItem("Search URL");
        performAutoTypeItem.addActionListener(_ -> OpenLink.openLink(row));
        popupMenu.add(performAutoTypeItem);
        popupMenu.addSeparator();

        JMenuItem addEntryItem = new JMenuItem("Add Entry...");
        addEntryItem.addActionListener(_ -> RunManager.runPasswordManager(row));
        popupMenu.add(addEntryItem);

        JMenuItem editItem = new JMenuItem("Edit Entry...");
        editItem.addActionListener(_ -> Edit.editEntry(row));
        popupMenu.add(editItem);

        JMenuItem editQuickItem = new JMenuItem("Edit Entry (Quick)");
        editQuickItem.addActionListener(_ -> {
            try {
                Edit.editEntryQuick(row);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        popupMenu.add(editQuickItem);

        JMenuItem deleteEntryItem = new JMenuItem("Delete Entry");
        deleteEntryItem.addActionListener(_ -> DeleteEntry.deleteEntry(row));
        popupMenu.add(deleteEntryItem);
        popupMenu.addSeparator();

        JMenuItem selectAllItem = new JMenuItem("Select All");
        selectAllItem.addActionListener(_ -> Table.selectAll());
        popupMenu.add(selectAllItem);

        popupMenu.show(Table, x, y);
    }

    private static JMenuItem getJMenuItem(int row) {
        JMenuItem copyPasswordItem = new JMenuItem("Copy Password");
        copyPasswordItem.addActionListener(GetCopyActionListener.getCopyActionListener(row));
        return copyPasswordItem;
    }
}
