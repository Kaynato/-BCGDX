package net.suizinshu.external.component;

import com.artemis.Component;


public final class CollisionDetection extends Component {
	
//	/** Arraylist of collision vectors. */
//	public ArrayList<Vector3> collisions = new ArrayList<Vector3>();
	
	public float epsilon;
	
	public CollisionDetection(float epsilon) {
		this.epsilon = epsilon;
	}
	
	public CollisionDetection() {
		this(0);
	}
	
}