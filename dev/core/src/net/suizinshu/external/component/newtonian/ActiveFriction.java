package net.suizinshu.external.component.newtonian;

import com.artemis.Component;


public final class ActiveFriction extends Component {
	
	/** Coefficient of friction: 1: Impossible to move. 0: No friction. */
	public final float coefficient;
	
	/** Threshold to round to zero. */
	public final float epsilon = 0.001f;
	
	/** Boolean active. */
	public boolean active = true;
	
	public ActiveFriction(float mu) {
		this.coefficient = mu;
	}
	
}
