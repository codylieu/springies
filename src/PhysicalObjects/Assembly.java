package PhysicalObjects;

import java.util.ArrayList;
import java.util.HashMap;

import Connectors.Spring;

import jgame.JGObject;

public class Assembly {
	private HashMap<String, PhysicalObjectMass> mymasses;
	
	private ArrayList<Spring> mysprings;
	
	
	public void Assembly(HashMap<String, PhysicalObjectMass> masses, ArrayList<Spring> springs) {
	
		mymasses = masses;
		mysprings = springs;
		
	}
	
	
	
	
	

}
