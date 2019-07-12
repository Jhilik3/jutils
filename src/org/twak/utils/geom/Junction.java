package org.twak.utils.geom;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Junction extends Point3d {
    public List<Street> streets;

    public Junction(Point3d p1) {
        super(p1);
        streets = new ArrayList<>();
    }

    public Street addStreet(Junction p) {
        Street s = new Street(this, p);
        streets.add(s);
        return s;
    }

    public Street addStreet(Street s) {
        streets.add(s);
        return s;
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
        }

        Collections.sort(streets);
        System.out.println("Streets");
        for (Street s : streets) {
            System.out.println(s.angle);
        }
    }

    public static void main(String[] args) {
        Point3d p1 = new Point3d(0,0,0);
        Point3d p2 = new Point3d(3,1,0);
        Point3d p3 = new Point3d(2,-1,0);
        Point3d p4 = new Point3d(2,2,0);
        Point3d p5 = new Point3d(-2,1,0);
        Junction jun1 = new Junction(p1);
        Junction jun2 = new Junction(p2);
        Junction jun3 = new Junction(p3);
        jun1.addStreet(jun2);
        jun1.addStreet(jun3);
        jun1.addStreet( new Junction( p4) );
        jun1.addStreet( new Junction( p5 ) );
        jun1.order();
    }

    public boolean hasStreetTo(Junction v1) {
        for (Street s : streets) {
            if (s.p1 == this && s.p2 == v1)
                return true;

            if (s.p2 == this && s.p1 == v1)
                return true;
        }

        return false;
    }
}
