package net.suizinshu.external.component.newtonian;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;


public class Gravity extends Component {
	
	/** Whether in effect. */
	public boolean active;
	
//	/** Whether this is added to the unlimited velocity vector or not. */
//	public boolean unlim;
	
	/** Acceleration due to gravity. NOT FORCE. */
	public Vector3 accel;
	
	public Gravity(float x, float y, float z, boolean active) {
		accel = new Vector3(x, y, z);
		this.active = active;
//		this.unlim = unlim;
	}
	
	public Gravity(Vector3 force, boolean active) {
		this(force.x, force.y, force.z, active);
	}
	
	public Gravity(Vector3 force) {
		this(force, true);
	}
	
}
