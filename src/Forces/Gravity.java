package Forces;

public class Gravity implements IForce {

	@Override
	public void applyForce() {
		// TODO Auto-generated method stub
		
	}
	
	public void setGravity(double mass, double direction, double magnitude){
		double forceNet = mass * magnitude;
		double forceX = forceNet * Math.cos((direction*Math.PI)/180);
		double forceY = forceNet * Math.sin((direction*Math.PI)/180);
//		setForce(forceX, forceY);
	}

}
