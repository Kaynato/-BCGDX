package net.suizinshu.external.component;

import net.suizinshu.central.Central;
import net.suizinshu.file.Fetch;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;

public class Sprite extends Component {
	
	/** Creatw a sprite with name. */
	public Sprite(String name) {
		sprite = Fetch.getImg(name);
	}
	
	/** Create a sprite with "Default Texture." Do not do... */
	public Sprite() {
		sprite = Central.DEFAULT_TEXTURE;
	}
	
	/** Sprite data. */
	public Texture sprite;
	
	/** Color data. */
	public float r = 1, g = 1, b = 1, a = 1;
	
	/** Transform data. */
	public float rotation,
	scaleY = 1,
	scaleX = 1;
	
}