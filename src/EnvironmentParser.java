import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class EnvironmentParser {
	private double gravitydirection;
	private double gravitymagnitude;
	private double viscosityscalevalue;
	private double centermassmagnitude;
	private double centermassexponent;
	
	 
	protected static Node getNode(String tagName, NodeList nodes) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            return node;
	        }
	    }
	 
	    return null;
	}
	 
	protected String getNodeValue( Node node ) {
	    NodeList childNodes = node.getChildNodes();
	    for (int x = 0; x < childNodes.getLength(); x++ ) {
	        Node data = childNodes.item(x);
	        if ( data.getNodeType() == Node.TEXT_NODE )
	            return data.getNodeValue();
	    }
	    return "";
	}
	 
	protected String getNodeValue(String tagName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.TEXT_NODE )
	                    return data.getNodeValue();
	            }
	        }
	    }
	    return "";
	}
	 
	protected static String getNodeAttr(String attrName, Node node ) {
	    NamedNodeMap attrs = node.getAttributes();
	    for (int y = 0; y < attrs.getLength(); y++ ) {
	        Node attr = attrs.item(y);
	        if (attr.getNodeName().equalsIgnoreCase(attrName)) {
	            return attr.getNodeValue();
	        }
	    }
	    return "";
	}
	 
	protected String getNodeAttr(String tagName, String attrName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.ATTRIBUTE_NODE ) {
	                    if ( data.getNodeName().equalsIgnoreCase(attrName) )
	                        return data.getNodeValue();
	                }
	            }
	        }
	    }
	 
	    return "";
	}
	
	
	public void parse(String filename) {
		
		
		try {
			
			
			DOMParser parser = new DOMParser();
			parser.parse(filename);
			Document doc = parser.getDocument();
			
			
			System.out.println("root of xml file ->" + doc.getDocumentElement().getNodeName());
			ArrayList<String> environobjects = new ArrayList<String>();
				environobjects.add("gravity");
				environobjects.add("viscosity");
				environobjects.add("centermass");
				environobjects.add("wall");
				
			NodeList root = doc.getChildNodes();
			
			Node environment = getNode("environment", root);
			Node gravity = getNode("gravity", environment.getChildNodes());
			gravitydirection = Double.parseDouble(getNodeAttr("direction", gravity));
			gravitymagnitude = Double.parseDouble(getNodeAttr("magnitude", gravity));
			
			Node viscosity = getNode("viscosity", environment.getChildNodes());
			viscosityscalevalue = Double.parseDouble(getNodeAttr("magnitude", viscosity));
			
			Node centermass = getNode("centermass", environment.getChildNodes());
			centermassmagnitude = Double.parseDouble(getNodeAttr("magnitude", centermass));
			centermassexponent = Double.parseDouble(getNodeAttr("exponent" , centermass));
			
			for (int i = 1; i <5; i++) {
				
				Node wall = getNode("wall", environment.getChildNodes());
				double wallid = Double.parseDouble(getNodeAttr("id", wall));
				double wallmagnitude = Double.parseDouble(getNodeAttr("magnitude", wall));
				double wallexponent = Double.parseDouble(getNodeAttr("exponent", wall)); 
				System.out.println(wallid  +" " +  wallmagnitude  +" " +  wallexponent);
						
			}
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			System.out.println("Gravity: " +"direction= " + gravitydirection + " magnitude= " + gravitymagnitude);
			System.out.println("Viscosity: " + "scale value= " + viscosityscalevalue);
			System.out.println("Center Mass: " + "magnitude= " + centermassmagnitude + "exponent = " + centermassexponent );
			
		}
	
	
	public static void main(String args[]) {
		EnvironmentParser temp = new EnvironmentParser();
		temp.parse("assets/environment.xml");
		
		
	}
	
	
	

}
