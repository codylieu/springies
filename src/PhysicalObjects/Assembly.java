package PhysicalObjects;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.tools.javac.util.List;

import Connectors.Spring;
import Masses.PhysicalObjectMass;
import jgame.JGObject;

public class Assembly {
	private ArrayList<PhysicalObjectMass> myMasses;
	
	private ArrayList<Spring> mySprings;

	public Assembly( ArrayList<PhysicalObjectMass> masses, ArrayList<Spring> springs) {
		myMasses = masses;
		mySprings = springs;
	}
	
	public ArrayList<PhysicalObjectMass> getMasses() {
		return myMasses;
	}
	
	public void remove() {
		for (PhysicalObjectMass mass : myMasses) {
			mass.destroy();		
		}
	}
}
