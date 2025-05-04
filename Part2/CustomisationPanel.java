import javax.swing.*;
import java.awt.*;

/**
 * CustomisationPanel class allows users to customise horse attributes
 * such as breed, coat colour, symbol, saddle, horseshoe, and bridle.
 */
public class CustomisationPanel extends JPanel {

    // ComboBoxes for customisation options
    public JComboBox<String> breedBox;       // Dropdown for selecting horse breed
    public JComboBox<String> coatBox;        // Dropdown for selecting coat colour
    public JComboBox<String> symbolBox;      // Dropdown for selecting horse symbol
    public JComboBox<String> saddleBox;      // Dropdown for selecting saddle
    public JComboBox<String> horseshoeBox;   // Dropdown for selecting horseshoe
    public JComboBox<String> bridleBox;      // Dropdown for selecting bridle

    /**
     * Constructor to set up the customisation panel with various options.
     */
    public CustomisationPanel() {
        // Set layout and border for the panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Horse Customisation"));

        // Breed selection
        breedBox = new JComboBox<>(new String[] {
            "Thoroughbred (+5 speed, -1 confidence)",
            "Quarter Horse (+5 confidence, -1 confidence)",
            "Arabian (+3 speed, +2 confidence)",
            "French Trotter (+3 confidence, +2 speed)",
            "Shetland Pony (+4 confidence)"
        });
        add(new JLabel("Breed:"));
        add(breedBox);

        // Coat Colour selection
        coatBox = new JComboBox<>(new String[] {
            "Black", "Yellow", "Brown", "Grey", "Pink"
        });
        add(new JLabel("Coat Colour:"));
        add(coatBox);

        // Symbol selection
        symbolBox = new JComboBox<>(new String[] {
            "♘", "♞"
        });
        add(new JLabel("Symbol:"));
        add(symbolBox);

        // Saddle selection
        saddleBox = new JComboBox<>(new String[] {
            "None", "Saddle (+15 confidence)"
        });
        add(new JLabel("Saddle:"));
        add(saddleBox);

        // Horseshoe selection
        horseshoeBox = new JComboBox<>(new String[] {
            "None", "Horseshoe (+5 speed, +10 confidence)"
        });
        add(new JLabel("Horseshoe:"));
        add(horseshoeBox);

        // Bridle selection
        bridleBox = new JComboBox<>(new String[] {
            "None", "Bridle (+15 speed)"
        });
        add(new JLabel("Bridle:"));
        add(bridleBox);
    }
}