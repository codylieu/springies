package Forces;

import org.jbox2d.common.Vec2;

import PhysicalObjects.PhysicalObjectMass;

public class Viscosity{

	public static Vec2 setViscosity(PhysicalObjectMass m, double magnitude){
	Vec2 linearVelocity = m.getVelocity();
		linearVelocity.x *= magnitude;
		linearVelocity.y *= magnitude;
		return linearVelocity;
	}


}