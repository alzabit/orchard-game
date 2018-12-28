package orchardEmulate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.*;
import org.w3c.dom.*;


public class WriteLog {	
	
	DocumentBuilderFactory 	docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder 	   	docBuilder;
	
	private Document 		doc ;
	private Element 		root ;
	private String 			rootElementName="OrchardGame" ;
	private File 		   	directory ;	
	private FileWriter      logFile ;
	
	WriteLog(){
		// root elements
		this.setDoc();			
		this.setRoot(this.getRootElementName());
	}
	
	/**
	 * 
	 * @param playSimulate
	 * ********************************************************************************
	 */
	public void run (PlaySimulate playSimulate) {		
		// root elements
		this.setDoc();			
		this.setRoot(this.getRootElementName());				
		this.addElementToRootChild("GameNo", "1", "GameResult","Win", "Instance");				
	}	
	
	//*********************************************************************************
	public void run (PlaySimulate playSimulate, String game, String gameNumber, 
			                 String gameResult, String result, String instance) {		
		// root elements			
		this.addElementToRootChild(game, gameNumber, gameResult, result, instance);				
	}	
	
	/**
	 * 
	 * @param instanceName
	 * @param gameNo
	 * @param number
	 * @param gameResult
	 * @param result
	 * @param recordTitle
	 * ***********************************************************************************
	 */
	public void addRecordToRoot(String instanceName, 
								String  gameNo, 	String number, 
								String gameResult,  String  result, String recordTitle) {		
		
	    this.addElementToRootChild(gameNo, number, gameResult, result, recordTitle);
	}
	
	/**
	 * 
	 * @param doc
	 * @param playSimulate
	 * *************************************************************************************
	 */
	public void writeTheContentIntoXmlFile(Document doc, PlaySimulate playSimulate) {
		this.doc= doc;
		try {
			TransformerFactory 	transformerFactory 	= TransformerFactory.newInstance();			
			Transformer transformer;
			
			transformer = transformerFactory.newTransformer();								
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		                     
			DOMSource   		source 				= new DOMSource(this.doc);			
			
			this.setDirectory(playSimulate);
			this.setLogFile  (playSimulate);
			
			StreamResult result = new StreamResult(this.getLogFile());
			
			transformer.transform(source, result);
			this.getLogFile().flush();
			this.getLogFile().close();

			System.out.println("File saved!");
			
		}catch (TransformerConfigurationException e) {
			System.err.println(	e.getClass().getName() );
			e.printStackTrace();
		}catch (TransformerException e) {
			System.err.println(	e.getClass().getName() );
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} // end of writeTheContentIntoXmlFile
	
	
	
	/**
	 * 
	 * @return
	 * *******************************************************
	 */
	public File getDirectory() {
		return directory;
	} // end of getDirectory

	/**
	 * 
	 * @param playSimulate
	 * *******************************************************
	 */
	public void setDirectory(PlaySimulate playSimulate) {		
		directory = new File (playSimulate.getResultdir() );
		
		if (! directory.exists() ){
			directory.mkdir();
		}
	}// end of setDirectory	
	
	/**
	 * ********************************************************
	 */
	public FileWriter getLogFile() {
		return logFile;
	}

	/**
	 * 
	 * @param playSimulate
	 * ********************************************************
	 */
	public void setLogFile(PlaySimulate playSimulate ) {		
		try {
			if ( directory.exists() && directory.isDirectory()) {				
				this.logFile = new FileWriter (playSimulate.getResultfilename() );
		    } 
		}catch (IOException e) {
			System.err.println(	e.getClass().getName() );
			e.printStackTrace();
		}
	
	}// end of setLogFile
	

	/**
	 * 
	 * @param doc
	 * @param rootElementName
	 * ***************************************************************
	 */
	public void setDoc() {		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();			
			this.docBuilder 	 = docFactory.newDocumentBuilder();			
			this.doc 			 = docBuilder.newDocument();				
		} catch (ParserConfigurationException e) {
			System.err.println(e.getClass().getName() );
			e.printStackTrace();
		}

	} // end  setDocRoot
	
	/**
	 * 
	 * @param rootElementName
	 * ***********************************************************************
	 */
	public void setRoot(String rootElementName) {	
			try{
				Element rootElement  = this.doc.createElement(rootElementName);	
				doc.appendChild(rootElement);
				this.root = rootElement ;
			}
			catch(DOMException de) {
				de.printStackTrace();
			}
	} // end  setDocRoot
	
	/**
	 * *******************************************************
	 */
			
	public Element getRoot(){
		return (this.root);
	}
	
	
	/**
	 * 
	 * @param elementName
	 * ******************************************************
	 */
    public void addElementToRootChild(String gameNo, 	 String number, 
    								  String gameResult, String result, 
    								  String parentElement){
    	
    	Element pElement = this.doc.createElement(parentElement);
    	Element element;
    	
		element = this.doc.createElement(gameNo);
		element.appendChild(doc.createTextNode(number));	
		pElement.appendChild(element);
		
		element = this.doc.createElement(gameResult);
		element.appendChild(doc.createTextNode(result));
		pElement.appendChild(element);
		
		this.getRoot().appendChild(pElement);
		
		
	} //addElementToRoot
	
    /**
     * 
     * @return main document object
     * *********************************************************
     */
	public Document getDoc() {
		return this.doc;
	}// end getDoc
	
	/**
	 * 
	 * @return root element
	 * **********************************************************
	 */
	public String getRootElementName() {
		return (this.rootElementName);
	}//getRootElementName
	
	
}
