import javax.swing.*;

public class GetJMenu extends ShowTable{
    protected static JMenu getJMenu() {
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(_ -> Save.saveFile());
        fileMenu.add(saveMenuItem);

        JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
        saveAsMenuItem.addActionListener(_ -> Save.saveFileAs());
        fileMenu.add(saveAsMenuItem);

        JMenuItem importMenuItem = new JMenuItem("Import...");
        importMenuItem.addActionListener(_ -> ImportExport.importData());
        fileMenu.add(importMenuItem);

        JMenuItem exportMenuItem = new JMenuItem("Export...");
        exportMenuItem.addActionListener(_ -> ImportExport.exportData());
        fileMenu.add(exportMenuItem);

        JMenuItem settingsMenuItem = new JMenuItem("Settings/Preferences...");
        settingsMenuItem.addActionListener(_ -> ChangeMasterPasswd.changeMasterPasswd());
        fileMenu.add(settingsMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(_ -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }
}
