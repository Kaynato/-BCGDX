package net.suizinshu.external.component.collision;

import com.artemis.Component;


public final class CollisionDetection extends Component {
	
	public short filterGroup;
	
	public short filterMask;
	
	public CollisionDetection(short filterGroup, short filterMask) {
		this.filterGroup = filterGroup;
		this.filterMask = filterMask;
	}
		
}
