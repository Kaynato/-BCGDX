package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public final class Angle extends Component {
	
	/** Rotation quaternion. */
	public Quaternion q;
	
	/** Axis for reference and editing. */
	public Vector3 axis;
	
	public float deg() {
		return q.getAngleAround(axis);
	}
	
	public void add(float deg) {
		q.setFromAxis(axis, q.getAngleAround(axis) + deg);
	}
	
	public void set(float degrees) {
		q.setFromAxis(axis, degrees);
	}
	
	public Angle(Quaternion q) {
		this.q = q.cpy();
	}
	
	public Angle(Vector3 axis, float degrees) {
		this.q = new Quaternion(axis, degrees);
		this.axis = axis;
	}
	
	public Angle(float degrees) {
		this(Vector3.Z, degrees);
	}
	
	public Angle() {
		this(0);
	}
	
//	private void constrain() {
//		while (this.deg < 0 || this.deg > 360) {
//			if (this.deg < 0)
//				this.deg += 360;
//			else if (this.deg > 360)
//				this.deg -= 360;
//		}
//	}
	
}
