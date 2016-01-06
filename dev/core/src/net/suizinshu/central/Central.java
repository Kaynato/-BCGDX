package net.suizinshu.central;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Central {

	public static final String INIT = "Central.",
			TMPDIR;

	
	public static final Texture DEFAULT_TEXTURE = new Texture(Gdx.files.internal("missingsprite.png"));


	static {
		TMPDIR = System.getProperty("java.io.tmpdir");





	}










}
