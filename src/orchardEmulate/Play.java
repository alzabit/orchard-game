package orchardEmulate;


import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Random;

public class Play {
	
	private static  final Integer 			NumberOfApples   		= 10;
	private static  final Integer 			NumberOfPears    		= 10 ;
	private static  final Integer 			NumberOfCheeries 		= 10 ;
	private static  final Integer 			NumberOfPlums    		= 10 ;
	private static  final Integer 			NumberOfRavenJigsaw 	= 9 ;	
	private static  final Integer 			BasketSelectTimes 		= 2 ;  // number of basket select time

	private 			  Dice  			dice  					= new Dice();
	private 			  Player			player					= new Player();
			
	private				  Dice.DiceFaces 	dface 					= null;
	private 			  Play.Fruits       playerSelection         = null;
	
	public static enum    Fruits {APPLES, PEARS, CHERRIES, PLUMS};	
		
	Hashtable<Integer,Player>  		basket     = new Hashtable<Integer,Player>();
	LinkedHashMap<String, Integer> 	gameRecord = new LinkedHashMap<String, Integer> ();
	LinkedHashMap<Integer, Play.Fruits>  basketSelectList = new LinkedHashMap<Integer, Play.Fruits> ();
	
	Play(){
		// set game recorder
		this.setGameRecord();
		this.setBasketSelectList();
	}
	
	
	/**
	 * 
	 * @param numberOfPlayers
	 * @param scan
	 * ==============================================================
	 */
	 public void startGame(OrchardGame orchardGame, boolean automate ){			 
							
		System.out.println("Starting game register is:");
		this.displayGameRecord();
		System.out.println("Push the ENTER key to start the game " +"\n");
		if (automate) {
			orchardGame.getInput();
		}
		
	  }// end startGame()
	 
	  /**
	   * 
	   * @param orchardGame
	   * @param automate
	   * ====================================================
	   */
	  public boolean playGame(OrchardGame orchardGame, boolean automate){
		// While game is running go from one user to the next
		while (! isGameOver() ){	
			for (int forVar=0 ; (! isGameOver()) && (forVar < orchardGame.numberOfPlayers); forVar++) {
				try {
					System.out.println("=============================================");
					System.out.print  ("Player "+ (forVar+1) );
					System.out.println(" Push the ENTER key to throw dice ");
					System.out.println("=============================================");
					
					if (automate) {
						orchardGame.resetScan();					
					}
					
					orchardGame.getInput();				// click on enter to throw dice
					
					this.setDface( dice.rollDice() );  	// roll dice to get random selection
					
					if (this.getDface() != Dice.DiceFaces.BASKET) {
						updateGameRecord (this.getDface());
						displayGameRecord();	
						
					} else {						
						System.out.println("Pick "+ Play.BasketSelectTimes +" fruits from availble fruit on tree");
						System.out.println(" Enter (1) APPLE, (2) PEARS, (3) CHERRIES, (4) PLUMS");						
									
						for (forVar=0; (! isGameOver()) && (forVar < Play.BasketSelectTimes); forVar++) {
							
							System.out.println("Basket selection " + (forVar+1) +" :");	
							Play.Fruits fruit;
							
							if (automate) {  // if this is a program go throw else get user input
								fruit = this.getAutomateRandomSelection();
								
							}else {								
								fruit= player.basketPlayerSelection(orchardGame.getScan()) ;	// get user input of fruit			
							}
							
							this.setPlayerSelection(fruit);
						    boolean selectStatus = this.updateGameRecord( this.getPlayerSelection() );

						    if (! selectStatus) {
						    	
						    	System.out.println("**********************************************");
						    	System.out.println ("*** The fruit selected is not available on the tree ***");
						    	System.out.println  ("please select another fruit");
						    	System.out.println("**********************************************");
						    	
						    	forVar--;  // reduce number of fruits to pick
						    }
						    
							System.out.println("Player selected : "+ this.getPlayerSelection() );
							
							displayGameRecord();
						}// end for loop							
					} // end else
						
				}catch (NoSuchElementException e1) {
					System.err.println("Play.playGame-> Input exception, \n only numbers accepted \n error message: "+e1.getMessage());
				}

			}// end of for loop
		}//end while
		
		if (isRavenTheWinner() ) {
			
			System.out.println("**************************************************");
			System.out.println("** Sorry team Raven won the game this time. **");
			System.out.println("**************************************************");
			return (false);
		}else {
			System.out.println("**************************************************");
			System.out.println("****  Gongratulation team you have won. **********");
			System.out.println("**************************************************");
			return (true);
		}

	}// end of playGame
	
