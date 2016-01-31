package net.suizinshu.external;

import net.suizinshu.external.system.BindableInputSystem.Bindings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;


public class Central {

	public static final int		MUS_COUNT 			= 3;
	public static final float	BACKGROUND_DEPTH	= -1e9f;
	
	public static final short	PLAYER_FILTER		= 1 << 6,
								WALL_FILTER			= 1 << 7,
								BULLET_FILTER		= 1 << 8;
	
	public static String INIT,
			TMPDIR;

	
	public static Texture DEFAULT_TEXTURE;
	
	public static Camera camera;
	
	public static Bindings bindings;


	public static void initialize() {
		INIT = "Central.";
		TMPDIR = System.getProperty("java.io.tmpdir");
		
		DEFAULT_TEXTURE = new Texture(Gdx.files.internal("missingsprite.png"));
	}
	
}
