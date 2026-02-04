import javax.swing.*;
import java.awt.*;

public class NotesPanel {
    protected static JPanel notesPanel() {
        JPanel notesPanel = new JPanel(new BorderLayout());
        int width = 50;
        int hight = 25;
        notesPanel.setBorder(BorderFactory.createEmptyBorder(hight, width, 0, width));

        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JTextArea notesArea = new JTextArea(5, 15);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);

        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        notesPanel.add(notesLabel, BorderLayout.NORTH);
        notesPanel.add(notesScrollPane, BorderLayout.CENTER);

        return notesPanel;
    }
}
