package Forces;

import java.util.ArrayList;

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
		for(int assemblyidx = 0; assemblyidx  < assemblies.size(); assemblyidx ++){
			ArrayList<PhysicalObjectMass> assemblyMasses = assemblies.get(assemblyidx).getMasses();
			for(int massIndex = 0; massIndex < assemblyMasses.size(); massIndex++){
				PhysicalObjectMass curMass = assemblyMasses.get(massIndex);
				Vec2 linearVelocity = curMass.getVelocity();
				linearVelocity.x *= magnitude;
				linearVelocity.y *= magnitude;
			
				curMass.setSpeed(linearVelocity.x, linearVelocity.y);
			}
			
//			setVelocity(linearVelocity.x, linearVelocity.y);
//			myBody.setLinearVelocity(Viscosity.setViscosity(this, 0.8));	
		}
	}

}