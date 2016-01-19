package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;


public class Gravity extends Component {
	
	/** Whether in effect. */
	public boolean active;
	
	/** Acceleration due to gravity. NOT FORCE. */
	public Vector3 accel;
	
	public Gravity(Vector3 force) {
		this.accel = force;
		this.active = false;
	}
	
}
