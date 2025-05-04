import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;



public class TrackDesigner {

    
    static JTextField laneInput;
    static JTextField lengthInput;
    static JLabel resultLabel;
    static RacePanel racePanel;
    static WeatherCondition selectedWeather = WeatherCondition.DRY;
    static int currentWeatherIndex = 0;

    // stats variables
    static JTabbedPane tabbedPane;
    static JTable statsTable;
    static DefaultTableModel statsModel;
    static HashMap<String, HorseStats> horseStatsMap = new HashMap<>();

        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                JFrame frame = createFrame();
                JPanel mainPanel = new JPanel(new BorderLayout());

                // Left-side vertical stack (customisation + input)
                JPanel leftSidePanel = new JPanel();
                leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));

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

                // creating stats tab
                tabbedPane = new JTabbedPane();
                tabbedPane.addTab("Race", mainPanel);
                tabbedPane.addTab("Stats", createStatsTab());

                frame.add(tabbedPane);
            
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

        ArrayList<HorseConfig> horseConfigs = getHorseConfigs(lanes);
        runRaceSimulation(length, horseConfigs);

    } catch (NumberFormatException ex) {
      resultLabel.setText("Please enter valid numbers.");
    }
    }

    private static ArrayList<HorseConfig> getHorseConfigs(int lanes) {
            ArrayList<HorseConfig> configs = new ArrayList<>();
            for (int i = 0; i < lanes; i++) {
            String name = JOptionPane.showInputDialog(null, "Enter name for Horse " + (i + 1) + ":");
            if (name == null || name.trim().isEmpty()) name = "Horse" + (i + 1);

            // Open a customisation panel for this horse
            CustomisationPanel cp = new CustomisationPanel();
            int result = JOptionPane.showConfirmDialog(null, cp, "Customise " + name, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) continue;

            String symbol = (String) cp.symbolBox.getSelectedItem();
            int speedBonus = 0;
            double confidenceBonus = 0;

            // Breed logic
            String breed = (String) cp.breedBox.getSelectedItem();
            if (breed.contains("Thoroughbred")) { speedBonus += 0.5; confidenceBonus -= 0.2; }
            if (breed.contains("Quarter Horse")) { confidenceBonus += 0.5; speedBonus -= 0.2; }
            if (breed.contains("Arabian")) { speedBonus += 0.3; confidenceBonus += 0.2; }
            if (breed.contains("French Trotter")) { speedBonus += 0.2; confidenceBonus += 0.3; }
            if (breed.contains("Shetland Pony")) { confidenceBonus += 0.3; }

            // Equipment logic
            if (cp.saddleBox.getSelectedItem().toString().contains("+15 confidence")) confidenceBonus += 0.15;
            if (cp.horseshoeBox.getSelectedItem().toString().contains("Horseshoe")) {
                speedBonus += 0.1;
                confidenceBonus += 0.1;
            }
            if (cp.bridleBox.getSelectedItem().toString().contains("Bridle")) speedBonus += 0.15;

            String coatColor = (String) cp.coatBox.getSelectedItem();
            configs.add(new HorseConfig(name, symbol, speedBonus, confidenceBonus, coatColor));
        }

            return configs;
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

    private static void runRaceSimulation(int length, ArrayList<HorseConfig> horseConfigs) {
    int lanes = horseConfigs.size();
    Race race = new Race(length, lanes);
    Horse[] horses = new Horse[lanes];

    for (int i = 0; i < lanes; i++) {
        HorseConfig config = horseConfigs.get(i);
        double confidence = selectedWeather.getBaseConfidence() + config.confidenceBonus;
        Horse h = new Horse(config.symbol.charAt(0), config.name, confidence);
        horses[i] = h;
        race.addHorse(h, i + 1);
    }

    racePanel.setupRace(horses, length, horseConfigs);

    new Thread(() -> {
        boolean finished = false;

        while (!finished) {
            try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) {}

            for (int i = 0; i < horses.length; i++) {
                Horse h = horses[i];
                HorseConfig config = horseConfigs.get(i);

                if (!h.hasFallen() && Math.random() < h.getConfidence()) {
                    int totalSpeed = (int)(selectedWeather.getSpeedMultiplier() + config.speedBonus);
                    for (int m = 0; m < totalSpeed; m++) h.moveForward();
                }

                if (!h.hasFallen() && Math.random() < (0.1 * h.getConfidence() * h.getConfidence())) h.fall();
            }

            racePanel.repaint();

            boolean someoneWon = false;
            boolean allFallen = true;

            for (Horse h : horses) {
                if (h.getDistanceTravelled() >= length) someoneWon = true;
                if (!h.hasFallen()) allFallen = false;
            }

            finished = someoneWon || allFallen;

            if (finished) {
                String message;
                if (someoneWon) {
                    for (Horse h : horses) {
                        if (h.getDistanceTravelled() >= length) {
                            message = "🏆 Winner: " + h.getName();
                            JOptionPane.showMessageDialog(null, message);
                            break;
                        }
                    }
                } else {
                    message = "💥 All horses have fallen! No winner!";
                    JOptionPane.showMessageDialog(null, message);
                }
            }
        }
    }).start();
    }

}


class RacePanel extends JPanel  {
    private Horse[] horses;
    private int trackLength;
    private String[] horseCoatColors;

    public void setupRace(Horse[] horses, int trackLength, ArrayList<HorseConfig> configs) {
        this.horses = horses;
        this.trackLength = trackLength;
        this.horseCoatColors = new String[horses.length];
        for (int i = 0; i < configs.size(); i++) {
            this.horseCoatColors[i] = configs.get(i).coatColor;
        }
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
            String symbol = String.valueOf(h.getSymbol());
        String currentSymbol = h.hasFallen() ? "❌" : symbol;

        g.drawString(symbol, margin - 15, y + 20); // Fixed symbol at lane start
        Color coat = getColorFromName(horseCoatColors[i]);
        g.setColor(coat);
        g.fillOval(x, y + 5, 20, 20); // draw colored body

        // Draw tail
        g.setColor(coat.darker());
        g.drawLine(x - 2, y + 15, x - 4, y + 18);


        // Draw legs
        g.setColor(Color.BLACK);
        g.drawLine(x + 15, y + 23, x + 16, y + 26);  
        g.drawLine(x + 5, y + 23, x + 4, y + 26);  

        // Draw head
        g.setColor(coat.brighter());
        g.fillOval(x + 18, y + 8, 10, 10); // head


        g.setColor(Color.BLACK);
        g.drawString(h.getName() + " (Conf: " + String.format("%.2f", h.getConfidence()) + ")", x + 30, y + 20);
                }
            }

        private Color getColorFromName(String name) {
        switch (name.toLowerCase()) {
        case "black": return Color.BLACK;
        case "yellow": return Color.YELLOW;
        case "brown": return new Color(139, 69, 19);
        case "grey": return Color.GRAY;
        case "pink": return Color.PINK;
        default: return Color.DARK_GRAY;
    }
}
}
