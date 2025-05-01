import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McRaceface
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private int laneCount;
    private Horse[] lanes;
    

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, int laneCount)
    {
        this.raceLength = distance;
        this.laneCount = laneCount;
        this.lanes = new Horse[laneCount];
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber >= 1 && laneNumber <= laneCount)
        {
            lanes[laneNumber - 1] = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (Horse horse : lanes) {
        if (horse != null) {
            horse.goBackToStart();
        }
        }
                      
        while (!finished)
        {
            //move each horse
            for (Horse horse : lanes) {
           moveHorse(horse);
            }
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            for (Horse currenthorse : lanes) {
            if (raceWonBy(currenthorse)) {
                finished = true;
                break;
            }

            // Check if all horses have fallen
            boolean allFallen = true;
            for (Horse horse : lanes) {
                if (horse != null && !horse.hasFallen()) {
                    allFallen = false;
                    break;
                }
            }
            if (allFallen) {
                finished = true;
                printRace(); // update screen before ending
                System.out.println("\nAll horses have fallen. No winner.");
                return;
            }

            
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }

        String[] winners = new String[laneCount];
        int winnerCount = 0;

        for (Horse horse : lanes) {
            if (raceWonBy(horse)) {
                winners[winnerCount] = horse.getName();
                winnerCount++;
            }
        }

        if (winnerCount > 1) {
            System.out.print("\nIt's a tie between: ");
            for (int i = 0; i < winnerCount; i++) {
                System.out.print(winners[i].toUpperCase());
                if (i < winnerCount - 1) System.out.print(" and ");
            }
            System.out.println("!");
        } else if (winnerCount == 1) {
            System.out.println("\nAnd the winner is… " + winners[0].toUpperCase() + "!");
        }
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
    if (theHorse == null) {
        return; // skip if no horse in this lane
    }

    if (!theHorse.hasFallen()) {
        if (Math.random() < theHorse.getConfidence()) {
            theHorse.moveForward();
        }

        if (Math.random() < (0.1 * theHorse.getConfidence() * theHorse.getConfidence())) {
            theHorse.fall();
        }
    }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse == null) return false; // avoid crash
        return theHorse.getDistanceTravelled() == raceLength;
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        for (Horse h : lanes) {
        if (h != null) {
            printLane(h);
            System.out.println();
        }
        
     }
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u2322');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');

         // Print the horse's name and confidence on the side
        System.out.print(" " + theHorse.getName().toUpperCase() + " (Current confidence " + theHorse.getConfidence() + ")");
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    public static void main(String[] args) {
    
    // Race 1: Standard 3 Horses
    Race race1 = new Race(20);
    Horse h1 = new Horse('B', "Bolt", 0.8);
    Horse h2 = new Horse('F', "Flash", 0.6);
    Horse h3 = new Horse('S', "Storm", 0.7);
    race1.addHorse(h1, 1);
    race1.addHorse(h2, 2);
    race1.addHorse(h3, 3);
    race1.startRace();

    // Race 2: Empty and Extra Lanes
    Race race2 = new Race(20);
    race2.addHorse(h1, 1); // valid
    race2.addHorse(h2, 2); // valid
    race2.addHorse(h1, 4); // same horse reused
    // lane 3 and 5 left empty
    race2.startRace();

        
      }
}
