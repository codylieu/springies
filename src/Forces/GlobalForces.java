package Forces;

import java.util.ArrayList;
import java.util.List;

import PhysicalObjects.PhysicalObjectMass;

public abstract class GlobalForces implements IForce{

	protected static List<PhysicalObjectMass> list = new ArrayList<PhysicalObjectMass>();
	
	@Override
	public abstract void applyForce();

}
