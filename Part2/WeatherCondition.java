/**
 * WeatherCondition class represents different weather conditions
 * that affect the race. Each condition has a name, a speed multiplier,
 * and a base confidence level for the horses.
 */
public class WeatherCondition {

    // Attributes of the weather condition
    private final String name;               // Name of the weather condition
    private final double speedMultiplier;    // Multiplier for horse speed
    private final double baseConfidence;     // Base confidence level for horses

    /**
     * Constructor to initialise a weather condition.
     *
     * @param name The name of the weather condition.
     * @param speedMultiplier The multiplier applied to horse speed.
     * @param baseConfidence The base confidence level for horses.
     */
    public WeatherCondition(String name, double speedMultiplier, double baseConfidence) {
        this.name = name;
        this.speedMultiplier = speedMultiplier;
        this.baseConfidence = baseConfidence;
    }

    /**
     * @return The name of the weather condition.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The speed multiplier for the weather condition.
     */
    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    /**
     * @return The base confidence level for the weather condition.
     */
    public double getBaseConfidence() {
        return baseConfidence;
    }

    /**
     * Converts the weather condition to a string representation.
     *
     * @return The name of the weather condition.
     */
    @Override
    public String toString() {
        return name;
    }

    // Predefined weather types
    public static final WeatherCondition DRY = new WeatherCondition("Dry", 1.0, 0.6);   // Dry weather: normal speed and confidence
    public static final WeatherCondition MUDDY = new WeatherCondition("Muddy", 0.7, 0.5); // Muddy weather: slower speed, moderate confidence
    public static final WeatherCondition ICY = new WeatherCondition("Icy", 1.2, 0.4);   // Icy weather: faster speed, lower confidence

    // Array of all predefined weather conditions
    public static final WeatherCondition[] CONDITIONS = { DRY, MUDDY, ICY };
}