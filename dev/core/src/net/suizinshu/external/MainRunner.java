package net.suizinshu.external;

import net.suizinshu.file.Fetch;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class MainRunner implements ApplicationListener {
	
	private PerspectiveCamera camera;
	
	@Override
	public void create () {
		camera = new PerspectiveCamera(90, 640, 480);
		// Set camera direction or something
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		Fetch.removeTemp();
	}
	
}