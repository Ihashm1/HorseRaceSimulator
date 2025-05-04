/**
 * HorseConfig class represents the configuration for a horse,
 * including its name, symbol, speed bonus, confidence bonus,
 * coat colour, and breed.
 */
public class HorseConfig {

    // Attributes of the horse
    public String name;               // Name of the horse
    public String symbol;             // Symbol representing the horse
    public int speedBonus;            // Speed bonus for the horse
    public double confidenceBonus;    // Confidence bonus for the horse
    public String coatColor;          // Coat colour of the horse
    public String breed;              // Breed of the horse

    /**
     * Constructor to initialise the horse configuration.
     *
     * @param name The name of the horse.
     * @param symbol The symbol representing the horse.
     * @param speedBonus The speed bonus for the horse.
     * @param confidenceBonus The confidence bonus for the horse.
     * @param coatColor The coat colour of the horse.
     * @param breed The breed of the horse.
     */
    public HorseConfig(String name, String symbol, int speedBonus, double confidenceBonus, String coatColor, String breed) {
        this.name = name;
        this.symbol = symbol;
        this.speedBonus = speedBonus;
        this.confidenceBonus = confidenceBonus;
        this.coatColor = coatColor;
        this.breed = breed;
    }
}



