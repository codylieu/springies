package jboxGlue;

import jgame.JGColor;

public class Gravity extends PhysicalObject {

	public Gravity(String name, int collisionId, JGColor color) {
		super(name, collisionId, color);
		// TODO Auto-generated constructor stub
	}

	public void setGravity(double mass){
		double force = mass * 9.8;
		setForce(0, force);
	}

	@Override
	protected void paintShape() {
		// TODO Auto-generated method stub
	}
	
	
}
