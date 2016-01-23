package net.suizinshu.external.component;

import net.suizinshu.external.component.interfaces.ActPass;
import net.suizinshu.external.system.ApplyPhysicsSystem;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;


public class Acceleration extends Component implements ActPass {
	
	private Vector3 active, nextActive, passive;
	
	/** 
	 * Vector. Acceleration due to active control.<br>
	 * DO NOT ALTER OUTSIDE OF {@link ApplyPhysicsSystem}.<br>
	 * FOR APPLY-PHYSICS SYSTEM ONLY.<br>
	 * For alteration, use nextActive.
	 */
	public Vector3 active() {return active;}
	
	/** 
	 * Vector. Acceleration due to environmental causes.<br>
	 * For {@link ApplyPhysicsSystem} only, to be affected by certain components.
	 */
	public Vector3 passive() {return passive;}
	
	/** 
	 * Vector. Acceleration queue for the active acceleration vector.
	 * 		Alterable by input events.<br>
	 * Can be affected within {@link ApplyPhysicsSystem} by components 
	 * 		such as {@link ActiveFriction}.
	 */
	public Vector3 nextActive() {return nextActive;}
	
	public Acceleration(float x, float y, float z) {
		this.active = new Vector3(x, y, z);
		this.passive = new Vector3(0, 0, 0);
		nextActive = new Vector3(0, 0, 0);
	}
	
	public Acceleration(Vector3 vec) {
		this.active = vec.cpy();
		nextActive = new Vector3(0, 0, 0);
	}
	
	public Acceleration() {
		this(0, 0, 0);
	}
	
}
