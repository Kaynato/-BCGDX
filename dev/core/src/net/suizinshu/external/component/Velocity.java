package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Velocity Component.
 * It's things like this which make me doubt ECS.
 * I mean, it's literally.... I could have just, y'know, had a turtle tower of Vector3?
 * @author Zicheng Gao
 */
public class Velocity extends Component {

	/** Velocity float vector. */
	public Vector3 vec;
	
	/** For queued velocity whatevers. */
	public Vector3 queue;
	
	/** Initialize using vector. */
	public Velocity(Vector3 vec) {
		this.vec = vec;
	}
	
	/** Initialize using 3 floats. */
	public Velocity(float x, float y, float z) {
		vec = new Vector3(x, y, z);
		queue = new Vector3(0, 0, 0);
	}
	
	/** Default zero initialization. */
	public Velocity() {
		this(0, 0, 0);
	}

}
