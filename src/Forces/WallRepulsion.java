package Forces;

import java.util.ArrayList;

import PhysicalObjects.PhysicalObjectMass;
import PhysicalObjects.WalledArea;

public class WallRepulsion extends GlobalForces{
	
	private int wallID;
	private double magnitude;
	private double exponent;
	private WalledArea allWalls;
	
	public WallRepulsion(int id, double m, double exp){
		wallID = id;
		magnitude = m;
		exponent = exp;
	}
	
	@Override
	public void applyForce() {
	}

	public void leftWallForce() {
		for(int i = 0; i < assemblies.size(); i++){
			ArrayList<PhysicalObjectMass> assemblyMasses = assemblies.get(i).getMasses();
			for(int j = 0; j < assemblyMasses.size(); j++){
				PhysicalObjectMass curMass = assemblyMasses.get(j);
				curMass.setForce(10, 0);
			}
		}
	}
	
	public void topWallForce() {
		for(int i = 0; i < assemblies.size(); i++){
			ArrayList<PhysicalObjectMass> assemblyMasses = assemblies.get(i).getMasses();
			for(int j = 0; j < assemblyMasses.size(); j++){
				PhysicalObjectMass curMass = assemblyMasses.get(j);
				curMass.setForce(0, 10);
			}
		}
	}
	
	public void rightWallForce() {
		for(int i = 0; i < assemblies.size(); i++){
			ArrayList<PhysicalObjectMass> assemblyMasses = assemblies.get(i).getMasses();
			for(int j = 0; j < assemblyMasses.size(); j++){
				PhysicalObjectMass curMass = assemblyMasses.get(j);
				curMass.setForce(-10, 0);
			}
		}
	}
	
	public void bottomWallForce() {
		for(int i = 0; i < assemblies.size(); i++){
			ArrayList<PhysicalObjectMass> assemblyMasses = assemblies.get(i).getMasses();
			for(int j = 0; j < assemblyMasses.size(); j++){
				PhysicalObjectMass curMass = assemblyMasses.get(j);
				curMass.setForce(0, -10);
			}
		}
	}

}