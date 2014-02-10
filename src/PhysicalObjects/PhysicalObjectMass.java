package PhysicalObjects;

import jgame.JGColor;
import jgame.JGObject;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

import Forces.Viscosity;
import Forces.WorldManager;


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

	public Vec2 getVelocity() {
		Vec2 velocity = myBody.getLinearVelocity();
		return velocity;
	}

	private void initViscosity(){
		myBody.setLinearVelocity(Viscosity.setViscosity(this, 0.8));
	}

	public void move(){
		// if the JGame object was deleted, remove the physical object too
		if (myBody.m_world != WorldManager.getWorld()) {
			remove();
			return;
		}
		// copy the position and rotation from the JBox world to the JGame world
		Vec2 position = myBody.getPosition();
		x = position.x;
		y = position.y;
		myRotation = -myBody.getAngle();
//		initViscosity();
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
		// Gets rid of collisions?
		shape.filter.groupIndex = -1;
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
		final double DAMPING_FACTOR = .8;
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
