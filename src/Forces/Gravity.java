package Forces;

import PhysicalObjects.PhysicalObjectMass;

public class Gravity extends GlobalForces {
	private double direction;
	private double magnitude;

	
	
	@Override
	public void applyForce() {
		// TODO Auto-generated method stub
		for(int i = 0; i < list.size(); i++){
			PhysicalObjectMass curMass = list.get(i);
			double forceNet = curMass.myMass * magnitude;
			double forceX = forceNet * Math.cos((direction*Math.PI)/180);
			double forceY = forceNet * Math.sin((direction*Math.PI)/180);
			curMass.setForce(forceX, forceY);
		}
	}

}
