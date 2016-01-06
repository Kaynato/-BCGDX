package net.suizinshu.external.component;

import com.artemis.Component;

/**
 * Position component.
 * @author Zicheng Gao
 */
public class Position extends Component {

	/** X and Y position floats. */
	public float x, y;

	/** Initialize a position component. */
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** Initialize the default 0,0 position component. */
	public Position() {
		this(0,0);
	}

}