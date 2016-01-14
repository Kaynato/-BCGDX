package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Physics component.
 * @author Zicheng Gao
 */
public class Physics extends Component {

	/** Mass of entity. */
	public float mass;
	
	/** Net force acting on entity. */
	public Vector3 netforce;
	
	public Physics(float mass, Vector3 netforce) {
		this.mass = mass;
		this.netforce = netforce;
	}
	
	public Physics(float mass) {
		this(mass, new Vector3(0,0,0));
	}
	
	public Physics() {
		this(0, new Vector3(0,0,0));
	}
	
	
}