	  /**
	   * 
	   * @return
	   * Description: 
	   */
	private Play.Fruits getAutomateRandomSelection() {
		int 		listSize = this.basketSelectList.size();
		Random 	 	random 			= new Random();

	    int 		fruitSelected	= random.nextInt(listSize);
	    Play.Fruits fruit=null;
	    
	   fruit = (Fruits) this.basketSelectList.get(
	    	  (this.basketSelectList.keySet().toArray())[fruitSelected]);
   
	  return (fruit);
	}// end of getAutomateRandomSelection


	/**
	 * 
	 * @param dfaces
	 * 
	 * Description: update game record based on dice face.
	 * ==========================================================
	 */
	private boolean updateGameRecord(Dice.DiceFaces dfaces){			
		boolean isValueUpdated = false;
		
		switch (dfaces) {
		case RED:
			
			Integer registerNumberOfCHEERIES = this.getGameRecord().get("CHEERIES");
			
			if (registerNumberOfCHEERIES>0) {
				registerNumberOfCHEERIES-- ;
				this.gameRecord.replace("CHEERIES", registerNumberOfCHEERIES);	
				if (registerNumberOfCHEERIES == 0) {
					this.getBasketSelectList().remove(3);
				}
				isValueUpdated = true; 
			}
			
			break;
			
		case YELLOW:
			Integer registerNumberOfPEARS = this.getGameRecord().get("PEARS");			
			
			if (registerNumberOfPEARS>0) {
				registerNumberOfPEARS-- ;
				this.gameRecord.replace("PEARS", registerNumberOfPEARS);
				if (registerNumberOfPEARS == 0) {
					this.getBasketSelectList().remove(2);
				}
				isValueUpdated = true; 
			}
						
			break;
			
		case BLUE:

			Integer registerNumberOfPLUMS = this.getGameRecord().get("PLUMS");
			
			if (registerNumberOfPLUMS>0) {
				registerNumberOfPLUMS-- ;
				this.gameRecord.replace("PLUMS", registerNumberOfPLUMS);
				if (registerNumberOfPLUMS == 0) {
					this.getBasketSelectList().remove(4);
				}
				isValueUpdated = true; 
			}
			
			break;
			
		case GREEN:

			Integer registerNumberOfAPPLES = this.getGameRecord().get("APPLES");
			
			if (registerNumberOfAPPLES>0) {
				registerNumberOfAPPLES-- ;
				this.gameRecord.replace("APPLES", registerNumberOfAPPLES);	
				if (registerNumberOfAPPLES == 0) {
					this.getBasketSelectList().remove(1);
				}
				isValueUpdated = true; 
			}
						
			break;
			
		case RAVEN:
			Integer registerNumberOfJIGSAWPARTS = this.getGameRecord().get("JIGSAWPARTS");

			
			if (registerNumberOfJIGSAWPARTS>0) {
				registerNumberOfJIGSAWPARTS-- ;
				this.gameRecord.replace("JIGSAWPARTS", registerNumberOfJIGSAWPARTS);
				isValueUpdated = true; 
			}
						
			break;
		case BASKET:
			break;
		default:
			break;
		} //end switch
		
		return (isValueUpdated);
	}//end of updateGameRecord
	
