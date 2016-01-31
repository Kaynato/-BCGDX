package net.suizinshu.external.component;

import com.artemis.Component;


public final class CollisionDetection extends Component {
	
//	/** Arraylist of collision vectors. */
//	public ArrayList<Vector3> collisions = new ArrayList<Vector3>();
	
	public short filterGroup;
	
	public short filterMask;
	
	public CollisionDetection(short filterGroup, short filterMask) {
		this.filterGroup = filterGroup;
		this.filterMask = filterMask;
	}
		
}