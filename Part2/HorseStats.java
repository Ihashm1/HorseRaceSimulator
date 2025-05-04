import java.util.*;

public class HorseStats {
    private int races;
    private int wins;
    private int falls;
    private int bestTime = Integer.MAX_VALUE;
    private int worstTime = 0;
    private int totalTime;
    private double totalConfidence;

    public void recordRace(boolean won, boolean fell, int time, double confidence) {
        races++;
        if (won) wins++;
        if (fell) falls++;
        if (!fell) {
            bestTime = Math.min(bestTime, time);
            worstTime = Math.max(worstTime, time);
            totalTime += time;
        }
        totalConfidence += confidence;
    }

    public int getRaces() { return races; }
    public int getWins() { return wins; }
    public int getFalls() { return falls; }
    public int getBestTime() { return bestTime == Integer.MAX_VALUE ? -1 : bestTime; }
    public int getWorstTime() { return worstTime; }
    public double getAvgSpeed() {
        int validRaces = races - falls;
        return validRaces > 0 ? (double) totalTime / validRaces : 0;
    }
    public double getAvgConfidence() {
        return races > 0 ? totalConfidence / races : 0;
    }
    public double getWinRatio() {
        return races > 0 ? (double) wins / races : 0;
    }
}