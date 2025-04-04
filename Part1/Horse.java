
/**
 * Write a description of class Horse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Horse
{
    //Fields of class Horse
    private String name;          
    private char symbol;          
    private int distanceTravelled; 
    private boolean hasFallen;    
    private double confidence;

    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.distanceTravelled = 0;
        this.hasFallen = false;
        setConfidence(horseConfidence); // Ensure valid confidence level
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        hasFallen = true;
    }
    
    public double getConfidence()
    {
        return confidence;
    }
    
    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }
    
    public String getName()
    {
        return name;
    }
    
    public char getSymbol()
    {
        return symbol;
    }
    
    public void goBackToStart()
    {
        distanceTravelled = 0;
        hasFallen = false;
    }
    
    public boolean hasFallen()
    {
        return hasFallen;
    }

    public void moveForward()
    {
        distanceTravelled += 1;
    }

    public void setConfidence(double newConfidence)
    {
        confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
       symbol = newSymbol; 
    }
    
}
