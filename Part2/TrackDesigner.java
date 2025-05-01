import javax.swing.*;
import java.awt.*;

public class TrackDesigner {

    public static void main(String[] args) {
        // Create the window
        JFrame frame = new JFrame("Horse Race Track Designer");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold UI components - A container for labels, buttons, etc.
        JPanel panel = new JPanel();
        //Organises items in a 4x2 grid with spacing between rows/columns.
        panel.setLayout(new GridLayout(4, 2, 10, 10)); 

        // Lane count input
        JLabel laneLabel = new JLabel("Number of Lanes:");
        //A box where the user can type input.
        JTextField laneInput = new JTextField();

        // Track length input
        JLabel lengthLabel = new JLabel("Track Length:");
        JTextField lengthInput = new JTextField();

        // Add components to panel
        panel.add(laneLabel);
        panel.add(laneInput);
        panel.add(lengthLabel);
        panel.add(lengthInput);

        // Add panel to frame and show
        frame.add(panel);
        frame.setVisible(true);
    }
}