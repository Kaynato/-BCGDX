package net.suizinshu.external.component.newtonian;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Position component.
 * @author Zicheng Gao
 */
public class Position extends Component {

	/** Position float vector that is limited by MaxSpeed. */
	public Vector3 vec;
	
	/** 
	 * Vector pointing from the current position to the intended position.<br>
	 * For collision-checking.
	 */
	public Vector3 intent;

	/** Initialize a position component from floats. */
	public Position(float x, float y, float z) {
		vec = new Vector3(x, y, z);
		intent = new Vector3(0, 0, 0);
	}
	
	/** Initialize a position component from a vector3. */
	public Position(Vector3 init) {
		this(init.x, init.y, init.z);
	}
	
	/** Initialize the default 0,0 position component. */
	public Position() {
		this(0,0,0);
	}

}