package Forces;

import org.jbox2d.common.Vec2;

import PhysicalObjects.PhysicalObjectMass;

public class Viscosity extends GlobalForces{

	private double magnitude;
	private PhysicalObjectMass m;

	@Override
	public void applyForce() {
		// TODO Auto-generated method stub
		Vec2 linearVelocity = m.getVelocity();
		linearVelocity.x *= magnitude;
		linearVelocity.y *= magnitude;
	}

}