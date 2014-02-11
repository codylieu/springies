package springies;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.Node;

import jgame.*;
import jgame.platform.*;
import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;

import org.jbox2d.common.Vec2;

import com.sun.tools.javac.util.List;

import Connectors.Spring;
import Forces.CenterOfMass;
import Forces.Gravity;
import Forces.WorldManager;
import PhysicalObjects.Assembly;
import PhysicalObjects.PhysicalObject;
import PhysicalObjects.PhysicalObjectCircle;
import PhysicalObjects.PhysicalObjectFixedMass;
import PhysicalObjects.PhysicalObjectMass;
import PhysicalObjects.PhysicalObjectRect;
import PhysicalObjects.Wall;
import PhysicalObjects.WalledArea;
import parserutil.EnvironmentParser;
import parserutil.ObjectsParser;


@SuppressWarnings("serial")
public class Springies extends JGEngine

{
	private double [] gravityvals;
	private double viscositymagnitude;
	private double [] centermass;
	private double[][] wallvals;
	private PhysicalObjectMass m1;
	private PhysicalObjectMass m2;
	private PhysicalObjectMass m3;
	public Spring temp;
	public Spring temp2;
	public Spring temp3;
	private WalledArea walls;
	private ArrayList<Assembly> assemblies;

	static final double WALL_MARGIN = 10;
	static final double WALL_THICKNESS = 10;
	private Gravity g;


	public Springies ()
	{
		// set the window size
		int height = 480;
		double aspect = 16.0 / 9.0;
		initEngineComponent((int) (height * aspect), height);
		System.out.println("Display Width = " + displayWidth() + "Display Height = " + displayHeight());
	}

	@Override
	public void initCanvas ()
	{
		// I have no idea what tiles do...
		setCanvasSettings(1, // width of the canvas in tiles
				1, // height of the canvas in tiles
				displayWidth(), // width of one tile
				displayHeight(), // height of one tile
				null,// foreground colour -> use default colour white
				null,// background colour -> use default colour black
				null); // standard font -> use default font
	}

	@Override
	public void initGame ()
	{
		setFrameRate(60, 2);
		// NOTE:
		//   world coordinates have y pointing down
		//   game coordinates have y pointing up
		// so gravity is up in world coords and down in game coords
		// so set all directions (e.g., forces, velocities) in world coords
		getEnvironment("assets/environment.xml");
		System.out.println("VISCOSITY MAGNITUDE: " + viscositymagnitude);
		WorldManager.initWorld(this);
		//		WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.1f));
		
		assemblies = new ArrayList<Assembly>();
		addBall();
		addWalls();
		createPhysicalElements("assets/ball.xml");
		
	}

	public void addBall ()
	{
		// add a bouncy ball
		// NOTE: you could make this into a separate class, but I'm lazy
		
		m1 = new PhysicalObjectMass("ball", 1, JGColor.red, 10, 5, displayWidth()/2, displayHeight()/2,0,0);
		m2 = new PhysicalObjectMass("ball2", 1, JGColor.yellow, 10, 5, displayWidth()/2-100, displayHeight()/2-100, 0,0);
		m3 = new PhysicalObjectMass("ball3", 1, JGColor.blue, 10, 5, displayWidth()/2-200, displayHeight()/2, 0, 0);

		temp = new Spring("spring", 0, JGColor.pink);
		temp2 = new Spring("spring", 0, JGColor.magenta);
		temp3 = new Spring("spring", 0, JGColor.orange);
		
		ArrayList<Spring> tempSprings = new ArrayList<Spring>();
		ArrayList<PhysicalObjectMass> tempMasses = new ArrayList<PhysicalObjectMass>();
		tempSprings.add(temp);
		tempSprings.add(temp2);
		tempSprings.add(temp3);
		tempMasses.add(m1);
		tempMasses.add(m2);
		tempMasses.add(m3);
		

		temp.connect(m1, m2, 4, 20);
		temp2.connect(m1, m3, 4, 20);
		temp3.connect(m2, m3, 4, 20);
		Assembly tempAssembly = new Assembly(tempMasses, tempSprings);
		assemblies.add(tempAssembly);
		
		

	}

