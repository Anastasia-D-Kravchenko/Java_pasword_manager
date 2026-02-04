import javax.swing.*;

public class GetViewMenu extends ShowTable {
    protected static JMenu getViewMenu() {
        JMenu viewMenu = new JMenu("View");

        JMenuItem sortByMenuItem = new JMenuItem("Sort By...");
        sortByMenuItem.addActionListener(_ -> SortBy.sortBy());
        viewMenu.add(sortByMenuItem);

        JCheckBoxMenuItem togglePasswordVisibilityMenuItem = new JCheckBoxMenuItem("Toggle Password Visibility");
        togglePasswordVisibilityMenuItem.addActionListener(_ -> {
            setIsPWVisible(togglePasswordVisibilityMenuItem.isSelected());
            Table.repaint();
        });
        viewMenu.add(togglePasswordVisibilityMenuItem);

        return viewMenu;
    }
}
