package net.suizinshu.external.component.render;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;


public class TransformTint extends Component {

	public Color tint;
	
	public TransformTint(float r, float g, float b, float a) {
		tint = new Color(r, g, b, a);
	}
	
	public TransformTint(Color tint) {
		this.tint = tint;
	}
	
}
