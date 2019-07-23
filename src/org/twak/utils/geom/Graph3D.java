package org.twak.utils.geom;

import java.util.*;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import org.twak.utils.Cache;
import org.twak.utils.collections.MultiMap;

public class Graph3D extends MultiMap<Point3d, Point3d> {

	public void transform( Matrix4d toOrigin ) {
		
		Set<Point3d> seen = new HashSet<>()	;
		
		Cache<Point3d, Point3d> oldNew = new Cache<Point3d, Point3d>() {

			@Override
			public Point3d create( Point3d i ) {
				Point3d out = new Point3d (i);
				toOrigin.transform( out );
				return out;
			}
		};
		
		MultiMap<Point3d, Point3d> n = new MultiMap<>();
		
		for (Point3d k : keySet())
			for (Point3d v : get(k)) 
				n.put( oldNew.get( k ), oldNew.get( v ));
		
		map = n.map;
	}

	public Set<Junction> getAllDiscrete() {

		Set<Junction> junctions = new HashSet<>();
		Map<Point3d, Junction> p2j = new HashMap<>();

		for (Point3d k : keySet()) {

			if (!p2j.containsKey(k)) {
				Junction j = new Junction(k);
				junctions.add(j);
				p2j.put(k, j);
			}

			for (Point3d v : get(k)) {
				if (!p2j.containsKey(v)) {
					Junction j2 = new Junction(v);
					junctions.add(j2);
					p2j.put(v, j2);
				}
			}
		}

		for (Point3d k : keySet()) {
			Junction k1 = p2j.get(k);

			for (Point3d v : get(k)) {
				Junction v1 = p2j.get(v);

				if (!k1.hasStreetTo(v1)) {
					Street s = new Street(k1, v1);
					v1.addStreet(s);
					k1.addStreet(s);
				}
			}
		}

		for (Junction j : junctions) {
			j.order();
		}

		return junctions;
	}

}
