import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackDesigner {

    // Declare global components to access across methods
    static JTextField laneInput;
    static JTextField lengthInput;
    static JLabel resultLabel;

    public static void main(String[] args) {
        JFrame frame = createFrame();
        JPanel panel = createPanel();

        addInputFields(panel);
        addApplyButton(panel);

        frame.add(panel);
        frame.setVisible(true);
    }

    //Create the main window
    private static JFrame createFrame() {
        JFrame frame = new JFrame("Horse Race Track Designer");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    //Create the main panel with layout
    private static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        return panel;
    }

    // Add input fields for lane count and track length
    private static void addInputFields(JPanel panel) {
        JLabel laneLabel = new JLabel("Number of Lanes:");
        laneInput = new JTextField();

        JLabel lengthLabel = new JLabel("Track Length:");
        lengthInput = new JTextField();

        panel.add(laneLabel);
        panel.add(laneInput);
        panel.add(lengthLabel);
        panel.add(lengthInput);
    }

    // Add button and result label, and link the button logic
    private static void addApplyButton(JPanel panel) {
        JButton applyButton = new JButton("Apply Settings");
        resultLabel = new JLabel("");

        panel.add(applyButton);
        panel.add(resultLabel);

        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleApplySettings();
            }
        });
    }

    // Handle what happens when the Apply button is clicked
    private static void handleApplySettings() {
        String laneText = laneInput.getText();
        String lengthText = lengthInput.getText();

        try {
            int lanes = Integer.parseInt(laneText);
            int length = Integer.parseInt(lengthText);
            resultLabel.setText("Track: " + lanes + " lanes, " + length + " units");
        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter valid numbers.");
        }
    }
}
