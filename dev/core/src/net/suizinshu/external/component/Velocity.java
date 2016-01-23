package net.suizinshu.external.component;

import net.suizinshu.external.component.interfaces.ActPass;
import net.suizinshu.external.system.ApplyPhysicsSystem;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Velocity Component.
 * It's things like this which make me doubt ECS.
 * I mean, it's literally.... I could have just, y'know, had a turtle tower of Vector3?
 * @author Zicheng Gao
 */
public class Velocity extends Component implements ActPass {

	private Vector3 active, nextActive, passive;
	
	/** Velocity float vector due to active control.<br>
	 * DO NOT ALTER OUTSIDE OF {@link ApplyPhysicsSystem}.<br>
	 * FOR APPLY-PHYSICS SYSTEM ONLY.<br>
	 * For alteration, use nextActive.
	 */
	public Vector3 active() {return active;}

	/** 
	 * Velocity float vector due to environmental causes.<br>
	 * For {@link ApplyPhysicsSystem} only, to be affected by certain components.
	 */
	public Vector3 passive() {return passive;}

	/** 
	 * Velocity float vector queue for the active velocity vector.
	 * 		Alterable by input events.<br>
	 * Can be affected within {@link ApplyPhysicsSystem} by components 
	 * 		such as {@link MaxSpeed}.
	 */
	public Vector3 nextActive() {return nextActive;}
	
	/** Initialize using 3 floats. */
	public Velocity(float x, float y, float z) {
		active = new Vector3(x, y, z);
		passive = new Vector3(0, 0, 0);
		nextActive = new Vector3(0, 0, 0);
	}
	
	/** Initialize using vector. */
	public Velocity(Vector3 vec) {
		this(vec.x, vec.y, vec.z);
	}
	
	/** Default zero initialization. */
	public Velocity() {
		this(0, 0, 0);
	}

}
