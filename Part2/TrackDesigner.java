import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.util.concurrent.TimeUnit;



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
            JPanel inputPanel = createInputPanel();
            JPanel controlPanel = createControlPanel();
            JPanel outputPanel = createOutputPanel();

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
        resultLabel.setText("Track: " + lanes + " lanes, " + length + " units");

        String[] horseNames = promptHorseNames(lanes);
        runRaceSimulation(lanes, length, horseNames);

    } catch (NumberFormatException ex) {
      resultLabel.setText("Please enter valid numbers.");
    }
    }

    private static String[] promptHorseNames(int lanes) {
    String[] names = new String[lanes];

    for (int i = 0; i < lanes; i++) {
        String name = JOptionPane.showInputDialog(null, "Enter name for Horse " + (i + 1) + ":");
        if (name == null || name.trim().isEmpty()) {
            name = "Horse" + (i + 1); // fallback name
        }
        names[i] = name;
    }

    return names;
    }

    private static void runRaceSimulation(int lanes, int length, String[] horseNames) {
    Race race = new Race(length, lanes);
    Horse[] horses = new Horse[lanes];

    for (int i = 0; i < lanes; i++) {
    horses[i] = new Horse('♘', horseNames[i], 0.5 + (i * 0.05));
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
                    h.moveForward();
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
            g.drawString(h.getName(), x + 20, y + 20);
        }
    }
}
