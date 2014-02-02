package parserutil;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class EnvironmentParser extends Parser {
	private double gravitydirection;
	private double gravitymagnitude;
	private double viscosityscalevalue;
	private double centermassmagnitude;
	private double centermassexponent;


	public void parse(String filename) {


		try {


			DOMParser parser = new DOMParser();
			parser.parse(filename);
			Document doc = parser.getDocument();


			System.out.println("root of xml file ->" + doc.getDocumentElement().getNodeName());
			
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
			
			ArrayList<Node> walls = getAllNodes("wall", environment.getChildNodes());
			for (int i = 0; i <walls.size(); i++) {

				
				double wallid = Double.parseDouble(getNodeAttr("id", walls.get(i)));
				double wallmagnitude = Double.parseDouble(getNodeAttr("magnitude", walls.get(i)));
				double wallexponent = Double.parseDouble(getNodeAttr("exponent", walls.get(i))); 
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
