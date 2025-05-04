# 🐎 Horse Race Simulator

A horse race simulation project.

Welcome to the **Horse Race Simulator**, a Java-based racing game featuring both a **textual** and **graphical** version. The simulator is divided into two parts:

- `part1`: Text-based race simulation
- `part2`: GUI-based race simulator with advanced customisation and statistics tracking

---
## 🧾 Requirements

- Java 8 or later
- No external dependencies
- Works from the command line (no IDE required)

---

## ▶️ How to Run the Simulator

### Part 1 – Text-Based Version

1. Open your terminal and navigate to the `part1` folder.
2. To ensure proper unicode display run:
   `chcp 65001`
3. Compile the race file:
   `javac Race.java`
4. Then run the race:
   `java Race`

This will execute the startRace() method within the main() method in Race.java.

To change horse names or add/remove horses, modify the main() method in Race.java. For example:

````
Race race1 = new Race(20); // Specify race length

Horse h1 = new Horse('B', "Bolt", 0.8); //Create horse symbol, name and confidence

race1.addHorse(h1, 1); // Specifies horse and lane number assigned to race

race1.startRace(); // Begins the race
`````
---

### Part 2 – Graphical (GUI) Version
1. Open your terminal and navigate to the `part2` folder.
2. Compile the race file:
   `javac MainPanel.java`
3. Then run the race:
   `java MainPanel`

This internally calls startRaceGUI() to begin the simulation and launches the graphical version

🕹️ Gameplay Instructions (GUI)
1. Input track length and number of lanes (horses) in the text fields.
2. Click "Apply Settings" to lock in the values.
3. A weather condition will be randomly chosen (Dry, Muddy, or Icy), which affects horse performance.

You'll then be prompted (via popups) to:
1. Name each horse
2. Select breed, coat color, symbol, and equipment
3. These choices impact each horse’s speed and confidence.
4. Once all horses are customised, the race begins.

During the race:
Horses will move across the screen with their confidence shown in brackets.
If a horse falls, a red ❌ will appear and the horse stops.
The first to reach the finish wins. If all horses fall, no one wins.


📊 Stats Tab

After each race, performance is logged in the Stats tab (top-left corner). Tracked metrics include:

* Races Run
* Wins
* Falls
* Best Time
* Worst Time
* Average Speed
* Average Confidence
* Win Ratio

You can run the race as many times as you like—stats are cumulative during the session.

---
Future Developments:
Betting system with odds calcualted per horse




