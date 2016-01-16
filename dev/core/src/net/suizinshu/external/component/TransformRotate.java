package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;


public class TransformRotate extends Component {
	
	/** Rotation quaternion. */
	public Quaternion q;
	
	public TransformRotate(Quaternion q) {
		this.q = q;
	}
	
	public TransformRotate(Vector3 axis, float degrees) {
		this.q = new Quaternion(axis, degrees);	
	}
	
	public TransformRotate(float degrees) {
		this.q = new Quaternion(new Vector3(0, 0, 1), degrees);
	}
	
}
