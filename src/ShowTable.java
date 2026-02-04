import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowTable extends GraphicalApp {
    private static int highlightedRow = -1;
    static final String MASK = "********"; // 8
    private static boolean isPWVisible = false;
    public static void setIsPWVisible(boolean isPWVisible) {
        ShowTable.isPWVisible = isPWVisible;
        if (isPWVisible) {
            highlightedRow = -1;
        }
    }
    static void ShowPasswordTable() {
        Frame = new JFrame("Password Entries");
        Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Frame.setSize(800, 600);
        Frame.setLocationRelativeTo(null); // center

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int column) {
                return String.class;
            }
        };
        Model.addColumn("Title");
        Model.addColumn("User Name");
        Model.addColumn("Password");
        Model.addColumn("URL");
        Model.addColumn("Notes");
        Table = new JTable(Model) {
            @Override
            public Object getValueAt(int row, int column) {
                if (column == 2) {
                    if (isPWVisible) {
                        Entry entry = storage.getEntry(row);
                        return (entry != null) ? HashGenerator.decode(entry.getPassword()) : MASK;
                    } else if (row == highlightedRow && highlightedRow != -1) {
                        Entry entry = storage.getEntry(row);
                        if (entry != null) {
                            return HashGenerator.decode(entry.getPassword());
                        }
                    } else {
                        return MASK;
                    }
                }
                return super.getValueAt(row, column);
            }
            @Override
            public Class<?> getColumnClass(int column) {
                return String.class;
            }
        };

// ---------------------------------------------------------------------------------------------------------------------

        new JPanel(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();

// ---------------------------------------------------------------------------------------------------------------------

        JMenu fileMenu = GetJMenu.getJMenu();
        menuBar.add(fileMenu);

// ---------------------------------------------------------------------------------------------------------------------

        JMenu entryMenu = GetMenu.getMenu();
        menuBar.add(entryMenu);

// ---------------------------------------------------------------------------------------------------------------------

        JMenu findMenu = new JMenu("Find");
        JMenuItem findMenuItem = new JMenuItem("Find...");
        JMenuItem advancedFindMenuItem = new JMenuItem("Advanced Find...");
        findMenuItem.addActionListener(new FindActionListener(Table, Frame, Model));
        advancedFindMenuItem.addActionListener(new AdvancedFindActionListener(Table, Frame, Model));

        findMenu.add(findMenuItem);
        findMenu.add(advancedFindMenuItem);
        menuBar.add(findMenu);
        Frame.setJMenuBar(menuBar);

        menuBar.add(findMenu);

// ---------------------------------------------------------------------------------------------------------------------

        JMenu viewMenu = GetViewMenu.getViewMenu();

        menuBar.add(viewMenu);

// ---------------------------------------------------------------------------------------------------------------------

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem generatePasswordMenuItem = new JMenuItem("Generate Password");
        generatePasswordMenuItem.addActionListener(new GeneratePasswdActionListener(Frame));
        toolsMenu.add(generatePasswordMenuItem);

        JMenuItem passwordStrengthMeterMenuItem = new JMenuItem("Password Strength Meter");
        passwordStrengthMeterMenuItem.addActionListener(_ -> PasswdStrengh.passwdStrength());
        toolsMenu.add(passwordStrengthMeterMenuItem);

        JMenuItem optionsMenuItem = new JMenuItem("Options...");
        optionsMenuItem.addActionListener(_ -> PasswdStrengh.generatorOptions());
        toolsMenu.add(optionsMenuItem);

        menuBar.add(toolsMenu);

// ---------------------------------------------------------------------------------------------------------------------

        JMenu helpMenu = new JMenu("Help");

        JMenuItem contentsMenuItem = new JMenuItem("Contents/Help Documentation");
        contentsMenuItem.addActionListener(_ -> HelpDoc.helpDoc());
        helpMenu.add(contentsMenuItem);

        JMenuItem aboutMenuItem = new JMenuItem("About...");
        aboutMenuItem.addActionListener(_ -> ShowAbout.showAbout());
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        Frame.setJMenuBar(menuBar);

        Font tableFont = new Font("SansSerif", Font.PLAIN, 16);
        Table.setFont(tableFont);
        Table.setRowHeight(Table.getRowHeight() + 10);

        JScrollPane tableScrollPane = new JScrollPane(Table);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        Frame.getContentPane().add(tablePanel, BorderLayout.CENTER);

        Table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                int row = Table.rowAtPoint(p);
                if (row != highlightedRow) {
                    highlightedRow = row;
                    Table.repaint();
                }
            }
        });

        Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (highlightedRow != -1) {
                    highlightedRow = -1;
                    Table.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = Table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < Table.getRowCount()) {
                        Table.setRowSelectionInterval(row, row);
                        PasswdTable.showPopupMenu(e.getX(), e.getY(), row);
                    }
                }
            }
        });

        JButton addButton = new JButton("Add Password");
        addButton.setPreferredSize(new Dimension(150, 30));
        addButton.addActionListener(_ -> RunManager.runPasswordManager());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(addButton);

        tablePanel.add(bottomPanel, BorderLayout.SOUTH);

        Frame.add(tablePanel);
        Frame.setVisible(true);

        UpdateTable.updateTable();
    }
}
