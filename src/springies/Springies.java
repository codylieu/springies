package springies;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.Node;

import jgame.JGColor;
import jgame.platform.JGEngine;

import Connectors.Spring;
import Forces.CenterOfMass;
import Forces.Gravity;
import Forces.Viscosity;
import Forces.WorldManager;
import PhysicalObjects.Assembly;
import PhysicalObjects.PhysicalObjectMass;
import PhysicalObjects.Wall;
import PhysicalObjects.WalledArea;
import parserutil.EnvironmentParser;
import parserutil.ObjectsParser;

@SuppressWarnings("serial")
public class Springies extends JGEngine

{
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
	private Viscosity v;
	private CenterOfMass com;

	public Springies ()
	{
		// set the window size
		int height = 480;
		double aspect = 16.0 / 9.0;
		initEngineComponent((int) (height * aspect), height);
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
		WorldManager.initWorld(this);
		//		WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.1f));
		
		assemblies = new ArrayList<Assembly>();
//		addBall();
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

		
		Wall bottomwall = new Wall("wall", 2, JGColor.green,
				WALL_WIDTH, WALL_THICKNESS, "bottom");

		bottomwall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);

		Wall leftwall = new Wall("wall", 2, JGColor.green,
				WALL_THICKNESS, WALL_HEIGHT, "left");
		leftwall.setPos(WALL_MARGIN, displayHeight() / 2);

	
		Wall rightwall = new Wall("wall", 2, JGColor.green,
				WALL_THICKNESS, WALL_HEIGHT, "right");
		rightwall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);

	
		walls.setWalls(topwall, leftwall, rightwall, bottomwall);
	}


	private HashMap<String, PhysicalObjectMass> implementMasses(String[][] masses) {

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
			
			PhysicalObjectMass newmass = new PhysicalObjectMass(id, collisionId, color, radius, mass, x, y, vx, vy);
			allmasses.put(id, newmass);

		}
		return allmasses; 

	}





	private ArrayList<Spring> implementSprings(String[][] springs, HashMap<String, PhysicalObjectMass> allmasses) {
		ArrayList<Spring> allSprings = new ArrayList<Spring>();

		for (int i = 0; i< springs.length; i++) {
			String[] currspring = springs[i];
			Spring spring = new Spring("spring", 1, JGColor.yellow);


			PhysicalObjectMass mass1 = allmasses.get(currspring[0]); 
			PhysicalObjectMass mass2 = allmasses.get(currspring[1]);

			double k = Double.parseDouble(currspring[3]);
			double restLength = Double.parseDouble(currspring[2]);

			spring.connect(mass1, mass2, 30, restLength );

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
		HashMap<String, PhysicalObjectMass> allmasses = this.implementMasses(masses);


		//String [][] fmasses = elements.createFixedMasses(doc);
		//createFMasses(fmasses);
		
		String [][]springs = elements.createSprings(doc);
		ArrayList<Spring> springList = this.implementSprings(springs, allmasses);
		ArrayList<PhysicalObjectMass> massList = new ArrayList<PhysicalObjectMass>(allmasses.values());

		Assembly assembly= new Assembly(massList, springList);
		assemblies.add(assembly);

		//String [][] muscles = elements.createMuscles(doc);
		//createMuscles(muscles);
	}

	private void getEnvironment(String filename) {

		EnvironmentParser environment = new EnvironmentParser();
		Node environ = environment.parse(filename);
		double [] gravityvals = environment.getGravity(environ);
		double viscositymagnitude = environment.getViscosity(environ);
		double [] centermass = environment.getCenterMass(environ);
		double[][] wallvals = environment.getWalls(environ);

		g = new Gravity(gravityvals[0], gravityvals[1]);
		g.setAssembliesList(assemblies);
		v = new Viscosity(viscositymagnitude);
		v.setAssembliesList(assemblies);
		com = new CenterOfMass(centermass[0], centermass[1]);
		com.setAssembliesList(assemblies);
	
	}


	public String userSelects() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"XML Files", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(getParent());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File chosenFile = chooser.getSelectedFile();
			String pathofFile = chosenFile.getAbsolutePath();

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
//		temp.applyForce();
//		temp2.applyForce();
//		temp3.applyForce();
		

		checkToggle();
	}

	private void checkToggle() {
		if(getKey('C')) {
			if (assemblies.size()>0) {
				for (Assembly assembly: assemblies) {
					assembly.remove();
				}
			}

		}

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
			GRAVITY = !GRAVITY;
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
		if(GRAVITY){
			g.setAssembliesList(assemblies);
			g.applyForce();
		}
		if(VISCOSITY) {
			v.setAssembliesList(assemblies);
			v.applyForce();
		}
		if(CENTER_OF_MASS){
			com.setAssembliesList(assemblies);
			com.applyForce();
		}
	}

	@Override
	public void paintFrame (){
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
