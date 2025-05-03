import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.util.concurrent.TimeUnit;
import java.util.Random;



public class TrackDesigner {

    
    static JTextField laneInput;
    static JTextField lengthInput;
    static JLabel resultLabel;
    static RacePanel racePanel;
    static WeatherCondition selectedWeather = WeatherCondition.DRY;
    static int currentWeatherIndex = 0;

        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                JFrame frame = createFrame();
                JPanel mainPanel = new JPanel(new BorderLayout());

                // Left-side vertical stack (customisation + input)
                JPanel leftSidePanel = new JPanel();
                leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));

                CustomisationPanel customPanel = new CustomisationPanel();
                leftSidePanel.add(customPanel);

                JPanel inputPanel = createInputPanel();
                JPanel controlPanel = createControlPanel();
                leftSidePanel.add(inputPanel);
                leftSidePanel.add(controlPanel);

                // Right: race output
                JPanel outputPanel = createOutputPanel();

                mainPanel.add(leftSidePanel, BorderLayout.WEST);
                mainPanel.add(outputPanel, BorderLayout.CENTER);

                frame.add(mainPanel);
                frame.setVisible(true);

                leftSidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                racePanel.setPreferredSize(new Dimension(600, 400));
            
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

    private static JPanel createOutputPanel() {
    racePanel = new RacePanel();
    racePanel.setPreferredSize(new Dimension(600, 300));
    racePanel.setBackground(Color.WHITE);
    return racePanel;
    }

    private static void handleApplySettings() {
    String laneText = laneInput.getText();
    String lengthText = lengthInput.getText();

    try {
        int lanes = Integer.parseInt(laneText);
        int length = Integer.parseInt(lengthText);
        // Randomly assign weather condition
        Random rand = new Random();
        selectedWeather = WeatherCondition.CONDITIONS[rand.nextInt(WeatherCondition.CONDITIONS.length)];
        resultLabel.setText("Track: " + lanes + " lanes, " + length + " units | Weather: " + selectedWeather.getName());

        String[] horseNames = new String[lanes];
        HorseCustomisation[] customisations = promptHorseCustomisations(lanes, horseNames);
        runRaceSimulation(lanes, length, horseNames, customisations);

    } catch (NumberFormatException ex) {
      resultLabel.setText("Please enter valid numbers.");
    }
    }

    private static HorseCustomisation[] promptHorseCustomisations(int lanes, String[] horseNames) {
    HorseCustomisation[] customisations = new HorseCustomisation[lanes];

    for (int i = 0; i < lanes; i++) {
        JTextField nameField = new JTextField("Horse" + (i + 1));
        CustomisationPanel customPanel = new CustomisationPanel();

        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.add(new JLabel("Enter name and customise Horse " + (i + 1) + ":"), BorderLayout.NORTH);
        dialogPanel.add(nameField, BorderLayout.CENTER);
        dialogPanel.add(customPanel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(null, dialogPanel, "Customise Horse " + (i + 1),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                name = "Horse" + (i + 1);
            }
            horseNames[i] = name;
            customisations[i] = customPanel.getSelectedCustomisation();
        } else {
            horseNames[i] = "Horse" + (i + 1);
            customisations[i] = new HorseCustomisation(); // default values
        }
    }

    return customisations;
    }

    private static void runRaceSimulation(int lanes, int length, String[] horseNames) {
    Race race = new Race(length, lanes);
    Horse[] horses = new Horse[lanes];

    for (int i = 0; i < lanes; i++) {
    double confidence = selectedWeather.getBaseConfidence() + (i * 0.05);
    horses[i] = new Horse('♘', horseNames[i], confidence);
    race.addHorse(horses[i], i + 1);
    }

    // Setup the panel for drawing
    racePanel.setupRace(horses, length);

    // Run the race in a separate thread
    new Thread(() -> {
        boolean finished = false;

        while (!finished) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Horse h : horses) {
                if (!h.hasFallen() && Math.random() < h.getConfidence()) {
                for (int m = 0; m < selectedWeather.getSpeedMultiplier(); m++) {
                    h.moveForward();
                }
}
                if (!h.hasFallen() && Math.random() < (0.1 * h.getConfidence() * h.getConfidence())) {
                    h.fall();
                }
            }

            racePanel.repaint();

            boolean someoneWon = false;
            for (Horse h : horses) {
                if (h.getDistanceTravelled() >= length) {
                    someoneWon = true;
                    break;
                }
            }

            boolean allFallen = true;
            for (Horse h : horses) {
                if (!h.hasFallen()) {
                    allFallen = false;
                    break;
                }
            }

            finished = someoneWon || allFallen;

            // Determine and display race result
        String message;
        if (someoneWon) {
            // Find first horse that finished
            for (Horse h : horses) {
                if (h.getDistanceTravelled() >= length) {
                    message = "🏆 Winner: " + h.getName();
                    JOptionPane.showMessageDialog(null, message);
                    break;
                }
            }
        } else if (allFallen) {
            message = "💥 All horses have fallen! No winner!";
            JOptionPane.showMessageDialog(null, message);
        }
                }
            }).start();
    }

}


class RacePanel extends JPanel  {
    private Horse[] horses;
    private int trackLength;

    public void setupRace(Horse[] horses, int trackLength) {
        this.horses = horses;
        this.trackLength = trackLength;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (horses == null) return;

        int laneHeight = 40;
        int margin = 20;
        for (int i = 0; i < horses.length; i++) {
            Horse h = horses[i];
            if (h == null) continue;

            int y = i * laneHeight + margin;
            g.drawLine(margin, y + 20, margin + trackLength * 20, y + 20); // lane line

            int x = margin + h.getDistanceTravelled() * 20;
            String symbol = h.hasFallen() ? "❌" : String.valueOf(h.getSymbol());
            g.drawString(symbol, x, y + 20);
            g.drawString(h.getName() + " (Conf: " + String.format("%.2f", h.getConfidence()) + ")", x + 20, y + 20);
        }
    }
}