	private void addWalls ()
	{
		// add walls to bounce off of
		// NOTE: immovable objects must have no mass

		final double WALL_WIDTH = displayWidth() - WALL_MARGIN * 2 + WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2 + WALL_THICKNESS;
		walls = new WalledArea("walled area", 10, JGColor.yellow);

		Wall topwall = new Wall("wall", 2, JGColor.green,
				WALL_WIDTH, WALL_THICKNESS, "top");
		topwall.setPos(displayWidth() / 2, WALL_MARGIN);

		System.out.println("TOP WALL IS AT " + displayWidth()/2 + "," +  WALL_MARGIN);

		Wall bottomwall = new Wall("wall", 2, JGColor.green,
				WALL_WIDTH, WALL_THICKNESS, "bottom");

		bottomwall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);

		double bottomwallx = displayHeight() - WALL_MARGIN;

		System.out.println("BOTTOM WALL IS AT " + displayWidth()/2 + "," + bottomwallx);

		Wall leftwall = new Wall("wall", 2, JGColor.green,
				WALL_THICKNESS, WALL_HEIGHT, "left");
		leftwall.setPos(WALL_MARGIN, displayHeight() / 2);

		System.out.println("LEFT WALL IS AT " + WALL_MARGIN + "," + displayHeight());

		Wall rightwall = new Wall("wall", 2, JGColor.green,
				WALL_THICKNESS, WALL_HEIGHT, "right");
		rightwall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);

		double rightwallx = displayWidth() - WALL_MARGIN; 
		System.out.println("RIGHT WALL IS AT " + rightwallx + "," + displayHeight()/2);
		walls.setWalls(topwall, leftwall, rightwall, bottomwall);
	}

	public HashMap<String, PhysicalObjectMass> createMasses(String[][] masses) {

		HashMap<String, PhysicalObjectMass> allmasses = new HashMap<String, PhysicalObjectMass>();
		for (int i = 0; i< masses.length; i++) {
			String[] currmass = masses[i];
			String id = currmass[0];
			int collisionId = 1;
			JGColor color = JGColor.green;
			double radius = 5;

			double x = Double.parseDouble(currmass[1]);
			double y = Double.parseDouble(currmass[2]);
			double mass = Double.parseDouble(currmass[3]);
			double vx = Double.parseDouble(currmass[4]);
			double vy = Double.parseDouble(currmass[5]);
			System.out.println("creatednewmass");
			PhysicalObjectMass newmass = new PhysicalObjectMass(id, collisionId, color, radius, mass, x, y, vx, vy);
			//						newmass.setGravity(mass, gravityvals[0], gravityvals[1]);
			
			allmasses.put(id, newmass);

		}

		//		CenterOfMass com = new CenterOfMass("com", 5, JGColor.green);
		//		com.setCOMForce(allmasses);
		System.out.println(allmasses.toString());
		return allmasses; 

	}

	public double[] averageLocation(HashMap<String, PhysicalObjectMass> allmasses) {
		double[] location = new double[2];

		double totalx = 0;
		double totaly = 0 ;


		for (Map.Entry entry : allmasses.entrySet()) {
			PhysicalObjectMass currmass = (PhysicalObjectMass) entry.getValue();
			totalx += currmass.myX;
			totaly += currmass.myY;
			System.out.print("key,val: ");
			System.out.println(entry.getKey() + "," + entry.getValue());
		}
		location[0] = totalx;
		location[1] = totaly; 
		return location; 


	}



	public ArrayList<Spring> createSprings(String[][] springs, HashMap<String, PhysicalObjectMass> allmasses) {
		ArrayList<Spring> allSprings = new ArrayList<Spring>();
		
		for (int i = 0; i< springs.length; i++) {
			String[] currspring = springs[i];
			Spring spring = new Spring("spring", 1, JGColor.yellow);
			
			
			PhysicalObjectMass mass1 = allmasses.get(currspring[0]); 
			PhysicalObjectMass mass2 = allmasses.get(currspring[1]);

			System.out.println("connected " + currspring[0] + " " +currspring[1]);
			double k = Double.parseDouble(currspring[3]);
			double restLength = Double.parseDouble(currspring[2]);
			spring.connect(mass1, mass2, k, restLength );
			spring.applyForce();
			
			allSprings.add(spring);
		}
		
		return allSprings;
		
	}

	public void createPhysicalElements(String filename) {

		ObjectsParser elements = new ObjectsParser();
		//Node doc = elements.parse("assets/ball.xml");
		System.out.println(filename);
		Node doc = elements.parse(filename);
		System.out.println(doc.toString());

		String[][] masses = elements.createMasses(doc);
		HashMap<String, PhysicalObjectMass> allmasses = createMasses(masses);
		
		System.out.println(masses[0][0] + "TEST");

		//String [][] fmasses = elements.createFixedMasses(doc);
		//createFMasses(fmasses);
		String [][]springs = elements.createSprings(doc);
		ArrayList<Spring> springList = createSprings(springs, allmasses);
		ArrayList<PhysicalObjectMass> massList = new ArrayList<PhysicalObjectMass>(allmasses.values());
		
		Assembly assembly= new Assembly(massList, springList);
		assemblies.add(assembly);
		
		//String [][] muscles = elements.createMuscles(doc);
		//createMuscles(muscles);
	}

	public void getEnvironment(String filename) {

		EnvironmentParser environment = new EnvironmentParser();
		Node environ = environment.parse(filename);
		gravityvals = environment.getGravity(environ);
		viscositymagnitude = environment.getViscosity(environ);
		centermass = environment.getCenterMass(environ);
		wallvals = environment.getWalls(environ);

		g = new Gravity(gravityvals[0], gravityvals[1]);
		g.setAssembliesList(assemblies);
	
	}


	private String userSelects() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"XML Files", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(getParent());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File chosenFile = chooser.getSelectedFile();
			String pathofFile = chosenFile.getAbsolutePath();

			System.out.println("You chose to open this file: " + pathofFile);

			return pathofFile;
		}
		return "";

	}



	boolean GRAVITY = false;
	boolean VISCOSITY = false;
	boolean CENTER_OF_MASS = false;

	@Override
	public void doFrame ()
	{
		// update game objects
		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(2, 1);
		temp.applyForce();
		temp2.applyForce();
		temp3.applyForce();
		

		if (getKey(KeyUp)) {
			walls.increaseArea();

		}

		if (getKey(KeyDown)) {
			walls.reduceArea();

		}
		if (getKey('N')) {
			String chosenFile= userSelects();
			createPhysicalElements(chosenFile);
		}

		if(getKey('G')){
			FAKE_GRAVITY = !FAKE_GRAVITY;
			clearKey('G');
		}
		if(getKey('V')){
			VISCOSITY = !VISCOSITY;
			clearKey('V');
		}
		if(getKey('M')){
			CENTER_OF_MASS = !CENTER_OF_MASS;
			clearKey('M');
		}
		// Just to play around with what gravity would do
		if(FAKE_GRAVITY){
			/*m1.setForce(0, 500);
			m2.setForce(0, 500);
			m3.setForce(0, 500);*/
			g.setAssembliesList(assemblies);
			g.applyForce();
			
		}
	}


	@Override
	public void paintFrame (){
		// nothing to do
		// the objects paint themselves
		drawString("Gravity ('g'): " + GRAVITY, 100, 20, 0, null, JGColor.white);
		drawString("Viscosity ('v'): " + VISCOSITY, 110, 40, 0, null, JGColor.white);
		drawString("Center of Mass ('m'): " + CENTER_OF_MASS, 140, 60, 0, null, JGColor.white);
		if(getKey(KeyUp)){
			drawString("Wall Expanding (up): true", 145, 80, 0, null, JGColor.white);
		}
		else{
			drawString("Wall Expanding (up): false", 145, 80, 0, null, JGColor.white);
		}
		if(getKey(KeyDown)){
			drawString("Wall Shrinking (down): true", 150, 100, 0, null, JGColor.white);
		}
		else{
			drawString("Wall Shrinking (down): false", 150, 100, 0, null, JGColor.white);
		}


	}
}
