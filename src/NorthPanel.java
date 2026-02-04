import javax.swing.*;
import java.awt.*;

public class NorthPanel {
    protected static JPanel northPanel(JPanel inputPanel, JPanel notesPanel) {
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 1));
        northPanel.add(inputPanel);
        northPanel.add(notesPanel);
        return northPanel;
    }
}
