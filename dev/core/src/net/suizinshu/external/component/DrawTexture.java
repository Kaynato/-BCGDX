package net.suizinshu.external.component;

import net.suizinshu.external.Central;
import net.suizinshu.external.StateGraphic;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;

public class DrawTexture extends Component {

	/** Sprite data. */
	public Texture texture;
	
	/** Creatw a sprite with name. */
	public DrawTexture(String name) {
		texture = StateGraphic.get(name);
	}
	
	/** Create a sprite with "Default Texture." Do not do... */
	public DrawTexture() {
		texture = Central.DEFAULT_TEXTURE;
	}
	
}