import java.util.*;

/**
 * HorseStats class tracks the performance statistics of a horse,
 * including races, wins, falls, best and worst times, average speed,
 * and average confidence.
 */
public class HorseStats {

    // Attributes to track horse statistics
    private int races;                // Total number of races
    private int wins;                 // Total number of wins
    private int falls;                // Total number of falls
    private double bestTime = Integer.MAX_VALUE; // Best race time (lower is better)
    private double worstTime = 0;     // Worst race time (higher is worse)
    private double totalTime;         // Total time across all valid races
    private double totalConfidence;   // Total confidence across all races
    private double trackLength;       // Length of the track for the race

    /**
     * Records the results of a race for the horse.
     *
     * @param won Whether the horse won the race.
     * @param fell Whether the horse fell during the race.
     * @param time The time taken to complete the race.
     * @param confidence The confidence level of the horse during the race.
     * @param trackLength The length of the track for the race.
     */
    public void recordRace(boolean won, boolean fell, double time, double confidence, double trackLength) {
        races++;
        if (won) wins++;
        if (fell) falls++;
        if (!fell) {
            this.bestTime = Math.min(bestTime, time); // Update best time if this race is better
            this.worstTime = Math.max(worstTime, time); // Update worst time if this race is worse
            this.totalTime += time; // Accumulate total time for valid races
            this.trackLength = trackLength; // Store the track length
        }
        totalConfidence += confidence; // Accumulate total confidence
    }

    // Getters for various statistics

    /**
     * @return The total number of races.
     */
    public int getRaces() { return races; }

    /**
     * @return The total number of wins.
     */
    public int getWins() { return wins; }

    /**
     * @return The total number of falls.
     */
    public int getFalls() { return falls; }

    /**
     * @return The best race time, or -1 if no valid races have been recorded.
     */
    public double getBestTime() { return bestTime == Integer.MAX_VALUE ? -1 : bestTime; }

    /**
     * @return The worst race time.
     */
    public double getWorstTime() { return worstTime; }

    /**
     * Calculates the average speed of the horse across all valid races.
     *
     * @return The average speed, or 0 if no valid races have been recorded.
     */
    public double getAvgSpeed() {
        return totalTime > 0 ? trackLength / totalTime : 0;
    }

    /**
     * Calculates the average confidence of the horse across all races.
     *
     * @return The average confidence, or 0 if no races have been recorded.
     */
    public double getAvgConfidence() {
        return races > 0 ? totalConfidence / races : 0;
    }

    /**
     * Calculates the win ratio of the horse.
     *
     * @return The win ratio as a decimal (e.g., 0.5 for 50%), or 0 if no races have been recorded.
     */
    public double getWinRatio() {
        return races > 0 ? (double) wins / races : 0;
    }
}