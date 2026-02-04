import javax.swing.*;
import java.awt.*;

public class MainPanel {
    protected static JPanel mainPanel(JPanel northPanel, JPanel buttonPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        return mainPanel;
    }
}
