import javax.swing.*;
import java.awt.*;

public class CustomisationPanel extends JPanel {

    public JComboBox<String> breedBox;
    public JComboBox<String> coatBox;
    public JComboBox<String> symbolBox;
    public JComboBox<String> saddleBox;
    public JComboBox<String> horseshoeBox;
    public JComboBox<String> bridleBox;

    public CustomisationPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Horse Customisation"));

        // Breed
        breedBox = new JComboBox<>(new String[] {
            "Thoroughbred (+5 speed, -1 confidence)", "Quarter Horse (+5 confidence, -1 confidence)", "Arabian (+3 speed, +2 confidence)", "French Trotter (+3 confidence, + 2 speed)", "Shetland Pony (+4 confidence)"
        });
        add(new JLabel("Breed:"));
        add(breedBox);

        // Coat Colour
        coatBox = new JComboBox<>(new String[] {
            "Black", "White", "Brown", "Grey", "pink"
        });
        add(new JLabel("Coat Colour:"));
        add(coatBox);

        // Symbol
        symbolBox = new JComboBox<>(new String[] {
            "🦄", "🐎", "🐴", "♘", "♞"
        });
        add(new JLabel("Symbol:"));
        add(symbolBox);

        // Saddle
        saddleBox = new JComboBox<>(new String[] {
            "None", "Saddle (+15 confidence)"
        });
        add(new JLabel("Saddle:"));
        add(saddleBox);

        // Horseshoe
        horseshoeBox = new JComboBox<>(new String[] {
            "None", "Horseshoe (+5 speed, +10 confidence)"
        });
        add(new JLabel("Horseshoe:"));
        add(horseshoeBox);

        // Bridle
        bridleBox = new JComboBox<>(new String[] {
            "None", "Bridle (+15 speed)"
        });
        add(new JLabel("Bridle:"));
        add(bridleBox);
    }

}