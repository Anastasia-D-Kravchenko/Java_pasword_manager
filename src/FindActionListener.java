import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FindActionListener extends ShowTable implements ActionListener{
    private String searchText = "";
    private final ArrayList<Integer> matchingRows = new ArrayList<>();
    private int currentIndex = -1;
    private JDialog findDialog;
    private JLabel resultCountLabel;

    private final JTable table;
    private final JFrame frame;
    private final DefaultTableModel model;

    public FindActionListener(JTable table, JFrame frame, DefaultTableModel model) {
        this.table = table;
        this.frame = frame;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (findDialog == null) {
            createFind();
        }
        findDialog.setVisible(true);
    }

    private void createFind() {
        findDialog = new JDialog(frame, "Find", false);
        findDialog.setLayout(new FlowLayout());

        JTextField searchTextField = new JTextField(20);
        JButton findButton = new JButton("Find");
        JButton nextButton = new JButton("^");
        JButton previousButton = new JButton("v");
        JButton closeButton = new JButton("X");
        resultCountLabel = new JLabel("0/0");

        findButton.addActionListener(_ -> {
            searchText = searchTextField.getText().toLowerCase();
            searching();
        });
        nextButton.addActionListener(_ -> navigateToResult(1));
        previousButton.addActionListener(_ -> navigateToResult(-1));
        closeButton.addActionListener(_ -> {
            findDialog.setVisible(false);
            searchText = "";
            matchingRows.clear();
            currentIndex = -1;
            table.clearSelection();
        });

        searchTextField.addActionListener(_ -> {
            searchText = searchTextField.getText().toLowerCase();
            searching();
        });

        findDialog.add(searchTextField);
        findDialog.add(findButton);
        findDialog.add(resultCountLabel);
        findDialog.add(previousButton);
        findDialog.add(nextButton);
        findDialog.add(closeButton);

        findDialog.pack(); // adjusts window size
        findDialog.setLocationRelativeTo(frame);
    }

    private void searching() {
        filling();
        updateResultCountLabel();
        if (!matchingRows.isEmpty()) {
            currentIndex = 0;
            table.setRowSelectionInterval(matchingRows.get(currentIndex), matchingRows.get(currentIndex));
            table.scrollRectToVisible(table.getCellRect(matchingRows.get(currentIndex), 0, true));
        } else {
            table.clearSelection();
            currentIndex = -1;
        }
        updateResultCountLabel();
    }

    private void filling() {
        matchingRows.clear();
        currentIndex = -1;

        if (searchText.isEmpty()) {
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Object nameValue = model.getValueAt(i, 0);
            if (nameValue != null && nameValue.toString().toLowerCase().contains(searchText)) {
                matchingRows.add(i);
            }
        }
    }

    private void navigateToResult(int direction) {
        if (matchingRows.isEmpty()) {
            return;
        }

        currentIndex += direction;
        if (currentIndex < 0) {
            currentIndex = matchingRows.size() - 1;
        } else if (currentIndex >= matchingRows.size()) {
            currentIndex = 0;
        }

        table.setRowSelectionInterval(matchingRows.get(currentIndex), matchingRows.get(currentIndex));
        table.scrollRectToVisible(table.getCellRect(matchingRows.get(currentIndex), 0, true));
        updateResultCountLabel();
    }

    private void updateResultCountLabel() {
        resultCountLabel.setText((matchingRows.isEmpty() ? "0" : (currentIndex + 1)) + "/" + matchingRows.size());
    }
}