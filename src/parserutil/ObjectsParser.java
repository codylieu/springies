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


public class ObjectsParser extends Parser{

	public void createMasses(Node environment) {

		ArrayList<Node> masses = getAllNodes("mass", environment.getChildNodes());
		for (int i = 0; i <masses.size(); i++) {


			String id = getNodeAttr("id", masses.get(i));
			double x = Double.parseDouble(getNodeAttr("x", masses.get(i)));
			double y = Double.parseDouble(getNodeAttr("y", masses.get(i))); 

			double vx = 0;
			double vy = 0;
			if (getNodeAttr("vx", masses.get(i)) != "") 
				vx = Double.parseDouble(getNodeAttr("vx", masses.get(i)));


			if (getNodeAttr("vy", masses.get(i)) != "") 	
				vy = Double.parseDouble(getNodeAttr("vy", masses.get(i)));


			//PhysicalObjectMass mass = new PhysicalObjectMass(id, x, y, z, vx, vy) 
			System.out.println(id  +" " +  x  +" " +  y + " " + vx + " " + vy);

		}



	}

	public void createSprings(Node environment) {
		//In the data file, this element is indicated by the keyword spring followed by the id of its two masses, a rest length, an
		//d a K springy-ness constant. If K is not given, the default value should be 1.
		ArrayList<Node> springs = getAllNodes("spring", environment.getChildNodes());
		for (int i = 0; i < springs.size(); i++) {


			String a = getNodeAttr("a", springs.get(i));
			String b = getNodeAttr("b", springs.get(i));
			double restlength = Double.parseDouble(getNodeAttr("restlength", springs.get(i))); 
			double k = 0;
			if (getNodeAttr("constant", springs.get(i)) != "") 
				k = Double.parseDouble(getNodeAttr("constant", springs.get(i)));



			//PhysicalObjectSpring spring = new PhysicalObjectSpring(a, b, restlength, k) 
			System.out.println(a  +" " +  b +" " +  restlength  + " " + k);
		}
	}

	public void createFixedMasses(Node environment) {
		ArrayList<Node> fixeds = getAllNodes("spring", environment.getChildNodes());
		for (int i = 0; i < fixeds.size(); i++) {


			String id = getNodeAttr("id", fixeds.get(i));
			double x = Double.parseDouble(getNodeAttr("x", fixeds.get(i)));
			double y = Double.parseDouble(getNodeAttr("y", fixeds.get(i))); 
			//PhysicalObjectFixedMass fixed = new PhysicalObjectFixedMass(id, x, y) 
			System.out.println(id  +" " +  x  +" " +  y);
		}

	}

	public void createMuscles(Node environment) {

		ArrayList<Node> muscles = getAllNodes("spring", environment.getChildNodes());
		for (int i = 0; i < muscles.size(); i++) {


			String a = getNodeAttr("a", muscles.get(i));
			String b = getNodeAttr("b", muscles.get(i));
			double restlength = Double.parseDouble(getNodeAttr("restlength", muscles.get(i))); 
			double k = 0;
			if (getNodeAttr("constant", muscles.get(i)) != "") 
				k = Double.parseDouble(getNodeAttr("constant", muscles.get(i)));



			//PhysicalObjectSpring spring = new PhysicalObjectSpring(a, b, restlength, k) 
			System.out.println(a  +" " +  b +" " +  restlength  + " " + k);
		}
	}



	public void parse(String filename) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(filename);
			Document doc = parser.getDocument();

			

			System.out.println("root of xml file ->" + doc.getDocumentElement().getNodeName());


			//get masses, create them
			//get springs, create them
			//get muscles, create them

			NodeList root = doc.getChildNodes();
			Node model = getNode("model", root);

			Node nodes = getNode("nodes", model.getChildNodes());
			createMasses(nodes);
			createFixedMasses(nodes);
			
			
			Node links = getNode("links", model.getChildNodes());
			createMuscles(links);
			createSprings(links);




		} catch (Exception ex) {

			ex.printStackTrace();

		}		




	}


	public static void main(String args[]) {
		ObjectsParser temp = new ObjectsParser();
		temp.parse("assets/example.xml");


	}

}

