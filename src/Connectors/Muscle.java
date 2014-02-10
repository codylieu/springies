package Connectors;

import PhysicalObjects.PhysicalObjectMass;
import jgame.JGColor;

public class Muscle extends Spring{

	public Muscle(String name, int collisionId, JGColor color) {
		super(name, collisionId, color);
		// TODO Auto-generated constructor stub
	}

	protected PhysicalObjectMass m1;
	protected PhysicalObjectMass m2;
	private double myRestLength;
	private double myK;
	
	// Unique to Muscles
	private double amplitude;
	private double timer; // Find a better way to do this


	@Override
	public void applyForce(){
		double totalDist = Math.sqrt(Math.pow(m2.x-m1.x, 2) + Math.pow(m2.y-m1.y, 2));
		double xVec = m2.x-m1.x; // x vector pointing FROM mass 1 TO mass 2
		double yVec = m2.y-m1.y; // y vector pointing FROM mass 1 TO mass 2
		double rest = myRestLength * (1 + amplitude * Math.sin(timer + 1));
		double forceX = (myK * (totalDist - rest) * xVec)/totalDist;
		double forceY = (myK * (totalDist - rest) * yVec)/totalDist;
		m1.setForce(forceX, forceY);
		m2.setForce(-forceX, -forceY);
	}

	

}
