import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenLink extends ShowTable{
    protected static void openLink(int row) {
        if (row >= 0 && row < Model.getRowCount()) {
            String url = (String) Model.getValueAt(row, 3);

            if (url != null && !url.trim().isEmpty()) {
                URI uri;
                try {
                    uri = new URI(url);
                    Desktop.getDesktop().browse(uri);
                    JOptionPane.showMessageDialog(Frame, "Opened URL: " + url, "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | URISyntaxException ex) {
                    String googleSearchUrl = "https://www.google.com/search?q=" + encodeUrl(url);
                    try {
                        uri = new URI(googleSearchUrl);
                        Desktop.getDesktop().browse(uri);
                        JOptionPane.showMessageDialog(Frame, "Opened Google search for the URL: " + url, "Info", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException | URISyntaxException googleEx) {
                        JOptionPane.showMessageDialog(Frame, "Error opening URL or searching in Google: " + googleEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(Frame, "No URL available for this entry.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(Frame, "No entry selected.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private static String encodeUrl(String url) {
        try {
            URI uri = new URI(url);
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            StringBuilder encoded = new StringBuilder();
            for (char c : url.toCharArray()) {
                if (Character.isLetterOrDigit(c) || "-._~:/?#[]@!$&'()*+,;=".contains(String.valueOf(c))) {
                    encoded.append(c);
                } else { // Format specifier %02x
                    encoded.append(String.format("%% %02X", (int) c)); // Unicode
                } // output will start with % => for ' ' %20
            }
            return encoded.toString();
        }
    }
}
