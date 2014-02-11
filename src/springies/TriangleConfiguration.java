package springies;

import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;
import Connectors.Spring;
import Masses.PhysicalObjectMass;
import PhysicalObjects.PhysicalObject;

// Used to test Spring Forces by building a three mass system
public class TriangleConfiguration extends PhysicalObject{

	protected TriangleConfiguration(String name, int collisionId, JGColor color) {
		super(name, collisionId, color);
	}

	private PhysicalObjectMass m1;
	private PhysicalObjectMass m2;
	private PhysicalObjectMass m3;
	private Spring temp;
	private Spring temp2;
	private Spring temp3;
	
	public void build(){
		m1 = new PhysicalObjectMass("ball", 1, JGColor.red, 10, 5, 400, 240,0,0);
		m2 = new PhysicalObjectMass("ball2", 1, JGColor.yellow, 10, 5, 300, 140, 0,0);
		m3 = new PhysicalObjectMass("ball3", 1, JGColor.blue, 10, 5, 200, 240, 0, 0);

		temp = new Spring("spring", 0, JGColor.pink);
		temp2 = new Spring("spring", 0, JGColor.magenta);
		temp3 = new Spring("spring", 0, JGColor.orange);

		temp.connect(m1, m2, 4, 20);
		temp2.connect(m1, m3, 4, 20);
		temp3.connect(m2, m3, 4, 20);
	}

	@Override
	protected void paintShape() {
		// TODO Auto-generated method stub
		
	}
	
}
