package org.twak.utils.geom;

import javax.vecmath.Point3d;
import java.util.ArrayList;
import java.util.List;

public class Junction {
    public Point3d point;
    public List<Street> streets;

    public Junction(Point3d p) {
        point = p;
        streets = new ArrayList<>();
    }

    public void addStreet(Point3d p) {
        Street s = new Street(point, p);
        streets.add(s);
    }

    public void order() {
        for (Street s1 : streets) {
            for (Street s2 : streets) {
                double dotProduct = (s1.getUnitVector().x*s2.getUnitVector().x)+(s1.getUnitVector().y*s2.getUnitVector().y)+(s1.getUnitVector().z*s2.getUnitVector().z);
                double angle = Math.acos(dotProduct);


            }
        }
    }
}
