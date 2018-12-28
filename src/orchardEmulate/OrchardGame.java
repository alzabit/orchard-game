package orchardEmulate;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author fga
 * Summery: game of max. 4 players
 *
 */
public class OrchardGame {
	
	// enter number of player if more than 4 prompt user again
	
	public   		Integer numberOfPlayers ;
    private         Scanner scan=null ;
    private static 	Integer NumberOfBaskets  	= 4 ;
    private  final  InputStream systemIn = System.in;	
	private 		ByteArrayInputStream  programIn=null;   // for automated input
	
	
	public static void main(String args[]) {		
		new OrchardGame().run();	
	} // end of main
	
	
	// constructor
	public OrchardGame() {
		
	} // end of constructor

	public void run() {		
		System.out.println("Enter number of players, the youngest will start: ");
		
		if (scan == null ) {
			setScan(this.systemIn);
		}
		
		 // toDo: verify user input is a digit.
		 String userInput = getInput().trim();
		 this.setNumberOfPlayers(Integer.parseInt(userInput));
				 
		while ( (numberOfPlayers <= 0) || (numberOfPlayers > OrchardGame.NumberOfBaskets) ) {
			System.out.println("Only "+ OrchardGame.NumberOfBaskets +" players are allowed, Number of players entered: "+ numberOfPlayers+" \n");
            System.out.println("Please try again");
            
			try {
				userInput = getInput();
				this.setNumberOfPlayers(Integer.parseInt(userInput));
            }catch (InputMismatchException e) {
            	System.err.println("Input mismatch exception,");
            	System.err.println(" only numbers accepted");
            }	
			
		}// end of while
						
		Play play = new Play ();
	  
		boolean automate= false; // this will be false with player interaction, true if a program is calling
		play.startGame(this, automate);
	    play.playGame(this , automate );	
	    
	    finalize();
	}
	
	public Integer getNumberOfPlayers() {
		return numberOfPlayers;
	}


	public void setNumberOfPlayers(Integer numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}


	public  Scanner getScan() {
		return this.scan;
	}
	
	public String getInput() {
        return this.scan.nextLine();
    }
	
	/**
	 * Description InputStream is used for direct player input
	 * @param inStream
	 */
	public void setScan(InputStream inStream){		
		this.scan = new Scanner (inStream);					
	}
	
	/**
	 * Description:  ByteArrayInputStream used for automated input
	 * @param inStream
	 */
	public void setScan(ByteArrayInputStream inStream){		
		this.programIn=  (ByteArrayInputStream) inStream;		
		this.scan = new Scanner (programIn);		
	}
	
	public void resetScan() {
		this.programIn.reset();			
	}
	
	
	
	public void finalize (){
		this.scan.close();
	}
}  // End of OrchardGame



