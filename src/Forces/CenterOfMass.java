package Forces;

import java.util.HashMap;

import PhysicalObjects.PhysicalObject;
import PhysicalObjects.PhysicalObjectMass;
import jgame.JGColor;

public class CenterOfMass extends GlobalForces{

	private HashMap<String, PhysicalObjectMass> map;
	
	public Double[] findCOM(HashMap<String, PhysicalObjectMass> map){
		Double[] coordinatesCOM = new Double[2];
		double numerCOMX = 0;
		double numerCOMY = 0;
		double denomCOM = 0;
		for(String id : map.keySet()){
			numerCOMX += map.get(id).myMass * map.get(id).myX;
			numerCOMY += map.get(id).myMass * map.get(id).myY;
			denomCOM += map.get(id).myMass;
		}
		
		double comX = numerCOMX/denomCOM;
		double comY = numerCOMY/denomCOM;
		
		coordinatesCOM[0] = comX;
		coordinatesCOM[1] = comY;
		
		return coordinatesCOM;
	}

	@Override
	public void applyForce() {
		// TODO Auto-generated method stub
		Double[] coordinatesCOM = findCOM(map);

		for(String id : map.keySet()){
			double dist = Math.sqrt(Math.pow(coordinatesCOM[0] - map.get(id).myX, 2) + Math.pow(coordinatesCOM[1] - map.get(id).myY, 2));
			double xVec = coordinatesCOM[0] - map.get(id).myX;
			double yVec = coordinatesCOM[1] - map.get(id).myY;
			double forceX = xVec; // No equation for this?
			double forceY = yVec; // No equation for this?
			map.get(id).setForce(forceX, forceY);
		}
	}

}
