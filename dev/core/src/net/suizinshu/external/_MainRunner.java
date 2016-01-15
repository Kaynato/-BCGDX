package net.suizinshu.external;

import net.suizinshu.central.Central;
import net.suizinshu.external.component.Position;
import net.suizinshu.external.component.Sprite;
import net.suizinshu.external.system.SpriteRenderSystem;
import net.suizinshu.file.Fetch;

import com.artemis.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class _MainRunner implements ApplicationListener {

	private long nanotime;
	private final long targ60nano = Utils_Timing.nanoTarget(60);

	private PerspectiveCamera camera;
	private World world;

	@Override
	public void create () {
		Central.initialize();
		Manager_Audio.initialize();




		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(10, 10, 10);
		camera.lookAt(0, 0, 0);
		camera.near = 1f;
		camera.far = 100f;
		camera.update();
		// Set camera direction or something

		WorldConfiguration config = new WorldConfigurationBuilder()
			.with(new SpriteRenderSystem(camera))
			.build();

		world = new World(config);

		Entity e = world.createEntity();
		e.edit()
			.add(new Sprite("cat"))
			.add(new Position(40, 60, 0));




	}

	@Override
	public void render () {


		// Set goal time at targ60. 

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0.4f, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		//		world.setDelta(lastTime);
		world.process();



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