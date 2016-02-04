package net.suizinshu.external.desktop;

import net.suizinshu.external._MainRunner;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 480;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		
		config.title = "TEST: GRAVITY OF THE SITUATION";
//		config.addIcon(path, fileType);
		config.width = WINDOW_WIDTH;
		config.height = WINDOW_HEIGHT;
		config.resizable = false;
		
//		config.backgroundFPS = 2000;
//		config.foregroundFPS = 2000;
//		config.vSyncEnabled = false;
		
		new LwjglApplication(new _MainRunner(), config);
	}
	
}
