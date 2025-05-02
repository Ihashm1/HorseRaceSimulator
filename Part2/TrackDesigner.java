import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;

public class TrackDesigner {

    static JTextField laneInput;
    static JTextField lengthInput;
    static JLabel resultLabel;
    static JTextArea raceOutputArea;

        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = createFrame();
            JPanel mainPanel = new JPanel(new BorderLayout());
            JPanel inputPanel = createInputPanel();
            JPanel controlPanel = createControlPanel();
            JScrollPane outputPanel = createOutputPanel();

            mainPanel.add(inputPanel, BorderLayout.NORTH);
            mainPanel.add(controlPanel, BorderLayout.CENTER);
            mainPanel.add(outputPanel, BorderLayout.SOUTH);

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("Horse Race Track Designer");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private static JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel laneLabel = new JLabel("Number of Lanes:");
        laneInput = new JTextField();
        laneInput.setPreferredSize(new Dimension(40, 20)); // smaller width

        JLabel lengthLabel = new JLabel("Track Length:");
        lengthInput = new JTextField();
        lengthInput.setPreferredSize(new Dimension(40, 20)); // smaller width

        panel.add(laneLabel);
        panel.add(laneInput);
        panel.add(lengthLabel);
        panel.add(lengthInput);

        return panel;
    }

    private static JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton applyButton = new JButton("Apply Settings");
        resultLabel = new JLabel("");

        applyButton.addActionListener(e -> handleApplySettings());

        panel.add(applyButton);
        panel.add(resultLabel);
        return panel;
    }

    private static JScrollPane createOutputPanel() {
        raceOutputArea = new JTextArea(8, 40);
        raceOutputArea.setEditable(false);
        raceOutputArea.setLineWrap(true);
        raceOutputArea.setWrapStyleWord(true);

        return new JScrollPane(raceOutputArea);
    }

    private static void handleApplySettings() {
    String laneText = laneInput.getText();
    String lengthText = lengthInput.getText();

    try {
        int lanes = Integer.parseInt(laneText);
        int length = Integer.parseInt(lengthText);
        resultLabel.setText("Track: " + lanes + " lanes, " + length + " units");

        runRaceSimulation(lanes, length); // <-- NEW

    } catch (NumberFormatException ex) {
        resultLabel.setText("Please enter valid numbers.");
        raceOutputArea.setText("");
    }
    }

    private static void runRaceSimulation(int lanes, int length) {
    Race race = new Race(length, lanes);

    for (int i = 1; i <= lanes; i++) {
        // Generate dummy horses with different names and confidence
        Horse h = new Horse((char) ('A' + i - 1), "Horse" + i, 0.5 + (i * 0.05));
        race.addHorse(h, i);
    }

    // Capture console output
    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
    java.io.PrintStream ps = new java.io.PrintStream(baos);
    java.io.PrintStream old = System.out;
    System.setOut(ps);

    race.startRace();

    System.out.flush();
    System.setOut(old);
    raceOutputArea.setText(baos.toString());
    }
}
