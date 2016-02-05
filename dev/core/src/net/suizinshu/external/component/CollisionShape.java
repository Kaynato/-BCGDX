package net.suizinshu.external.component;

import com.artemis.Component;

public class CollisionShape extends Component {

	/*
	 * Have:
	 * enum HITSHAPE of those previous variants.
	 * 
	 * in the collision
	 * have one algorithm for each possible oollsion
	 * of two possible types.
	 */
	
	public enum HitShape {
		SPHERE,
		CUBOID,
		PLANE_FINITE,
		PLANE_INFINITE,
		CONVEX_HULL_PRISM,
		CYLINDER
	}
	
	public CollisionShape() {
		// TODO Auto-generated constructor stub
	}

}
