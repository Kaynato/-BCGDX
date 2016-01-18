package net.suizinshu.external;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;


public class Central {

	public static int MUS_COUNT;
	
	public static String INIT,
			TMPDIR;

	
	public static Texture DEFAULT_TEXTURE;
	
	public static Camera camera;


	public static void initialize() {
		INIT = "Central.";
		TMPDIR = System.getProperty("java.io.tmpdir");
		
		DEFAULT_TEXTURE = new Texture(Gdx.files.internal("missingsprite.png"));
		
		MUS_COUNT = 3;
		

	}
	
}
