import javax.swing.*;
import java.awt.*;

public class HelpDoc extends PasswdTable {
    protected static void helpDoc() {
        String helpContent = """
                This is the help documentation.
                 - File Menu: Contains options for file operations.
                 - Edit Menu:  For editing entries.
                 - etc...
                """;

        JTextArea helpTextArea = new JTextArea(helpContent);
        helpTextArea.setEditable(false);
        helpTextArea.setLineWrap(true);
        helpTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(helpTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(Frame, scrollPane, "Help Documentation", JOptionPane.INFORMATION_MESSAGE);
    }
}
