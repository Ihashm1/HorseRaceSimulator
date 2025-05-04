import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

public class MainPanel {

    static JTextField laneInput;
    static JTextField lengthInput;
    static JLabel resultLabel;
    static RacePanel racePanel;
    static WeatherCondition selectedWeather = WeatherCondition.DRY;
    static int currentWeatherIndex = 0;

    // Stats variables
    static JTabbedPane tabbedPane;
    static JTable statsTable;
    static DefaultTableModel statsModel;
    static HashMap<String, HorseStats> horseStatsMap = new HashMap<>();

    /**
     * Main method to initialise the GUI and start the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = createFrame();
            JPanel mainPanel = new JPanel(new BorderLayout());

            // Left-side vertical stack (customisation + input)
            JPanel leftSidePanel = new JPanel();
            leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));

            // Input panel for lanes and track length
            JPanel inputPanel = createInputPanel();

            // Control panel with "Apply Settings" button
            JPanel controlPanel = createControlPanel();

            leftSidePanel.add(inputPanel);
            leftSidePanel.add(controlPanel);

            // Right: race output panel
            JPanel outputPanel = createOutputPanel();

            mainPanel.add(leftSidePanel, BorderLayout.WEST);
            mainPanel.add(outputPanel, BorderLayout.CENTER);

            frame.add(mainPanel);
            frame.setVisible(true);

            // Set padding for the left-side panel
            leftSidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            racePanel.setPreferredSize(new Dimension(600, 400));

            // Creating stats tab for race statistics
            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Race", mainPanel);
            tabbedPane.addTab("Stats", createStatsTab());

            frame.add(tabbedPane);
        });
    }

    /**
     * Creates the main application frame.
     * @return The JFrame for the application.
     */
    private static JFrame createFrame() {
        JFrame frame = new JFrame("Horse Race Track Designer");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    /**
     * Creates the input panel for entering the number of lanes and track length.
     * @return The JPanel containing input fields.
     */
    private static JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Input for number of lanes
        JLabel laneLabel = new JLabel("Number of Lanes:");
        laneInput = new JTextField();
        laneInput.setPreferredSize(new Dimension(40, 20)); 

        // Input for track length
        JLabel lengthLabel = new JLabel("Track Length:");
        lengthInput = new JTextField();
        lengthInput.setPreferredSize(new Dimension(40, 20)); 

        panel.add(laneLabel);
        panel.add(laneInput);
        panel.add(lengthLabel);
        panel.add(lengthInput);

        return panel;
    }

    /**
     * Creates the control panel with the "Apply Settings" button.
     * @return The JPanel containing the control button.
     */
    private static JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton applyButton = new JButton("Apply Settings");
        resultLabel = new JLabel("");

        // Add action listener to handle button click
        applyButton.addActionListener(e -> handleApplySettings());

