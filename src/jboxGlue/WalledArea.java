package jboxGlue;

import org.jbox2d.common.Vec2;

import PhysicalObjects.PhysicalObject;

import jgame.JGColor;

public class WalledArea extends PhysicalObject {
	

	private Wall topWall;
	private Wall leftWall;
	private Wall rightWall;
	private Wall bottomWall;
	
	public WalledArea(String name, int collisionId, JGColor color) {
		
		super(name, collisionId, color);

	}

	
	public void setWalls(Wall w1, Wall w2, Wall w3, Wall w4)  {
		
		topWall = w1;
		leftWall = w2;
		rightWall = w3;
		bottomWall = w4;
		
	}
	
	public void reduceArea() {
		
		topWall.reduceArea();
		leftWall.reduceArea();
		rightWall.reduceArea();
		bottomWall.reduceArea();
		
		
		
		
		
	}
	
	public void increaseArea() {
		topWall.increaseArea();
		rightWall.increaseArea();
		leftWall.increaseArea();
		bottomWall.increaseArea();
		
		
		
		
	}
	

	
	@Override
	protected void paintShape() {
		// TODO Auto-generated method stub

	}

}
