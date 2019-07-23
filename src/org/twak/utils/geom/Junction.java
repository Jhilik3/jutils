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
            System.out.println("Cross product: " + cross.toString());

            // angle
            double angle;
            if (cross.y > 0) {
                angle = 2*Math.PI - s1.getVector().angle(s2.getVector());
            } else {
                angle = s1.getVector().angle(s2.getVector());
            }
            s2.angle = angle;
            System.out.println("Angle: " + angle);
        }

        Collections.sort(streets);
        System.out.println("Streets");
        for (Street s : streets) {
            System.out.println(s.angle);
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        Point3d p1 = new Point3d(0,0,0);
        Point3d p2 = new Point3d(3,0,1);
        Point3d p3 = new Point3d(2,0,-1);
        Point3d p4 = new Point3d(2,0,2);
        Point3d p5 = new Point3d(-2,0,1);
        Point3d p6 = new Point3d(2,0,1);
        Point3d p7 = new Point3d(1,0,3);
        Point3d p8 = new Point3d(3,0,3);
        Junction jun1 = new Junction(p1);
        Junction jun2 = new Junction(p2);
        Junction jun3 = new Junction(p3);
        Street street = jun1.addStreet(jun2);
        Street street2 = jun1.addStreet(jun3);
        jun1.addStreet( new Junction( p4) );
        jun1.addStreet( new Junction( p5 ) );
        jun1.order();

        Point3d i = street.intersect(p4, p6, p7, p8);

//        List<Street> s = street.createTempStreets();
//        Point3d i = street.intersect(s.get(0).getC2(), s.get(0).getC4(), s.get(1).getC1(), s.get(1).getC3());
        System.out.println(i.toString());
    }

    public boolean hasStreetTo(Junction v1) {
        for (Street s : streets) {
            if (s.getP1() == this && s.getP2() == v1)
                return true;

            if (s.getP2() == this && s.getP1() == v1)
                return true;
        }

        return false;
    }
}
