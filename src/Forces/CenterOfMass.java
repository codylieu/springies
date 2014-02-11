package Forces;

import java.util.ArrayList;
import java.util.HashMap;

import Masses.PhysicalObjectMass;
import PhysicalObjects.PhysicalObject;
import jgame.JGColor;

public class CenterOfMass extends GlobalForces{
	private double magnitude;
	private double exponent;

	public CenterOfMass(double m, double exp){
		magnitude = m;
		exponent = exp;
	}

	public Double[] findCOM(ArrayList<PhysicalObjectMass> masses){
		Double[] coordinatesCOM = new Double[2];
		double numerCOMX = 0;
		double numerCOMY = 0;
		double denomCOM = 0;
		for(int i = 0; i < masses.size(); i++){
			PhysicalObjectMass curMass = masses.get(i);
			numerCOMX += curMass.myMass * curMass.myX;
			numerCOMY += curMass.myMass * curMass.myY;
			denomCOM += curMass.myMass;
		}

		double comX = numerCOMX/denomCOM;
		double comY = numerCOMY/denomCOM;

		coordinatesCOM[0] = comX;
		coordinatesCOM[1] = comY;

		return coordinatesCOM;
	}

	@Override
	public void applyForce() {
		for(int i = 0; i < assemblies.size(); i++){
			ArrayList<PhysicalObjectMass> assemblyMasses = assemblies.get(i).getMasses();
			Double[] coordinatesCOM = findCOM(assemblyMasses);
			for(int j = 0; j < assemblyMasses.size(); j++){
				PhysicalObjectMass curMass = assemblyMasses.get(j);
				double dist = Math.sqrt(Math.pow(coordinatesCOM[0] - curMass.myX, 2) + Math.pow(coordinatesCOM[1] - curMass.myY, 2));
				double xVec = coordinatesCOM[0] - curMass.myX;
				double yVec = coordinatesCOM[1] - curMass.myY;
				double forceX = (xVec * magnitude) / Math.pow(dist, exponent);
				double forceY = (yVec * magnitude) / Math.pow(dist, exponent);
				if(dist < 100){
					curMass.setForce(0, 0);
				}
				else{
					curMass.setForce(forceX, forceY);
				}
			}
		}
	}

}
