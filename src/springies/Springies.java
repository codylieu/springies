package springies;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Node;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectCircle;
import jboxGlue.PhysicalObjectFixedMass;
import jboxGlue.PhysicalObjectMass;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.Spring;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;
import jboxGlue.CenterOfMass;

import org.jbox2d.common.Vec2;

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
		System.out.println("VISCOSITY MAGNITUDE: " + viscositymagnitude);
		WorldManager.initWorld(this);
//		WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.1f));
				addBall();
		addWalls();

//		createPhysicalElements();

		//		PhysicalObject fixed = new PhysicalObjectFixedMass("ball", 1, JGColor.yellow, 10, 0, displayWidth()/1.2, displayHeight()/1.2);

	}

	public void addBall ()
	{
		// add a bouncy ball
		// NOTE: you could make this into a separate class, but I'm lazy

		/*PhysicalObjectMass*/ m1 = new PhysicalObjectMass("ball", 1, JGColor.red, 10, 5, displayWidth()/2, displayHeight()/2,0,0);
		/*PhysicalObjectMass*/ m2 = new PhysicalObjectMass("ball2", 1, JGColor.yellow, 10, 5, displayWidth()/2-100, displayHeight()/2-100, 0,0);
		/*PhysicalObjectMass*/ m3 = new PhysicalObjectMass("ball3", 1, JGColor.blue, 10, 5, displayWidth()/2-200, displayHeight()/2, 0, 0);

				temp = new Spring("spring", 0, JGColor.pink);
//				temp.calculateSpringForce(m1.myX, m1.myY, m2.myX, m2.myY, 4, 100);

				temp2 = new Spring("spring", 0, JGColor.magenta);
				temp3 = new Spring("spring", 0, JGColor.orange);
				temp.connect(m1, m2, 4, 20);
				temp2.connect(m1, m3, 4, 20);
//				temp2.calculateSpringForce(m1.myX, m1.myY, m3.myX, m3.myY, 4, 100);


				temp3.connect(m2, m3, 4, 20);
//				temp3.calculateSpringForce(m2.myX, m2.myY, m3.myX, m3.myY, 4, 100);

		//		m1.setForce(-10000, -10000);
		//				temp.calculateSpringForce(m1.myX, m1.myY, m2.myX, m2.myY, 1, 50);

		//				temp2.calculateSpringForce(m1.myX, m1.myY, m3.myX, m3.myY, 1, 50);
		//				m1.setGravity(m1.myMass, 90, -10000);
		//				m2.setGravity(m2.myMass, 0, 10000);
		//				m3.setGravity(m3.myMass, 90, -10000);

	}

	private void addWalls ()
	{
		// add walls to bounce off of
		// NOTE: immovable objects must have no mass
		final double WALL_MARGIN = 10;
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = displayWidth() - WALL_MARGIN * 2 + WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2 + WALL_THICKNESS;
		PhysicalObject wall = new PhysicalObjectRect("wall", 2, JGColor.green,
				WALL_WIDTH, WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, WALL_MARGIN);
		wall = new PhysicalObjectRect("wall", 2, JGColor.green,
				WALL_WIDTH, WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
		wall = new PhysicalObjectRect("wall", 2, JGColor.green,
				WALL_THICKNESS, WALL_HEIGHT);
		wall.setPos(WALL_MARGIN, displayHeight() / 2);
		wall = new PhysicalObjectRect("wall", 2, JGColor.green,
				WALL_THICKNESS, WALL_HEIGHT);
		wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);
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
//			newmass.setGravity(mass, gravityvals[0], gravityvals[1]);
			System.out.println(gravityvals[0]);
			System.out.println(gravityvals[1]);
			allmasses.put(id, newmass);

		}

		CenterOfMass com = new CenterOfMass("com", 5, JGColor.green);
		com.setCOMForce(allmasses);
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
	
	

	public void createSprings(String[][] springs, HashMap<String, PhysicalObjectMass> allmasses) {

		for (int i = 0; i< springs.length; i++) {
			String[] currspring = springs[i];
			Spring spring = new Spring("spring", 1, JGColor.yellow);

			PhysicalObjectMass mass1 = allmasses.get(currspring[0]); 
			PhysicalObjectMass mass2 = allmasses.get(currspring[1]);

			System.out.println("connected " + currspring[0] + " " +currspring[1]);
			double k = Double.parseDouble(currspring[3]);
			double restLength = Double.parseDouble(currspring[2]);
			spring.connect(mass1, mass2, 6, restLength );
			spring.calculateSpringForce(mass1.myX, mass1.myY, mass2.myX, mass2.myY, k, restLength);
		}

	}


	//	public ArrayList<ArrayList<PhysicalObjectMass>> findNetworks(String[][] springs, HashMap<String, PhysicalObject> allmasses) {
	//		ArrayList<PhysicalObjectMass> network = new ArrayList<PhysicalObjectMass>();
	//		
	//		for (int i = 0; i<springs.length; i++) {
	//			String[] currspring = springs[i];
	//			PhysicalObjectMass currmass = (PhysicalObjectMass)allmasses.get(i);
	//		}
	//		
	//		return null;
	//		
	//		
	//	}
	//	




	public void createPhysicalElements( ) {

		ObjectsParser elements = new ObjectsParser();
		Node doc = elements.parse("assets/ball.xml");
		System.out.println(doc.toString());

		String[][] masses = elements.createMasses(doc);
		HashMap<String, PhysicalObjectMass> allmasses = createMasses(masses);
		allmasses.toString();

		System.out.println(masses[0][0] + "TEST");


		//String [][] fmasses = elements.createFixedMasses(doc);
		//createFMasses(fmasses);
		String [][]springs = elements.createSprings(doc);
		createSprings(springs, allmasses);

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

	}



	@Override
	public void doFrame ()
	{
		// update game objects
		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(2, 1);
		temp.calculateSpringForce(m1.myX, m1.myY, m2.myX, m2.myY, 1, 14);
		temp2.calculateSpringForce(m1.myX, m1.myY, m3.myX, m3.myY, 1, 14);
		temp3.calculateSpringForce(m2.myX, m2.myY, m3.myX, m3.myY, 1, 14);
		
	}


	@Override
	public void paintFrame ()
	{
		// nothing to do
		// the objects paint themselves
	}
}
