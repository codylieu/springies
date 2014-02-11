package Forces;

import java.util.ArrayList;

import Masses.PhysicalObjectMass;

public class Gravity extends GlobalForces {
	private double direction;
	private double magnitude;
	
	public Gravity(double d, double m){
		direction = d;
		magnitude = m;
	}

	@Override
	public void applyForce() {
		for(int assemblyidx = 0; assemblyidx < assemblies.size(); assemblyidx++){
			ArrayList<PhysicalObjectMass> assemblyMasses = assemblies.get(assemblyidx).getMasses();
			for(int massIndex = 0; massIndex < assemblyMasses.size(); massIndex++){
				PhysicalObjectMass curMass = assemblyMasses.get(massIndex);
				double forceNet = curMass.getMass() * magnitude;
				double forceX = forceNet * Math.cos((direction*Math.PI)/180);
				double forceY = forceNet * Math.sin((direction*Math.PI)/180);
				curMass.setForce(forceX, forceY);
			}
		}
	}
}
