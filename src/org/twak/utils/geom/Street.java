package org.twak.utils.geom;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Street {

    public Point3d p1, p2;
    public double angle;

    public Street(Point3d point1, Point3d point2) {
        p1 = point1;
        p2 = point2;
    }

    public Point3d getP1() {
        return p1;
    }

    public Point3d getP2() {
        return p2;
    }

    public Vector3f getVector() {
        return new Vector3f((float)(p1.x-p2.x), (float)(p1.y-p2.y), (float)(p1.z-p2.z));
    }

    public Point3d getUnitVector() {
        Vector3f vec = getVector();
//        double mag = Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2) + Math.pow(vec.z, 2));
        float mag = vec.length();
        return new Point3d(vec.x/mag, vec.y/mag, vec.z/mag);
    }
}
