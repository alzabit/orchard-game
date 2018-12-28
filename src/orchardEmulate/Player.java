package orchardEmulate;

import java.util.Scanner;

public class Player {	
	
	int player=0;
	private boolean 	validEntry=true;
	private Play.Fruits returnPlayerSelection ;
	
	/**
	 * 
	 * @param scan
	 * @return returnPlayerSelection
	 * 
	 * Description: player will pick number of fruits as per play rules
	 */
	public Play.Fruits basketPlayerSelection(Scanner scan) {
		
		String playerInput= scan.next();
		
		while  ( validEntry) {
			if (playerInput.equals("1") || playerInput.equals("2") ||
				playerInput.equals("3") || playerInput.equals("4"))   {
				validEntry=false;			
			}else {
				System.out.println("Not valid input, player selectd: "+playerInput +"\n");
				System.out.print("Valid values is number 1 to " + Play.Fruits.values().length );
				System.out.println(" try again:");
				
				playerInput= scan.next();
				
				validEntry=true;
			}
		}// end of while loop
		
		switch (playerInput) {		
		case ("1"):			
			returnPlayerSelection = Play.Fruits.APPLES ;
			break;
		case ("2"):		
			returnPlayerSelection = Play.Fruits.CHERRIES;
			break;
		case ("3"):			
			returnPlayerSelection = Play.Fruits.PEARS;
			break;
		case ("4"):			
			returnPlayerSelection =  Play.Fruits.PLUMS;
			break;			
		} // end of switch		
	
		return (returnPlayerSelection);
	}// end of basketPlayerSelection
	

} // end of Player class
