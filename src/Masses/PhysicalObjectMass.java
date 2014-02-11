package Masses;

import jgame.JGColor;
import jgame.JGObject;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

import Forces.WorldManager;
import PhysicalObjects.PhysicalObject;

public class PhysicalObjectMass extends PhysicalObject{
	private double myRadius;
	private double myMass;
	private double myX;
	private double myY;
	
	public double getX(){
		return myX;
	}
	public double getY(){
		return myY;
	}
	public double getMass(){
		return myMass;
	}
	
	public PhysicalObjectMass (String id,
			int collisionId,
			JGColor color,
			double radius,
			double mass,
			double x,
			double y,
			double vx,
			double vy){
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
			double vy){
		super(id, collisionId, gfxname);
		init(radius, mass, x, y, vx, vy);
	}

	public Vec2 getVelocity(){
		Vec2 velocity = myBody.getLinearVelocity();
		return velocity;
	}

	public void move(){
		if (myBody.m_world != WorldManager.getWorld()){
			remove();
			return;
		}
		Vec2 position = myBody.getPosition();
		x = position.x;
		y = position.y;
		myRotation = -myBody.getAngle();
	}

	private void init (double radius, double mass, double x, double y, double vx, double vy){
		myRadius = radius;
		myMass = mass;
		myX = x;
		myY = y;
		int intRadius = (int)radius;
		CircleDef shape = new CircleDef();
		shape.radius = (float)radius;
		shape.density = (float)mass;
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

	public void hit (JGObject other){
		Vec2 velocity = myBody.getLinearVelocity();
		final double DAMPING_FACTOR = .8;
		boolean isSide = other.getBBox().height > other.getBBox().width;
		if (isSide) {
			velocity.x *= -DAMPING_FACTOR;
		}
		else {
			velocity.y *= -DAMPING_FACTOR;
		}
		myBody.setLinearVelocity(velocity);
	}

	@Override
	public void paintShape (){
		myEngine.setColor(myColor);
		myEngine.drawOval(x, y, (float)myRadius * 2, (float)myRadius * 2, true, true);
	}

}
