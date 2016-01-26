package net.suizinshu.external.component;

import net.suizinshu.external.Central;
import net.suizinshu.file.Fetch;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;

public class SpriteTexture extends Component {
	
	/** Creatw a sprite with name. */
	public SpriteTexture(String name) {
		sprite = Fetch.getImg(name);
	}
	
	/** Create a sprite with "Default Texture." Do not do... */
	public SpriteTexture() {
		sprite = Central.DEFAULT_TEXTURE;
	}
	
	/** Sprite data. */
	public Texture sprite;
	
}