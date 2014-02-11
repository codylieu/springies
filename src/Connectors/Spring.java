package Connectors;

import Forces.IForce;
import PhysicalObjects.PhysicalObject;
import PhysicalObjects.PhysicalObjectMass;
import jgame.JGColor;
import jgame.JGObject;

public class Spring extends PhysicalObject implements IForce {
	
	protected PhysicalObjectMass m1;
	protected PhysicalObjectMass m2;
	private double myRestLength;
	private double myK;
	boolean killed;
	private double totalDist = myRestLength;
	
	public Spring(String name, int collisionId, JGColor color) {
		super(name, collisionId, color);
	}

	public void connect(PhysicalObjectMass mass1, PhysicalObjectMass mass2, double k, double restlength) {
		m1 = mass1;
		m2 = mass2;
		myRestLength = restlength;
		myK = k;
		killed = false;
	}
	
	public void remove() {
		killed = true;
	}
	
	@Override
    public void paintShape(){
		double x1 = m1.x;
		double y1 = m1.y;
		double x2 = m2.x;
		double y2 = m2.y;
		applyForce();
		if(totalDist >= myRestLength){
			myEngine.setColor(JGColor.blue);
		}
		else{
			myEngine.setColor(JGColor.pink);
		}
        if (!killed) {
        	myEngine.drawLine(x1, y1, x2, y2);
        }
    }

	@Override
	public void applyForce() {
		totalDist = Math.sqrt(Math.pow(m2.getX()-m1.getX(), 2) + Math.pow(m2.getY()-m1.getY(), 2));
		double xVec = m2.getX()-m1.getX(); // x vector pointing FROM mass 1 TO mass 2
		double yVec = m2.getY()-m1.getY(); // y vector pointing FROM mass 1 TO mass 2
		double forceX = (myK * (totalDist - myRestLength) * xVec)/totalDist;
		double forceY = (myK * (totalDist - myRestLength) * yVec)/totalDist;
		m1.setForce(forceX, forceY);
		m2.setForce(-forceX, -forceY);
	}	
}
