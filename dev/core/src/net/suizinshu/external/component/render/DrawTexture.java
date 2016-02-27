package net.suizinshu.external.component.render;

import net.suizinshu.external.Central;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;

public final class DrawTexture extends Component {

	/** Sprite data. */
	public Texture texture;
	
	/** Creatw a sprite with name. */
	public DrawTexture(Texture texture) {
		this.texture = texture;
	}
	
	/** Create a sprite with "Default Texture." Do not do... */
	public DrawTexture() {
		texture = Central.DEFAULT_TEXTURE;
	}
	
}