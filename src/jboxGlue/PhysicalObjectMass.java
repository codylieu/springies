package jboxGlue;

import jgame.JGColor;
import jgame.JGObject;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObjectRect;

public class PhysicalObjectMass extends PhysicalObject
{
	private double myRadius;
	public double myMass;
	public double myX;
	public double myY;
	public double vx;
	


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

	public void setGravity(double mass, double direction, double magnitude){
		double forceNet = mass * magnitude;
		double forceX = forceNet * Math.cos((direction*Math.PI)/180);
		double forceY = forceNet * Math.sin((direction*Math.PI)/180);
		setForce(forceX, forceY);
	}

	public void setViscosity(double magnitude){
		Vec2 velocity = myBody.getLinearVelocity();
		velocity.x *= magnitude;
		velocity.y *= magnitude;
		myBody.setLinearVelocity(velocity);
	}

	private void init (double radius, double mass, double x, double y, double vx, double vy)
	{
		// save arguments
		myRadius = radius;
		myMass = mass;
		myX = x;
		myY = y;
		int intRadius = (int)radius;
		// make it a circle
		CircleDef shape = new CircleDef();
		shape.radius = (float)radius;
		shape.density = (float)mass;
		createBody(shape);
		setBBox(-intRadius, -intRadius, 2 * intRadius, 2 * intRadius);
		setPos(x, y);

		Vec2 velocity = myBody.getLinearVelocity();
		velocity.x = 0.0f;
		velocity.y = 0.0f;
		velocity.x += vx;
		velocity.y += vy;
		myBody.setLinearVelocity(velocity);

	}

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