        panel.add(applyButton);
        panel.add(resultLabel);
        return panel;
    }

    /**
     * Creates the output panel where the race will be displayed.
     * @return The JPanel for race output.
     */
    private static JPanel createOutputPanel() {
        racePanel = new RacePanel();
        racePanel.setPreferredSize(new Dimension(600, 300));
        racePanel.setBackground(Color.WHITE);
        return racePanel;
    }

    /**
     * Handles the "Apply Settings" button click event.
     * Reads input values, sets up the race, and starts the simulation.
     */
    private static void handleApplySettings() {
        String laneText = laneInput.getText();
        String lengthText = lengthInput.getText();

        try {
            // Parse input values for lanes and track length
            int lanes = Integer.parseInt(laneText);
            int length = Integer.parseInt(lengthText);

            // Randomly assign a weather condition for the race
            Random rand = new Random();
            selectedWeather = WeatherCondition.CONDITIONS[rand.nextInt(WeatherCondition.CONDITIONS.length)];
            resultLabel.setText("Track: " + lanes + " lanes, " + length + " units | Weather: " + selectedWeather.getName());

            // Get horse configurations and start the race simulation
            ArrayList<HorseConfig> horseConfigs = getHorseConfigs(lanes);
            runRaceSimulation(length, horseConfigs);

        } catch (NumberFormatException ex) {
            // Handle invalid input
            resultLabel.setText("Please enter valid numbers.");
        }
    }

    /**
     * Prompts the user to configure horses for the race.
     * @param lanes The number of lanes in the race.
     * @return A list of HorseConfig objects for each horse.
     */
    private static ArrayList<HorseConfig> getHorseConfigs(int lanes) {
        ArrayList<HorseConfig> configs = new ArrayList<>();
        for (int i = 0; i < lanes; i++) {
            // Prompt user for horse name
            String name = JOptionPane.showInputDialog(null, "Enter name for Horse " + (i + 1) + ":");
            if (name == null || name.trim().isEmpty()) name = "Horse" + (i + 1);

            // Open customization panel for the horse
            CustomisationPanel cp = new CustomisationPanel();
            int result = JOptionPane.showConfirmDialog(null, cp, "Customise " + name, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) continue;

            // Get selected attributes from the customization panel
            String symbol = (String) cp.symbolBox.getSelectedItem();
            int speedBonus = 0;
            double confidenceBonus = 0;

            // Apply breed-specific bonuses
            String breed = (String) cp.breedBox.getSelectedItem();
            if (breed.contains("Thoroughbred")) { speedBonus += 0.5; confidenceBonus -= 0.2; }
            if (breed.contains("Quarter Horse")) { confidenceBonus += 0.5; speedBonus -= 0.2; }
            if (breed.contains("Arabian")) { speedBonus += 0.3; confidenceBonus += 0.2; }
            if (breed.contains("French Trotter")) { speedBonus += 0.2; confidenceBonus += 0.3; }
            if (breed.contains("Shetland Pony")) { confidenceBonus += 0.3; }

            // Apply equipment-specific bonuses
            if (cp.saddleBox.getSelectedItem().toString().contains("+15 confidence")) confidenceBonus += 0.15;
            if (cp.horseshoeBox.getSelectedItem().toString().contains("Horseshoe")) {
                speedBonus += 0.1;
                confidenceBonus += 0.1;
            }
            if (cp.bridleBox.getSelectedItem().toString().contains("Bridle")) speedBonus += 0.15;

            // Add horse configuration to the list
            String coatColor = (String) cp.coatBox.getSelectedItem();
            configs.add(new HorseConfig(name, symbol, speedBonus, confidenceBonus, coatColor, breed));
        }

        return configs;
    }

    /**
     * Creates the stats tab with a table to display horse statistics.
     * @return A JScrollPane containing the stats table.
     */
    private static JScrollPane createStatsTab() {
        String[] columns = {
            "Horse", "Races", "Wins", "Falls", "Best Time", "Worst Time",
            "Avg Speed", "Avg Conf", "Win %"
        };
        statsModel = new DefaultTableModel(columns, 0);
        statsTable = new JTable(statsModel);
        return new JScrollPane(statsTable);
    }

    /**
     * Runs the race simulation with the given track length and horse configurations.
     * @param length The length of the track.
     * @param horseConfigs The configurations for the horses.
     */
    private static void runRaceSimulation(int length, ArrayList<HorseConfig> horseConfigs) {
        int lanes = horseConfigs.size();
        Race race = new Race(length, lanes);
        Horse[] horses = new Horse[lanes];

        // Initialise horses and add them to the race
        for (int i = 0; i < lanes; i++) {
            HorseConfig config = horseConfigs.get(i);
            double confidence = Math.max(0.3, selectedWeather.getBaseConfidence() + config.confidenceBonus);
            Horse h = new Horse(config.symbol.charAt(0), config.name, confidence);
            horses[i] = h;
            race.addHorse(h, i + 1);
        }

        // Set up the race panel for display
        racePanel.setupRace(horses, length, horseConfigs);

        // Start the race simulation in a separate thread
        new Thread(() -> {
            boolean finished = false;
            int ticks = 0;

            while (!finished) {
                try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) {}
                ticks++;

                // Move horses and handle falls
                for (int i = 0; i < horses.length; i++) {
                    Horse h = horses[i];
                    HorseConfig config = horseConfigs.get(i);

                    if (!h.hasFallen() && Math.random() < h.getConfidence()) {
                        int totalSpeed = Math.max(1, (int)(selectedWeather.getSpeedMultiplier() + config.speedBonus));
                        for (int m = 0; m < totalSpeed; m++) h.moveForward();
                    }

                    double fallChance = 0.05 * (1 - h.getConfidence());
                    if (!h.hasFallen() && Math.random() < fallChance) h.fall();
                }

                racePanel.repaint();

                // Check if the race is finished
                boolean someoneWon = false;
                boolean allFallen = true;

                for (Horse h : horses) {
                    if (h.getDistanceTravelled() >= length) someoneWon = true;
                    if (!h.hasFallen()) allFallen = false;
                }

                finished = someoneWon || allFallen;

                // Handle race results
                if (finished) {
                    for (int i = 0; i < horses.length; i++) {
                        Horse h = horses[i];
                        HorseConfig cfg = horseConfigs.get(i);
                        String horseKey = h.getName() + " " + cfg.coatColor + " " + cfg.breed;
                        horseStatsMap.putIfAbsent(horseKey, new HorseStats());
                        HorseStats stats = horseStatsMap.get(horseKey);
                        
                        boolean won = h.getDistanceTravelled() >= length;

                        double timeInSeconds = ticks * 0.1;
                        stats.recordRace(won, h.hasFallen(), timeInSeconds, h.getConfidence(), h.getDistanceTravelled());
                    }

                    updateStatsTable();

                    if (someoneWon) {
                        for (Horse h : horses) {
                            if (h.getDistanceTravelled() >= length) {
                                JOptionPane.showMessageDialog(null, "🏆 Winner: " + h.getName());
                                break;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "💥 All horses have fallen! No winner!");
                    }
                }
            }
        }).start();
    }

    /**
     * Updates the stats table with the latest horse statistics.
     */
    private static void updateStatsTable() {
        statsModel.setRowCount(0);
        for (String horseKey : horseStatsMap.keySet()) {
            HorseStats stats = horseStatsMap.get(horseKey);
            statsModel.addRow(new Object[]{
                horseKey,
                stats.getRaces(),
                stats.getWins(),
                stats.getFalls(),
                stats.getBestTime() == -1 ? "N/A" : stats.getBestTime(),
                stats.getWorstTime() == 0 ? "N/A" : stats.getWorstTime(),
                String.format("%.2f", stats.getAvgSpeed()),
                String.format("%.2f", stats.getAvgConfidence()),
                String.format("%.0f%%", stats.getWinRatio() * 100)
            });
        }
    }
}