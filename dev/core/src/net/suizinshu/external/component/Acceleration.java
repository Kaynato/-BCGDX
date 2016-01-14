package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Acceleration component.
 * Agghhhhhh. Why...
 * @author Zicheng Gao
 */
public class Acceleration extends Component {

	/** Acceleration float vector. */
	public Vector3 vec;
	
	/** Initialize using vector. */
	public Acceleration(Vector3 vec) {
		this.vec = vec;
	}
	
	/** Initialize using 3 floats. */
	public Acceleration(float x, float y, float z) {
		vec = new Vector3(x, y, z);
	}
	
	/** Default zero initialization. */
	public Acceleration() {
		this(0, 0, 0);
	}

}