	/**
	 * =======================================================
	 * @param fruit
	 * 
	 * Description: update game record based on fruit type.
	 * =======================================================
	 */
	private boolean updateGameRecord(Play.Fruits fruit){
		
		boolean isValueUpdated = false;
		
		switch (fruit) {
		case APPLES:
			
			Integer registerNumberOfAPPLES = this.getGameRecord().get("APPLES");
	
			// decrement number of apples on tree
			if (registerNumberOfAPPLES>0) {
				registerNumberOfAPPLES-- ;
				this.gameRecord.replace("APPLES", registerNumberOfAPPLES);	
				if (registerNumberOfAPPLES == 0) {
					this.getBasketSelectList().remove(1);
				}
				isValueUpdated = true; 
			}					
			break;		
		case PEARS:
			
			Integer registerNumberOfPEARS = this.getGameRecord().get("PEARS");			
	
			// decrement number of pears on tree
			if (registerNumberOfPEARS>0) {
				registerNumberOfPEARS-- ;
				this.gameRecord.replace("PEARS", registerNumberOfPEARS);	
				
				if (registerNumberOfPEARS == 0) {
					this.getBasketSelectList().remove(2);
				}
				isValueUpdated = true; 
				
			}			
			break;
		case CHERRIES:
			
			Integer registerNumberOfCHEERIES = this.getGameRecord().get("CHEERIES");
			
			// decrement number of CHEERIE on tree
			if (registerNumberOfCHEERIES>0) {
				registerNumberOfCHEERIES-- ;
				this.gameRecord.replace("CHEERIES", registerNumberOfCHEERIES);	
				if (registerNumberOfCHEERIES == 0) {
					this.getBasketSelectList().remove(3);
				}
				isValueUpdated = true; 
			}					
			break;		
		case PLUMS:
	
			Integer registerNumberOfPLUMS = this.getGameRecord().get("PLUMS");
	
			// decrement number of plums on tree
			if (registerNumberOfPLUMS>0) {
				registerNumberOfPLUMS-- ;
				this.gameRecord.replace("PLUMS", registerNumberOfPLUMS);
				if (registerNumberOfPLUMS == 0) {
					this.getBasketSelectList().remove(4);
				}
				isValueUpdated = true; 
			}			
			break;
			
		} // end of switch
		
		return (isValueUpdated);
	}//end of updateGameRecord		

	
	/**
	 * 
	 * @return gameRecord
	 * Description: return game record/register when called
	 * ===========================================================
	 */
	public LinkedHashMap <String, Integer> getGameRecord() {
		return gameRecord;
	}

	/**
	 * 
	 * Description: set initial value of hash table
	 * =====================================================
	 */
	public void setGameRecord() {

		this.gameRecord.put("APPLES", 		Play.NumberOfApples);
		this.gameRecord.put("PEARS", 		Play.NumberOfPears);
		this.gameRecord.put("CHEERIES", 	Play.NumberOfCheeries);
		this.gameRecord.put("PLUMS", 		Play.NumberOfPlums);		
		this.gameRecord.put("JIGSAWPARTS", 	Play.NumberOfRavenJigsaw);		
	}
	
	/**
	 * description: display game record register when called
	 * ========================================================
	 */
	public void displayGameRecord() {
		System.out.print("Current game register -> ");
		for (String key : this.gameRecord.keySet()) {			
            System.out.print(key + ": " + this.gameRecord.get(key)+" .\t ");			
		}
		System.out.println("\n");
	}
	
	/**
	 * 
	 * @return true if the jigsaw or fruits count is zero.
	 * ======================================================
	 */
	public boolean isGameOver() {
		boolean gameOver = false;
		if ( this.gameRecord.get("JIGSAWPARTS") != null && this.gameRecord.get("JIGSAWPARTS") <= 0 ) {
			gameOver = true ;
		}else {
			if ( ( this.gameRecord.get("APPLES") != null && this.gameRecord.get("APPLES"  ) <= 0  ) &&
				 ( this.gameRecord.get("PEARS") != null && this.gameRecord.get("PEARS"   ) <= 0  ) &&
				 ( this.gameRecord.get("CHEERIES") != null && this.gameRecord.get("CHEERIES") <= 0  ) &&
				 ( this.gameRecord.get("PLUMS") != null && this.gameRecord.get("PLUMS"   ) <= 0  )   
			   ) {				
				gameOver = true;
			} 			
		}
		
		return (gameOver);
	}// end is game over
	
	/**
	 * 
	 * @return true if jigsawparts is picked.
	 * Description: if the count is zero the status will be true
	 * ===========================================================
	 */
	private boolean isRavenTheWinner() {
		boolean ravenWon = false;
		
		if ( this.gameRecord.get("JIGSAWPARTS") <= 0 ) {
			ravenWon = true ;
		}		
		return (ravenWon);
	}
	
	public Dice.DiceFaces getDface() {
		return dface;
	}

	public void setDface(Dice.DiceFaces dface) {
		this.dface = dface;
	}
	
	public Play.Fruits getPlayerSelection() {
		return playerSelection;
	}

	public void setPlayerSelection(Play.Fruits playerSelection) {
		this.playerSelection = playerSelection;
	}
	
	private void setBasketSelectList() {
		this.basketSelectList.put(1, Play.Fruits.APPLES);
		this.basketSelectList.put(2, Play.Fruits.PEARS);
		this.basketSelectList.put(3, Play.Fruits.CHERRIES);
		this.basketSelectList.put(4, Play.Fruits.PLUMS);
	}
	
	public LinkedHashMap<Integer, Play.Fruits> getBasketSelectList() {
		return basketSelectList;
	}
	
} // end of Play
