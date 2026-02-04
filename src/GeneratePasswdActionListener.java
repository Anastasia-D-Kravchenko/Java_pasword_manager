import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneratePasswdActionListener implements ActionListener {

    private final JFrame frame;

    public GeneratePasswdActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String generatedPassword = PasswdManager.generateStrongPasswd();
        JPanel messagePanel = new JPanel(new FlowLayout());
        JLabel passwordLabel = new JLabel("Generated Password: " + generatedPassword);
        JButton copyButton = new JButton("Copy");
        messagePanel.add(passwordLabel);
        messagePanel.add(copyButton);

        JOptionPane pane = new JOptionPane(messagePanel, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = pane.createDialog(frame, "Password Generator");
        dialog.setResizable(true);

        copyButton.addActionListener(_ -> {
            StringSelection stringSelection = new StringSelection(generatedPassword);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            dialog.dispose();
            JOptionPane.showMessageDialog(frame, "Generated password copied to clipboard.", "Password Generator", JOptionPane.INFORMATION_MESSAGE);
        });

        dialog.setVisible(true);
    }
}