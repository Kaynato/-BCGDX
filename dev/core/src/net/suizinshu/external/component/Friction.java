package net.suizinshu.external.component;

import com.artemis.Component;


public class Friction extends Component {
	
	/** Coefficient of friction: 1: Impossible to move. 0: No friction. */
	public float mu = 0;
	
	/** Threshold to round to zero. */
	public float epsilon = 0.001f;
	
	public Friction(float mu) {
		this.mu = mu;
	}
	
}