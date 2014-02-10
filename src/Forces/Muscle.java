package Forces;

import jgame.JGColor;

public class Muscle extends Spring{

	public Muscle(String name, int collisionId, JGColor color) {
		super(name, collisionId, color);
		// TODO Auto-generated constructor stub
	}
	
	public void calculateMuscleForce(double x1, double y1, double x2, double y2, double k, double restLength, double Amp, double timer){
		
		double totalDist = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
		double xVec = x2-x1; // x vector pointing FROM mass 1 TO mass 2
		double yVec = y2-y1; // y vector pointing FROM mass 1 TO mass 2
		double rest = restLength * (1 + Amp * Math.sin(timer + 1));
		double forceX = (k * (totalDist - rest) * xVec)/totalDist;
		double forceY = (k * (totalDist - rest) * yVec)/totalDist;
		m1.setForce(forceX, forceY);
		m2.setForce(-forceX, -forceY);

	}

	

}
