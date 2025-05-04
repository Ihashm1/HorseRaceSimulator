public class WeatherCondition {
    private final String name;
    private final double speedMultiplier;
    private final double baseConfidence;

    public WeatherCondition(String name, double speedMultiplier, double baseConfidence) {
        this.name = name;
        this.speedMultiplier = speedMultiplier;
        this.baseConfidence = baseConfidence;
    }

    public String getName() {
        return name;
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public double getBaseConfidence() {
        return baseConfidence;
    }

    @Override
    public String toString() {
        return name;
    }

    // Predefined weather types
    public static final WeatherCondition DRY = new WeatherCondition("Dry", 1.0, 0.6);
    public static final WeatherCondition MUDDY = new WeatherCondition("Muddy", 0.7, 0.5);
    public static final WeatherCondition ICY = new WeatherCondition("Icy", 1.2, 0.4);

    public static final WeatherCondition[] CONDITIONS = { DRY, MUDDY, ICY };
}