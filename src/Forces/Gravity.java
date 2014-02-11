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
		for(int i = 0; i < assemblies.size(); i++){
			ArrayList<PhysicalObjectMass> assemblyMasses = assemblies.get(i).getMasses();
			for(int j = 0; j < assemblyMasses.size(); j++){
				PhysicalObjectMass curMass = assemblyMasses.get(j);
				double forceNet = curMass.myMass * magnitude;
				double forceX = forceNet * Math.cos((direction*Math.PI)/180);
				double forceY = forceNet * Math.sin((direction*Math.PI)/180);
				curMass.setForce(forceX, forceY);
			}
		}
	}

}
