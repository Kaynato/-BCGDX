package net.suizinshu.external.component;

import com.artemis.Component;


public final class AngleVelocity extends Component {
	
//	/** Rotation quaternion. */
//	public Quaternion q;
//	
//	/** Utility quaternion for operations. */
//	public Quaternion op;
	
	public float deg;
	
	public AngleVelocity(float deg) {
		this.deg = deg;
	}
	
	public AngleVelocity() {
		this(0);
	}
	
	
//	public AngleVelocity(Quaternion q) {
//		this.q = q;
//		this.op = new Quaternion();
//	}
//	
//	public AngleVelocity(Vector3 axis, float degrees) {
//		this(new Quaternion(axis, degrees));
//	}
//	
//	public AngleVelocity(float degrees) {
//		this(Vector3.Z, degrees);
//	}
//	
//	public AngleVelocity() {
//		this(0);
//	}
		
}
