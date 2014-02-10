package PhysicalObjects;

import org.jbox2d.collision.CircleDef;

import jgame.JGColor;

public class PhysicalObjectFixedMass extends PhysicalObject {

	private double myRadius;
	private double myX;
	private double myY;

    public PhysicalObjectFixedMass (String id,
                                 int collisionId,
                                 JGColor color,
                                 double radius,
                                 double mass,
                                 double x,
                                 double y)
    {
        super(id, collisionId, color);
        init(radius, mass, x, y);
    }

    public PhysicalObjectFixedMass (String id,
                                 int collisionId,
                                 String gfxname,
                                 double radius,
                                 double mass,
                                 double x,
                                 double y)
    {
        super(id, collisionId, gfxname);
        init(radius, mass, x, y);
    }

    private void init (double radius, double mass, double x, double y)
    {
        // save arguments
        myRadius = radius;
        int intRadius = (int)radius;
        // make it a circle
        CircleDef shape = new CircleDef();
        shape.radius = (float)radius;
        shape.density = (float)mass;
        shape.filter.groupIndex = -1;
        createBody(shape);
        setBBox(-intRadius, -intRadius, 2 * intRadius, 2 * intRadius);
        setPos(x, y);
    }
    
    

    @Override
    public void paintShape ()
    {
        myEngine.setColor(myColor);
        myEngine.drawOval(x, y, (float)myRadius * 2, (float)myRadius * 2, true, true);
    }

}
