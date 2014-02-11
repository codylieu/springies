package PhysicalObjects;

import org.jbox2d.common.Vec2;

import jgame.JGColor;

public class Wall extends PhysicalObjectRect {
	private String type;

	public Wall(String id, int collisionId, JGColor color, double width,
			double height, String mytype) {
		
		super(id, collisionId, color, width, height);
		setType(mytype);
	}
	
	private void setType(String mytype) {
		if(!(mytype.equals("top") || mytype.equals("bottom") || mytype.equals("right") || mytype.equals("left"))) {
			type = "";
		} else {
			type = mytype;
		}
	}
	
	public void increaseArea() {
		Vec2 position = myBody.getPosition();
		if (type.equals("top")) {
			
			if (y >10.0) {
				y--;
			}
			
		} else if (type.equals("bottom")) {
			if (y < 470.0) {
				y++;
			}
			
			
		} else if (type.equals("right")) {
			if (x < 843.0) {
				x++; 
			}
			
		} else if (type.equals("left")) {
			if (x > 10.0) {
				x--;
			}
			
		}
		this.setPos(x, y);
		
	}
	
	public void reduceArea() {
		Vec2 position = myBody.getPosition();
		if (type.equals("top")) {
			
			y++;
			
		} else if (type.equals("bottom")) {
			
			y--;
			
		} else if (type.equals("right")) {
			
			x--;
			
		} else if (type.equals("left")) {
			x++; 
		}
		this.setPos(x, y);
	}
	
	

}
