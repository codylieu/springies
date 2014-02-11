package PhysicalObjects;

import org.jbox2d.collision.CircleDef;

import jgame.JGColor;

public class PhysicalObjectFixedMass extends PhysicalObjectMass {

	private double myRadius;
	private double myX;
	private double myY;

	public PhysicalObjectFixedMass (String id,
			int collisionId,
			JGColor color,
			double radius,
			double x,
			double y){

		super(id, collisionId, color, radius, 0, x, y, 0, 0);
	}

	public PhysicalObjectFixedMass (String id,
			int collisionId,
			String gfxname,
			double radius,
			double x,
			double y){

		super(id, collisionId, gfxname, radius, 0, x, y, 0, 0);
	}

}
