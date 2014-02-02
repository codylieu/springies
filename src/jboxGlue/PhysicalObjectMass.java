package jboxGlue;

import jgame.JGColor;
import jgame.JGObject;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObjectRect;


public class PhysicalObjectMass extends PhysicalObject
{
	private double myRadius;

	public PhysicalObjectMass (String id,
			int collisionId,
			JGColor color,
			double radius,
			double mass,
			double x,
			double y,
			double vx,
			double vy)
	{
		super(id, collisionId, color);
		init(radius, mass, x, y, vx, vy);
	}

	public PhysicalObjectMass (String id,
			int collisionId,
			String gfxname,
			double radius,
			double mass,
			double x,
			double y,
			double vx,
			double vy)
	{
		super(id, collisionId, gfxname);
		init(radius, mass, x, y, vx, vy);
	}

	private void init (double radius, double mass, double x, double y, double vx, double vy)
	{
		// save arguments
		myRadius = radius;
		int intRadius = (int)radius;
		// make it a circle
		CircleDef shape = new CircleDef();
		shape.radius = (float)radius;
		shape.density = (float)mass;
		createBody(shape);
		setBBox(-intRadius, -intRadius, 2 * intRadius, 2 * intRadius);
		setPos(x, y);
		//				setSpeed(vx, vy);
		setForce(vx, vy);
	}
	
//	public void move(){
//		
//	}


	public void hit (JGObject other)
	{
		// we hit something! bounce off it!
		Vec2 velocity = myBody.getLinearVelocity();
		// is it a tall wall?
		final double DAMPING_FACTOR = 0.8;
		boolean isSide = other.getBBox().height > other.getBBox().width;
		if (isSide) {
			velocity.x *= -DAMPING_FACTOR;
		}
		else {
			velocity.y *= -DAMPING_FACTOR;
		}
		// apply the change
		myBody.setLinearVelocity(velocity);
	}

	@Override
	public void paintShape ()
	{
		myEngine.setColor(myColor);
		myEngine.drawOval(x, y, (float)myRadius * 2, (float)myRadius * 2, true, true);
	}

}
