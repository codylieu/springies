package parserutil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;


public class ObjectsParser extends Parser{

	public String[][] createMasses(Node model) {
//		final ArrayList<String[]> massvalues = new ArrayList<String[]>();
		Node nodes = getNode("nodes", model.getChildNodes());

		ArrayList<Node> masses = getAllNodes("mass", nodes.getChildNodes());
		String[][] massvalues = new String[masses.size()][6];
		for (int i = 0; i <masses.size(); i++) {
			

			String id = getNodeAttr("id", masses.get(i));
			double x = Double.parseDouble(getNodeAttr("x", masses.get(i)));
			double y = Double.parseDouble(getNodeAttr("y", masses.get(i)))-200;
			double mass = 1;
			if (getNodeAttr("mass", masses.get(i)) != "")
				mass = Double.parseDouble(getNodeAttr("mass", masses.get(i)));

			double vx = 0;
			double vy = 0;
			if (getNodeAttr("vx", masses.get(i)) != "") 
				vx = Double.parseDouble(getNodeAttr("vx", masses.get(i)));

			if (getNodeAttr("vy", masses.get(i)) != "") 	
				vy = Double.parseDouble(getNodeAttr("vy", masses.get(i)));
			
			String[] massattributes = {id, Double.toString(x), Double.toString(y), Double.toString(mass), Double.toString(vx), Double.toString(vy)};

//			massvalues.add(massattributes);
			
			massvalues[i] = massattributes;
			
			//PhysicalObjectMass mass = new PhysicalObjectMass(id, x, y, z, vx, vy) 
			System.out.println(id +" " +  x  +" " +  y + " " + vx + " " + vy);
			System.out.println(massvalues[0][0] + "Test");
		}
		
		return massvalues;



	}

	public String[][] createSprings(Node model) {
		Node links = getNode("links", model.getChildNodes());
		
		
		ArrayList<Node> springs = getAllNodes("spring", links.getChildNodes());
		String[][] springvalues = new String[springs.size()][4];
		for (int i = 0; i < springs.size(); i++) {


			String a = getNodeAttr("a", springs.get(i));
			String b = getNodeAttr("b", springs.get(i));
			double restlength = 50.0;
			if (getNodeAttr("restlength", springs.get(i)) != "")
				restlength = Double.parseDouble(getNodeAttr("restlength", springs.get(i))); 
			double k = 0;
			if (getNodeAttr("constant", springs.get(i)) != "") 
				k = Double.parseDouble(getNodeAttr("constant", springs.get(i)));


			String[] springattributes = {a, b, Double.toString(restlength), Double.toString(k)};
			springvalues[i] = springattributes;
			//PhysicalObjectSpring spring = new PhysicalObjectSpring(a, b, restlength, k) 
			System.out.println(a  +" " +  b +" " +  restlength  + " " + k);
		}
		
		return springvalues;
			
	}

	public String[][]  createFixedMasses(Node model) {
		Node nodes = getNode("nodes", model.getChildNodes());
		
		ArrayList<Node> fixeds = getAllNodes("spring", nodes.getChildNodes());
		String[][] fmvalues = new String[fixeds.size()][3];
		for (int i = 0; i < fixeds.size(); i++) {


			String id = getNodeAttr("id", fixeds.get(i));
			double x = Double.parseDouble(getNodeAttr("x", fixeds.get(i)));
			double y = Double.parseDouble(getNodeAttr("y", fixeds.get(i))); 
			//PhysicalObjectFixedMass fixed = new PhysicalObjectFixedMass(id, x, y) 
			String[] fmattributes = {Double.toString(x), Double.toString(y)};
			fmvalues[i] = fmattributes;
			System.out.println(id  +" " +  x  +" " +  y);
		}
		
		return fmvalues;

	}

	public String[][] createMuscles(Node model) {
		Node links = getNode("links", model.getChildNodes());
		

		ArrayList<Node> muscles = getAllNodes("spring", links.getChildNodes());
		String [][] musclevalues = new String[muscles.size()][4];
		for (int i = 0; i < muscles.size(); i++) {


			String a = getNodeAttr("a", muscles.get(i));
			String b = getNodeAttr("b", muscles.get(i));
			double restlength = Double.parseDouble(getNodeAttr("restlength", muscles.get(i))); 
			double k = 0;
			if (getNodeAttr("constant", muscles.get(i)) != "") 
				k = Double.parseDouble(getNodeAttr("constant", muscles.get(i)));
			
			String[] springattributes = {a, b, Double.toString(restlength), Double.toString(k)};
			musclevalues[i] = springattributes;

			//PhysicalObjectSpring spring = new PhysicalObjectSpring(a, b, restlength, k) 
			System.out.println(a  +" " +  b +" " +  restlength  + " " + k);
		}
		return musclevalues;
	}

	public void createNodes(Node model) {
		Node nodes = getNode("nodes", model.getChildNodes());
		createMasses(nodes);
		createFixedMasses(nodes);
		
	}
	public void createLinks(Node model) {
		Node links = getNode("links", model.getChildNodes());
		createMuscles(links);
		createSprings(links);

	}

	public Node parse(String filename) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(filename);
			Document doc = parser.getDocument();

			System.out.println("root of xml file ->" + doc.getDocumentElement().getNodeName());

			NodeList root = doc.getChildNodes();
			Node model = getNode("model", root);

//			Node nodes = getNode("nodes", model.getChildNodes());
//			createMasses(model);
//			createFixedMasses(model);
//
//			Node links = getNode("links", model.getChildNodes());
//			createMuscles(model);
//			createSprings(model);
//			
			return model;

		} catch (Exception ex) {

			ex.printStackTrace();

		}		
		return null;
		

	}


	public static void main(String args[]) {
		ObjectsParser temp = new ObjectsParser();
		temp.parse("assets/example.xml");


	}

}

