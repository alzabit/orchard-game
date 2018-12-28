package orchardEmulate;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ReadLog {
	
	private DocumentBuilderFactory 	docFactory ;
	private DocumentBuilder 	   	docBuilder;
	private PlaySimulate			playSimulate ;
	private Document				doc	;
	double winPercentage=0;
	double lostPercentage=0;
	

	/**
	 * pass file and directory string to file object
	 */
	void readLog() {
		try {
			File fXmlFile 	= new File( this.getPlaySimulate().getResultfilename() );	
			this.setDocFactory(docFactory);
			this.setDocBuilder(docBuilder);
			
			Document doc 			= docBuilder.parse(fXmlFile);				
			int 	totalWin		= 0;
			int 	totalLost		= 0;
			double 	totalNoOfGames	= 0;
			
			doc.getDocumentElement().normalize();	
			NodeList list = doc.getElementsByTagName("Instance");
			
			totalNoOfGames = (list.getLength()-1);
			
			for (int forLoop=0; forLoop < list.getLength(); forLoop++) {
				
				Node node = list.item(forLoop);				
				if (node.getNodeType() == Node.ELEMENT_NODE) {					
					Element 	eElement 		= (Element) node;								
					NodeList 	instanceList 	= eElement.getElementsByTagName("GameResult");					
					Element 	gameNoElement 	= (Element)instanceList.item(0);					
					NodeList 	textFNList1 	= gameNoElement.getChildNodes();					
					String	 	gameResult 		= ((Node)textFNList1.item(0)).getNodeValue().trim();

					if (gameResult.equals("Win")) {
						totalWin++;
					}
					if (gameResult.equals("Lost")) {
						totalLost++;
					}	
				}
			}
			
			winPercentage  = (totalWin  / (totalNoOfGames)) * 100 ;
			lostPercentage = (totalLost / (totalNoOfGames)) * 100 ;
			
			System.out.println("total number of games " + totalNoOfGames );
			System.out.println("Number of Win games: "+ totalWin);
			System.out.println("Number of Lost games: "+ totalLost);
			
			System.out.println("total win: "  + this.winPercentage + " Percent");			
			System.out.println("total Lost: " + this.lostPercentage + " Percent");
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		//fXmlFile.get
	}  // end readLog
    
	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}
	
	public DocumentBuilderFactory getDocFactory() {
		return docFactory;
	}
	public void setDocFactory(DocumentBuilderFactory docFactory) {
		this.docFactory = docFactory;		
		this.docFactory = DocumentBuilderFactory.newInstance();
	}
	public DocumentBuilder getDocBuilder() {
		return docBuilder;
	}
	public void setDocBuilder(DocumentBuilder docBuilder) {
		this.docBuilder = docBuilder;		
		try {
			this.docBuilder = docFactory.newDocumentBuilder();			
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		}
	}
	public PlaySimulate getPlaySimulate() {
		return playSimulate;
	}
	public void setPlaySimulate(PlaySimulate playSimulate) {
		this.playSimulate = playSimulate;	
		this.playSimulate = new PlaySimulate();
		
	}
	
	public static void main(String[] args) {
		ReadLog readLog = new ReadLog();
		readLog.run();
	}

	public void run() {
		
		this.setPlaySimulate(playSimulate);		
		this.readLog();
	}
}
