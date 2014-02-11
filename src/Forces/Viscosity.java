package Forces;

import org.jbox2d.common.Vec2;

import PhysicalObjects.PhysicalObjectMass;

public class Viscosity extends GlobalForces{

	private double magnitude;

	public Viscosity(double m){
		magnitude = m;
	}
	
	
	@Override
	public void applyForce() {
		// TODO Auto-generated method stub
		for(int i = 0; i < list.size(); i++){
			PhysicalObjectMass curMass = list.get(i);
			Vec2 linearVelocity = curMass.getVelocity();
			linearVelocity.x *= magnitude;
			linearVelocity.y *= magnitude;
			
			curMass.setSpeed(linearVelocity.x, linearVelocity.y);
			
//			setVelocity(linearVelocity.x, linearVelocity.y);
//			myBody.setLinearVelocity(Viscosity.setViscosity(this, 0.8));	
		}
	}

}