package jboxGlue;

import jgame.JGColor;
import org.jbox2d.collision.CircleDef;


public class PhysicalObjectMass extends PhysicalObject
{
    private double myRadius;

    public PhysicalObjectMass (String id,
                                 int collisionId,
                                 JGColor color,
                                 double radius,
                                 double mass,
                                 double x,
                                 double y,
                                 double vx,
                                 double vy)
    {
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
                                 double vy)
    {
        super(id, collisionId, gfxname);
        init(radius, mass, x, y, vx, vy);
    }

    private void init (double radius, double mass, double x, double y, double vx, double vy)
    {
        // save arguments
        myRadius = radius;
        int intRadius = (int)radius;
        // make it a circle
        CircleDef shape = new CircleDef();
        shape.radius = (float)radius;
        shape.density = (float)mass;
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
