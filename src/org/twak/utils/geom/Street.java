package org.twak.utils.geom;

import javax.vecmath.Point3d;

public class Street {

    public Point3d p1, p2;

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

    public Point3d getVector() {
        return new Point3d(p1.x-p2.x, p1.y-p2.y, p1.z-p2.z);
    }

    public Point3d getUnitVector() {
        Point3d vec = getVector();
        double mag = Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2) + Math.pow(vec.z, 2));
        return new Point3d(vec.x/mag, vec.y/mag, vec.z/mag);
    }
}
