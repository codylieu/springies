package Forces;

import org.jbox2d.common.Vec2;

import PhysicalObjects.PhysicalObjectMass;

public class Viscosity implements IForce{

	public static Vec2 setViscosity(PhysicalObjectMass m, double magnitude){
	Vec2 linearVelocity = m.getVelocity();
		linearVelocity.x *= magnitude;
		linearVelocity.y *= magnitude;
		return linearVelocity;
	}

	@Override
	public void applyForce() {
		// TODO Auto-generated method stub
		
	}


}