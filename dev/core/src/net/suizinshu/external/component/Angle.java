package net.suizinshu.external.component;

import com.artemis.Component;


public class Angle extends Component {
	
	/** Degrees. Private to ensure that behavior is regulated. */
	private float deg;
	
	public float deg() {
		return deg; 
	}
	
	public void add(float deg) {
		this.deg += deg;
		constrain();
	}
	
	public void set(float deg) {
		this.deg = deg;
		constrain();
	}
	
	public Angle(float deg) {
		this.deg = deg;
	}
	
	public Angle() {
		this(0);
	}
	
	private void constrain() {
		while (this.deg < 0 || this.deg > 360) {
			if (this.deg < 0)
				this.deg += 360;
			else if (this.deg > 360)
				this.deg -= 360;
		}
	}
//	/** Rotation quaternion. */
//	public Quaternion q;
//	
//	public Angle(Quaternion q) {
//		this.q = q;
//	}
//	
//	public Angle(Vector3 axis, float degrees) {
//		this.q = new Quaternion(axis, degrees);	
//	}
//	
//	public Angle(float degrees) {
//		this.q = new Quaternion(new Vector3(0, 0, 1), degrees);
//	}
	
}
