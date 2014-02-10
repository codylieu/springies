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
	
	
	public Spring(String name, int collisionId, JGColor color) {
		super(name, collisionId, color);
		
	}

	public void connect(PhysicalObjectMass mass1, PhysicalObjectMass mass2, double k, double restlength) {
		
		m1 = mass1;
		m2 = mass2;
		myRestLength = restlength;
		myK = k;
		
	}
	
	@Override
    public void paintShape()
    {
		double x1 = m1.x;
		double y1 = m1.y;
		double x2 = m2.x;
		double y2 = m2.y;
		applyForce();
		
		
        myEngine.setColor(myColor);
      
        myEngine.drawLine(x1, y1, x2, y2);
    }

	@Override
	public void applyForce() {
		// TODO Auto-generated method stub
		double totalDist = Math.sqrt(Math.pow(m2.x-m1.x, 2) + Math.pow(m2.y-m1.y, 2));
		double xVec = m2.x-m1.x; // x vector pointing FROM mass 1 TO mass 2
		double yVec = m2.y-m1.y; // y vector pointing FROM mass 1 TO mass 2
		double forceX = (myK * (totalDist - myRestLength) * xVec)/totalDist;
		double forceY = (myK * (totalDist - myRestLength) * yVec)/totalDist;
		m1.setForce(forceX, forceY);
		m2.setForce(-forceX, -forceY);
	}
    
 
	
}
