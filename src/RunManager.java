import javax.swing.*;

public class RunManager {
    protected static void runPasswordManager(int position) {
        JFrame mainFrame = BaseFrame.baseFrame();
        JPanel inputPanel = InputPanel.inputPanel();
        JPanel notesPanel = NotesPanel.notesPanel();
        JPanel buttonPanel = ButtonPanel.buttonPanel(mainFrame, (JTextField) inputPanel.getComponent(1),
                (JTextField) inputPanel.getComponent(3), (JTextField) inputPanel.getComponent(5),
                (JTextField) inputPanel.getComponent(7),
                (JTextArea) ((JScrollPane) notesPanel.getComponent(1)).getViewport().getComponent(0), position);
        JPanel northPanel = NorthPanel.northPanel(inputPanel, notesPanel);
        JPanel mainPanel = MainPanel.mainPanel(northPanel, buttonPanel);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    protected static void runPasswordManager() {
        runPasswordManager(-1);
    }
}