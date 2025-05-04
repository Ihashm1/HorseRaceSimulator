import java.util.*;

public class HorseStats {
    private int races;
    private int wins;
    private int falls;
    private double bestTime = Integer.MAX_VALUE;
    private double worstTime = 0;
    private double totalTime;
    private double totalConfidence;
    private double trackLength;

    public void recordRace(boolean won, boolean fell, double time, double confidence, double trackLength) {
        races++;
        if (won) wins++;
        if (fell) falls++;
        if (!fell) {
            this.bestTime = Math.min(bestTime, time);
            this.worstTime = Math.max(worstTime, time);
            this.totalTime += time;
            this.trackLength = trackLength;
        }
        totalConfidence += confidence;
    }

    public int getRaces() { return races; }
    public int getWins() { return wins; }
    public int getFalls() { return falls; }
    public double getBestTime() { return bestTime == Integer.MAX_VALUE ? -1 : bestTime; }
    public double getWorstTime() { return worstTime; }
    public double getAvgSpeed() {
        return totalTime > 0 ? trackLength / totalTime : 0;
    }
    public double getAvgConfidence() {
        return races > 0 ? totalConfidence / races : 0;
    }
    public double getWinRatio() {
        return races > 0 ? (double) wins / races : 0;
    }
}