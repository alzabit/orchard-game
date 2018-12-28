package orchardEmulate;

import java.util.Random;

/**
 * 
 * @author fga
 * Description: dice object of six faces.
 */

public class Dice{
	
	public static enum    DiceFaces {RED, YELLOW, BLUE, GREEN, BASKET, RAVEN};
	
	/**
	 * role dice: through dice and return value
	 * 
	 * Description: roll dice and return face/side selected.
	 * ======================================================================
	 */
	public DiceFaces rollDice() {
		
		final int    	 DiceNumberOfFace 	= 6;
		      Random 	 random 			= new Random();
		      DiceFaces  returnDice			= null;		
		      int 		 diceFace			= random.nextInt(DiceNumberOfFace)+1;	
		
		switch (diceFace) {		
			case (1):
				System.out.println("Dice face is: "+ DiceFaces.GREEN  +" <APPLES>\n");
				returnDice =  DiceFaces.GREEN;
				break;
			case (2):
				System.out.println("Dice face is: "+ DiceFaces.YELLOW +" <PEARS>\n");
			    returnDice = DiceFaces.YELLOW;
				break;
			case (3):
				System.out.println("Dice face is: "+ DiceFaces.RED +" <CHEERIES>\n"); 
				returnDice = DiceFaces.RED ;
				break;			
			case (4):
				System.out.println("Dice face is: "+ DiceFaces.BLUE +" <PLUMS>\n");
				returnDice = DiceFaces.BLUE;
				break;			
			case (5):
				System.out.println("Dice face is: "+ DiceFaces.BASKET +"\n");
				returnDice = DiceFaces.BASKET;
				break;
			case (6):
				System.out.println("Dice face is: "+ DiceFaces.RAVEN +"\n");
				returnDice = DiceFaces.RAVEN;
				break;
			
		} // end of switch		
		
		return (returnDice);
	}

}
