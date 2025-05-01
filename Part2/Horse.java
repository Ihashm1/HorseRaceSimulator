
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
        if (newConfidence < 0) {
            confidence = 0;
        } else if (newConfidence > 1) {
            confidence = 1;
        } else {
            confidence = newConfidence;
        }
    }
    
    public void setSymbol(char newSymbol)
    {
       symbol = newSymbol; 
    }

    /**
     * Main method to test the Horse class functionality.
     */
    public static void main(String[] args) {
        
        // Test constructor and getter methods
        Horse h1 = new Horse('♘', "PIPPI LONGSTOCKING", 0.8);


        // Test getName and getSymbol
        System.out.println("Horse: " + h1.getName() + " Symbol: " + h1.getSymbol());
        
        // Test getConfidence
        System.out.println("Confidence: " + h1.getConfidence());
        // Test getDistanceTravelled 
        System.out.println("Distance: " + h1.getDistanceTravelled());
        
        // Test hasFallen 
        System.out.println("Has fallen? " + h1.hasFallen());

        // Test fall method and then check with hasFallen method
        h1.fall();
        System.out.println("Has fallen? " + h1.hasFallen());

        // Move forward and test distance
        h1.moveForward();
        System.out.println("Distance after moving forward: " + h1.getDistanceTravelled());

        // Test goBackToStart
        h1.goBackToStart();
        System.out.println("Distance after reset: " + h1.getDistanceTravelled());
        System.out.println("Has fallen after reset? " + h1.hasFallen());

        //Test setConfidence boundaries 
        h1.setConfidence(1.2);
        System.out.println("Confidence: " + h1.getConfidence());
        h1.setConfidence(-0.8);
        System.out.println("Confidence: " + h1.getConfidence());
        h1.setConfidence(0.5);
        System.out.println("Confidence: " + h1.getConfidence());

        //Test setSymbol method
        h1.setSymbol('♜');
        System.out.println("New Symbol: " + h1.getSymbol());
    }
    
}
