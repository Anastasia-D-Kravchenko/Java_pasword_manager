import javax.swing.*;

public class ShowAbout extends PasswdTable {
    protected static void showAbout() {
        String aboutText = """
                Password Manager
                Version 1.0
                Author: Anastasiia Kravchenko
                Description: A simple password management application.""";
        JOptionPane.showMessageDialog(Frame, aboutText, "About Password Manager", JOptionPane.INFORMATION_MESSAGE);
    }
}
