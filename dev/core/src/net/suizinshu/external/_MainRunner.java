package net.suizinshu.external;

import net.suizinshu.external.component.Position;
import net.suizinshu.external.component.SpriteDecal;
import net.suizinshu.external.component.TransformRotate;
import net.suizinshu.external.system.RenderPlaneSystem;
import net.suizinshu.external.system.RenderSpriteSystem;
import net.suizinshu.file.Fetch;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class _MainRunner implements ApplicationListener {

	private long nanotime;
	private final long targ60nano = Utils_Timing.nanoTarget(60);
	
	private Camera camera;
	
	private World world;
	
	private FPSLogger log = new FPSLogger();
	
	@Override
	public void create () {
		Central.initialize();
		Manager_Audio.initialize();
		Manager_Keyboard.initialize();

		camera = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		camera.near = 0.1f;
		camera.far = 300f;
		camera.position.set(0f, 1f, 0f);
		camera.lookAt(0, 0f, 0f);
		camera.update();
		
		WorldConfiguration config = new WorldConfigurationBuilder()
			.with(new RenderSpriteSystem(), new RenderPlaneSystem(camera))
			.build();
		
		world = new World(config);
		
//		Entity e1 = world.createEntity();
//		e1.edit()
//			.add(new SpriteDecal("cat"))
//			.add(new Position(0.5f, 0.5f, 0))
//			.add(new TransformScale(0.3f, 0.3f))
//			.add(new DoesAlwaysFaceCamera());
		
//		Entity e2 = world.createEntity();
//		e2.edit()
//			.add(new SpriteDecal("cat"))
//			.add(new Position(0, 0, 0))
//			.add(new DoesAlwaysFaceCamera());
		
		Entity e3 = world.createEntity();
		e3.edit()
			.add(new SpriteDecal("test/Bounds2"))
			.add(new Position(0, 0, 0))
			.add(new TransformRotate(new Vector3(-1, 0, 0), 90));
		
//		Entity e4 = world.createEntity();
//		e4.edit()
//			.add(new SpriteDecal("test/long"))
//			.add(new Position(0, 1f, 0));
		
	}

	@Override
	public void render () {

//		System.out.println(Gdx.graphics.getFramesPerSecond());
		// Set goal time at targ60.
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		camera.update();

		//		world.setDelta(lastTime);
		world.process();
		
//		_Test2.render(camera);
		log.log();
		Manager_Keyboard.dequeue();

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