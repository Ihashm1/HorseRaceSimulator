public class HorseConfig {
    public String name;
    public String symbol;
    public int speedBonus;
    public double confidenceBonus;

    public HorseConfig(String name, String symbol, int speedBonus, double confidenceBonus) {
        this.name = name;
        this.symbol = symbol;
        this.speedBonus = speedBonus;
        this.confidenceBonus = confidenceBonus;
    }
}