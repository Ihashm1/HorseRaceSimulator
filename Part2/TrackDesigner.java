import javax.swing.JFrame;  // Imports JFrame class from Swing

public class TrackDesigner {

    public static void main(String[] args) {
        //Create a window (JFrame)
        JFrame frame = new JFrame("Horse Race Track Designer");

        // Set the size of the window
        frame.setSize(600, 400);  // Width: 600 pixels, Height: 400 pixels

        // Close the application when the user clicks the X button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the window visible
        frame.setVisible(true);
    }
}