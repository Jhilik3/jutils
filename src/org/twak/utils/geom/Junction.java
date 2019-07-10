package org.twak.utils.geom;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class Junction extends Point3d {
    public List<Street> streets;

    public Junction(Point3d p1) {
        super(p1);
        streets = new ArrayList<>();
    }

    public void addStreet(Point3d p) {
        Street s = new Street(this, p);
        streets.add(s);
    }

    public void order() {
        Street s1 = streets.get(0);
        for (Street s2 : streets) {
            // cross product
            Vector3f cross = new Vector3f();
            cross.cross(s1.getVector(), s2.getVector());
            System.out.println(cross.toString());

            // angle
            double angle;
            if (cross.z > 0) {
                angle = 2*Math.PI - s1.getVector().angle(s2.getVector());
            } else {
                angle = s1.getVector().angle(s2.getVector());
            }
            s2.angle = angle;
            System.out.println(angle);

//                double dotProduct = (s1.getUnitVector().x*s2.getUnitVector().x)+(s1.getUnitVector().y*s2.getUnitVector().y)+(s1.getUnitVector().z*s2.getUnitVector().z);
//                double angle = Math.acos(dotProduct);
//                System.out.println(s1.getP1().toString() + s1.getP2().toString()+ " and " + s2.getP1().toString() + s2.getP2().toString()+ angle);

        }
    }

    public static void main(String[] args) {
        Point3d p1 = new Point3d(0,0,0);
        Point3d p2 = new Point3d(3,1,0);
        Point3d p3 = new Point3d(2,-1,0);
        Junction jun1 = new Junction(p1);
        Junction jun2 = new Junction(p2);
        Junction jun3 = new Junction(p3);
        jun1.addStreet(p2);
        jun1.addStreet(p3);
        jun1.order();
    }
}
