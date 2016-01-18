package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;


public class Gravity extends Component {
	
	public Vector3 force;
	
	public Gravity(Vector3 force) {
		this.force = force;
	}
	
}
