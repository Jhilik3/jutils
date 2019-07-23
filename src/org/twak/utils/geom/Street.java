package org.twak.utils.geom;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class Street implements Comparable<Street> {

    private Junction p1, p2;
    public double angle;
    private Point3d c1, c2, c3, c4;

    public Street(Junction point1, Junction point2) {
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

    public int compareTo(Street s) {
        return Double.compare(this.angle, s.angle);// (int)(this.angle - s.angle);
    }

    public Junction getOther(Junction p) {
        if (p == p1) {
            return p2;
        } else if (p == p2) {
            return p1;
        } else {
            throw new Error("bad junction");
        }
    }

    public List<Street> createTempStreets() {
        List<Street> streets = new ArrayList<>();
        Street fl, fr, bl, br;

        streets.add(0, this);

        int indexFront = p1.streets.indexOf(this);
        if (((indexFront+1) % p1.streets.size()) < p1.streets.size()) {
            fl = p1.streets.get((indexFront+1) % p1.streets.size());
            streets.add(1, fl);

            int frIndex = indexFront - 1;
            if (frIndex == -1) {
                fr = p1.streets.get(frIndex + p1.streets.size());
                if (fl.compareTo(fr) != 0)
                    streets.add(2, fr);
                else
                    streets.add(2, null);
            } else if (frIndex < p1.streets.size()) {
                fr = p1.streets.get(frIndex);
                if (fl.compareTo(fr) != 0)
                    streets.add(2, fr);
                else
                    streets.add(2, null);
            }
        } else {
            streets.add(1, null);
            streets.add(2, null);
        }

        int indexBack = p2.streets.indexOf(this);
        if (((indexBack+1) % p2.streets.size()) < p2.streets.size()) {
            bl = p2.streets.get((indexBack+1) % p2.streets.size());
            streets.add(3, bl);

            int brIndex = indexBack - 1;
            if (brIndex == -1) {
                br = p2.streets.get(brIndex+p2.streets.size());
                if (bl.compareTo(br) != 0)
                    streets.add(4, br);
                else
                    streets.add(4, null);
            } else if (brIndex < p2.streets.size()) {
                br = p2.streets.get(brIndex);
                if (bl.compareTo(br) != 0)
                    streets.add(4, br);
                else
                    streets.add(4, null);
            }
        } else {
            streets.add(3, null);
            streets.add(4, null);
        }

        for (Street s : streets) {
            if (s != null)
                s.corners();
        }

        return streets;
    }

    public void corners() {
//        List<Point3d> corners = new ArrayList<>();

        // perpendicular vector
        Vector3f vector = new Vector3f((float)-(p2.z-p1.z), (float)(p2.y-p1.y), (float)(p2.x-p1.x));

        Vector3f v = new Vector3f(vector.x*(4/vector.length()), vector.y*(4/vector.length()), vector.z*(4/vector.length()));

        c1 = new Point3d((p1.x+v.x), (p1.y+v.y), (p1.z+v.z));
        c2 = new Point3d((p1.x-v.x), (p1.y-v.y), (p1.z-v.z));
        c3 = new Point3d((p2.x+v.x), (p2.y+v.y), (p2.z+v.z));
        c4 = new Point3d((p2.x-v.x), (p2.y-v.y), (p2.z-v.z));
//        corners.add(c1);
//        corners.add(c2);
//        corners.add(c3);
//        corners.add(c4);
//
//        return corners;
    }

    public Point3d getC1() {
        return c1;
    }

    public Point3d getC2() {
        return c2;
    }

    public Point3d getC3() {
        return c3;
    }

    public Point3d getC4() {
        return c4;
    }

    public Point3d intersect(Point3d p1, Point3d p2, Point3d p3, Point3d p4) {
        double d = (p1.x - p2.x)*(p3.z - p4.z);
        double d2 = (p1.z - p2.z)*(p3.x - p4.x);
        double denom = (p1.x - p2.x)*(p3.z - p4.z) - (p1.z - p2.z)*(p3.x - p4.x);
        double x11 = p1.x*p2.z - p1.z*p2.x;
        double x12 = p3.x-p4.x;
        double x1 = (p1.x*p2.z - p1.z*p2.x)*(p3.x-p4.x);
        double x2 = (p1.x-p2.x)*(p3.x*p4.z - p3.z*p4.x);
        double numx = ((p1.x*p2.z - p1.z*p2.x)*(p3.x-p4.x)) - ((p1.x-p2.x)*(p3.x*p4.z - p3.z*p4.x));
        double numy = (p1.x*p2.z - p1.z*p2.x)*(p3.z-p4.z) - (p1.z-p2.z)*(p3.x*p4.z - p3.z*p4.x);
		Point3d intersection = new Point3d(numx/denom, 0, numy/denom);

		if (denom == 0) {
		    return new Point3d(0,0,0);
        }
		return intersection;
    }

//    public Point3d getUnitVector() {
//        Vector3f vec = getVector();
//        double mag = Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2) + Math.pow(vec.z, 2));
//        float mag = vec.length();
//        return new Point3d(vec.x/mag, vec.y/mag, vec.z/mag);
//    }
}
