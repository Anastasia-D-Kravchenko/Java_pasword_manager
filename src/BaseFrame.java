import javax.swing.*;

public class BaseFrame {
    protected static JFrame baseFrame() {
        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        return frame;
    }
}
