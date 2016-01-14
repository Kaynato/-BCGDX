package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Position component.
 * @author Zicheng Gao
 */
public class Position extends Component {

	/** Position float vector. */
	public Vector3 vec;

	/** Initialize a position component from a vector3. */
	public Position(Vector3 init) {
		vec = init;
	}
	
	/** Initialize a position component from floats. */
	public Position(float x, float y, float z) {
		vec = new Vector3(x, y, z);
	}

	/** Initialize the default 0,0 position component. */
	public Position() {
		this(0,0,0);
	}

}