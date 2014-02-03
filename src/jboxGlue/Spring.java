package jboxGlue;

import jgame.JGColor;
import jgame.JGObject;

public class Spring extends PhysicalObject {
	private PhysicalObjectMass m1;
	private PhysicalObjectMass m2;
	private double myrestlength;
	private double myk;
	
	
	public Spring(String name, int collisionId, JGColor color) {
		super(name, collisionId, color);
		
	}

	public void connect(PhysicalObjectMass mass1, PhysicalObjectMass mass2, double k, double restlength) {
		
		m1 = mass1;
		m2 = mass2;
		myrestlength = restlength;
		myk = k;
		
	}


	public void calculateSpringForce(double x1, double y1, double x2, double y2, double k, double restLength){
		
		double totalDist = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
		double xVec = x2-x1; // x vector pointing FROM mass 1 TO mass 2
		double yVec = y2-y1; // y vector pointing FROM mass 1 TO mass 2
		double forceX = (k * (totalDist - restLength) * xVec)/totalDist;
		double forceY = (k * (totalDist - restLength) * yVec)/totalDist;
		m1.setForce(forceX, forceY);
		m2.setForce(-forceX, -forceY);
	}
	
	@Override
    public void paintShape()
    {
		double x1 = m1.x;
		double y1 = m1.y;
		double x2 = m2.x;
		double y2 = m2.y;
		calculateSpringForce(x1, y1, x2, y2, myk, myrestlength);
		
		
        myEngine.setColor(myColor);
      
        myEngine.drawLine(x1, y1, x2, y2);
    }
    
 
	
}
