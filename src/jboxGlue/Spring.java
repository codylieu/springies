package jboxGlue;

import jgame.JGColor;
import jgame.JGObject;

public class Spring extends PhysicalObject {
	private PhysicalObjectMass m1;
	private PhysicalObjectMass m2;
	
	
	public Spring(String name, int collisionId, JGColor color) {
		super(name, collisionId, color);
		
	}

	public void connect(PhysicalObjectMass mass1, PhysicalObjectMass mass2) {
		m1 = mass1;
		m2 = mass2;
	}
	
	@Override 
	public void move() {
		
	}
	
	@Override
    public void paintShape()
    {
		double x1 = m1.x;
		double y1 = m1.y;
		double x2 = m2.x;
		double y2 = m2.y;
		
        myEngine.setColor(myColor);
      
        myEngine.drawLine(x1, y1, x2, y2);
    }
    
 
	
}
