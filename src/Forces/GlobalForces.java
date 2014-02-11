package Forces;

import java.util.ArrayList;
import java.util.List;

import jgame.JGColor;
import Connectors.Spring;
import PhysicalObjects.Assembly;
import PhysicalObjects.PhysicalObjectMass;

public abstract class GlobalForces implements IForce{

	protected static ArrayList<Assembly> assemblies;
	
	@Override
	public abstract void applyForce();
	
	public void setAssembliesList(ArrayList<Assembly> list) {
		assemblies = list;
	}

}