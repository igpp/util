package igpp.util;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.FactoryConfigurationError;  
import javax.xml.parsers.ParserConfigurationException;
 
import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;  

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.DOMException;

/**
 * Load an options file represented in XML and provide access to its elements.
 *
 * @author Todd King
 * @version 1.00 05/09/14
 */
public class Options {
    // Global value so it can be ref'd by the tree-adapter
    static Document mDocument = null; 
	public static final String mVersion= "0.0.1";
	
    public static void main(String argv[])
    {
    	Options me = new Options();
    	
		System.out.println("Version: " + me.mVersion);

        if (argv.length != 1) {
            System.err.println("Usage: java " + me.getClass().getName() + " filename");
            System.exit(1);
        }

		Options opt = new Options();
		
		opt.loadXML(argv[0]);
		opt.dump();
	}
	
	boolean loadXML(String pathname)
	{
        DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();
        //factory.setValidating(true);   
        //factory.setNamespaceAware(true);
        try {
           DocumentBuilder builder = factory.newDocumentBuilder();
           mDocument = builder.parse( new File(pathname) );
 
        } catch (SAXException sxe) { // Error generated during parsing
           Exception  x = sxe;
           if (sxe.getException() != null)
               x = sxe.getException();
           x.printStackTrace();

        } catch (ParserConfigurationException pce) { // Parser with specified options can't be built
            pce.printStackTrace();

        } catch (IOException ioe) { // I/O error
           ioe.printStackTrace();
        }
        
        return true;
    }
    
    void dump()
    {
    	if(mDocument == null) {
    		System.out.println("No document parsed.");
    		return;		
    	}
    	
    	dumpNode(mDocument);	
    }
    
    void dumpNode(Node node)
    {
    	Node		n;
    	NodeList	list;
    	String		nodeName;
    	
    	nodeName = node.getNodeName();
    	// if(nodeName.compareTo("#text") == 0) System.out.println(node.getNodeValue());
    	// else System.out.print(node.getNodeName() + ": " + node.getNodeValue());
    	if(node.getNodeType() == Node.ELEMENT_NODE) {
    		System.out.println("OBJECT = " + node.getNodeName());
    	} else {
    		System.out.println(node.getNodeValue());
    	}
    	
    	// while((n = node.getNextSibling()) != null) dumpNode(n);
    	
    	list = node.getChildNodes();
    	for(int i = 0; i < list.getLength(); i++) {
    		dumpNode(list.item(i));
    	}
    	
    	if(node.getNodeType() == Node.ELEMENT_NODE) {
	    	System.out.println("END_OBJECT = " + node.getNodeName());
	    }
	}
	
  /* ------------------------------------------------------------ */
  	public static java.util.Properties loadProperties(String propName)
  		throws Exception
  	{
  		return loadProperties(null, propName);
  	}
  	
  /* ------------------------------------------------------------ */
  	public static java.util.Properties  loadProperties(String home, String propName)
  		throws Exception
  	{
  		if(home == null) home = System.getenv("IGPP_HOME");
  		if(home == null) home = "/opt/igpp"; 
  		// Check if path exists - if not use current directory + "/conf";
  		// Works well for servlets since current directory is root
  		File file = new File(home);
  		if(!file.exists()) {
  			file = new File(".");
  			home = file.getCanonicalPath() + "/WEB-INF/conf";
  		}
  		String path = home + System.getProperty("file.separator");
  		
		java.util.Properties props = new java.util.Properties();
		FileInputStream stream = new FileInputStream(path + propName);
		if(stream == null) {
			throw new Exception("Unable to open resource: " + propName);
		}
		props.load(stream);
		
		return props;
  	}
}
