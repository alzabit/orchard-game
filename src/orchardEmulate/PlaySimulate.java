/**
 * 
 */
package orchardEmulate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author fga
 *
 */
public class PlaySimulate {

	 
		final private  String resultDir 		= "c:\\orchardEmulateResults";
		final private  String resultFileName    = resultDir+"\\gameResules.xml";
		final private  String enterReturnKey  	= "\"\"\n";
		final private  int 	  numberOfPlayers   = 4 ;
		
		private  final InputStream systemIn 	= System.in;
	    private  final PrintStream systemOut 	= System.out;

	    private ByteArrayInputStream  autoIn;
	    private ByteArrayOutputStream byteOutputStream;
	    
	    private OrchardGame orchardGame			= new OrchardGame();
	    private Play		play	   			= new Play();
	    
	    PlaySimulate(){
			//this.simulateRun();
		}
	    
	    public static void main(String args[]) {		
			new PlaySimulate().simulateRun();	
		} // end of main
	    
	    public void setUpOutput() {
	        byteOutputStream = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(byteOutputStream));
	    }	
		
		public void provideInput(String data) {
		        autoIn = new ByteArrayInputStream(data.getBytes());
		        if (autoIn.markSupported()) {
		        	autoIn.mark(data.length() );
		        }		        	
		        System.setIn(autoIn);       
		 }

		 public void restoreSystemInputOutput() {
		        System.setIn(systemIn);
		        System.setOut(systemOut);
		 }
		     
		 public boolean simulateRun() {	  
			       boolean  teamWon;
			 final boolean  automate 		= true;	
			 
			 this.provideInput(this.enterReturnKey);	
			 
			  this.orchardGame.setScan(this.autoIn); 
			  this.orchardGame.setNumberOfPlayers(this.numberOfPlayers);
			  			  	
			  play.startGame(orchardGame, automate);
			  teamWon = play.playGame (orchardGame,  automate);			  	        		        
		      restoreSystemInputOutput();
		      return (teamWon);
		   }
		 
		   public  String getResultdir() {
				return resultDir;
			}

			public  String getResultfilename() {
				return resultFileName;
			}
    
}
