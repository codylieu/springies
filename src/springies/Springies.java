package springies;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
		WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.1f));
		addBall();
		addWalls();

		createPhysicalElements();

		//		PhysicalObject fixed = new PhysicalObjectFixedMass("ball", 1, JGColor.yellow, 10, 0, displayWidth()/1.2, displayHeight()/1.2);

	}

	public void addBall ()
	{
		// add a bouncy ball
		// NOTE: you could make this into a separate class, but I'm lazy

		/*PhysicalObjectMass*/ m1 = new PhysicalObjectMass("ball", 1, JGColor.red, 10, 5, displayWidth()/2, displayHeight()/2,0,0);
		/*PhysicalObjectMass*/ m2 = new PhysicalObjectMass("ball2", 1, JGColor.yellow, 10, 5, displayWidth()/2-50, displayHeight()/2-50, 0,0);
		//		/*PhysicalObjectMass*/ m3 = new PhysicalObjectMass("ball3", 1, JGColor.blue, 10, 5, displayWidth()/2-100, displayHeight()/2-100, 0, 0);
		Spring temp = new Spring("spring", 0, JGColor.red);
		temp.connect(m1, m2);
		m1.setForce(8000, -10000);
		//		temp.calculateSpringForce(m1.myX, m1.myY, m2.myX, m2.myY, 1, 50);
		//		Spring temp2 = new Spring("spring", 0, JGColor.blue);
		//		temp2.connect(m1, m3);
		//		temp2.calculateSpringForce(m1.myX, m1.myY, m3.myX, m3.myY, 1, 50);
		//		m1.setGravity(m1.myMass, 90, -10000);
		//		m2.setGravity(m2.myMass, 0, 10000);
		//		m3.setGravity(m3.myMass, 90, -10000);

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

	public HashMap<String, PhysicalObject> createMasses(String[][] masses) {
		HashMap<String, PhysicalObject> allmasses = new HashMap<String, PhysicalObject>();
		for (int i = 0; i< masses.length; i++) {
			String[] currmass = masses[i];
			String id = currmass[0];
			int collisionId = 10;
			JGColor color = JGColor.green;
			double radius = 3;
			double mass = 5;
			double x = Double.parseDouble(currmass[1]);
			double y = Double.parseDouble(currmass[2]);
			double vx = Double.parseDouble(currmass[3]);
			double vy = Double.parseDouble(currmass[4]);
			System.out.println("creatednewmass");
			PhysicalObject newmass = new PhysicalObjectMass(id, collisionId, color, radius, mass, x, y, vx, vy);
			allmasses.put(id, newmass);


		}
		System.out.println(allmasses.toString());
		return allmasses; 

	}

	public void createSprings(String[][] springs, HashMap<String, PhysicalObject> allmasses) {
		for (int i = 0; i< springs.length; i++) {
			String[] currspring = springs[i];
			Spring spring = new Spring("spring", 10, JGColor.yellow);
			spring.connect((PhysicalObjectMass)allmasses.get(currspring[0]), (PhysicalObjectMass) allmasses.get(currspring[1]));
			System.out.println("connected " + currspring[0] + " " +currspring[1]);

		}

	}


	public void createPhysicalElements( ) {

		ObjectsParser elements = new ObjectsParser();
		Node doc = elements.parse("assets/ball.xml");
		System.out.println(doc.toString());

		String[][] masses = elements.createMasses(doc);
		HashMap<String, PhysicalObject> allmasses = createMasses(masses);
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
		//		m1.setViscosity(0);
		//		m2.setViscosity(0);
		//		m3.setViscosity(0);
		temp.calculateSpringForce(50, 50, 100, 100, 4, 50);
		//		temp2.calculateSpringForce(m1.myX, m1.myY, m3.myX, m3.myY, 1, 50);
		System.out.println(m1.myX + "Test");
	}

	@Override
	public void paintFrame ()
	{
		// nothing to do
		// the objects paint themselves
	}
}
