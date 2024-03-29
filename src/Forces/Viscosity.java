package Forces;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import Masses.PhysicalObjectMass;

public class Viscosity extends GlobalForces{

	private double magnitude;

	public Viscosity(double m){
		magnitude = m;
	}
	
	@Override
	public void applyForce() {
		for(int assemblyidx = 0; assemblyidx  < assemblies.size(); assemblyidx ++){
			ArrayList<PhysicalObjectMass> assemblyMasses = assemblies.get(assemblyidx).getMasses();
			for(int massIndex = 0; massIndex < assemblyMasses.size(); massIndex++){
				PhysicalObjectMass curMass = assemblyMasses.get(massIndex);
				Vec2 linearVelocity = curMass.getVelocity();
				linearVelocity.x *= magnitude;
				linearVelocity.y *= magnitude;
				curMass.setForce(-linearVelocity.x, -linearVelocity.y);
			}
		}
	}
}