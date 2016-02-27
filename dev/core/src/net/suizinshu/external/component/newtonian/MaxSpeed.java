package net.suizinshu.external.component.newtonian;

import com.artemis.Component;


public class MaxSpeed extends Component {
	
	public float maxspeed;
	
	public float movecap;
	
	public MaxSpeed(float maxspeed, float movecap) {
		this.maxspeed = maxspeed;
		this.movecap = movecap;
	}
	
	public MaxSpeed(float max) {
		this(max, max);
	}
	
}
