package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;


public class Acceleration extends Component {
	
	/** Vector. */
	public Vector3 vec;
	
	/** Queue. */
	public Vector3 queue;
	
	public Acceleration(float x, float y, float z) {
		this.vec = new Vector3(x, y, z);
		queue = new Vector3(0, 0, 0);
	}
	
	public Acceleration(Vector3 vec) {
		this.vec = vec.cpy();
		queue = new Vector3(0, 0, 0);
	}
	
	public Acceleration() {
		this(0, 0, 0);
	}
	
}
