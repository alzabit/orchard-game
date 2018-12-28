package orchardEmulate;
// class to simulate the running of the manual game playing
public class RunPlaySimulate {
		
	WriteLog     wLog			= new WriteLog();
	int			 numberOfGames  = 100;
	PlaySimulate playSimulate	;	
	ReadLog		 readLog		= new ReadLog(); 
	public static void main(String[] args) {
		new RunPlaySimulate().run();
	}
	
	public void run() {
		boolean bresult;		
		String  result;
		
		for (int forLoop=0; forLoop <= numberOfGames; forLoop++){
		    playSimulate = new PlaySimulate();
			bresult 	 = playSimulate.simulateRun();	
			
			if (bresult) {
				result="Win";
			}else {
				result="Lost";
			}
			
			wLog.run(this.playSimulate, "GameNo", Integer.toString(forLoop),
					                    "GameResult", result, "Instance");			
		}// end of for loop
		
		// write to file
		wLog.writeTheContentIntoXmlFile(wLog.getDoc(), playSimulate);
		readLog.run();
		
	} // end of run
} // end of RunPlaySimulate
