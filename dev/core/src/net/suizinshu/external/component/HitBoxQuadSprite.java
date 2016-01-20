package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;


public class HitBoxQuadSprite extends Component {
	
	public Vector2 sw;
	public Vector2 se;
	public Vector2 ne;
	public Vector2 nw;
	
	public HitBoxQuadSprite() {
		sw = new Vector2(0, 0);
		se = new Vector2(0, 0);
		ne = new Vector2(0, 0);
		nw = new Vector2(0, 0);
	}
	
}
