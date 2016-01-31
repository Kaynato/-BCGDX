package net.suizinshu.external;

import net.suizinshu.external.system.*;
import net.suizinshu.file.Fetch;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.bullet.Bullet;

public class _MainRunner implements ApplicationListener {

//	private long nanotime;
//	private final long targ60nano = Utils_Timing.nanoTarget(60);
	
	private World world;
	private Camera camera;
	
	private FPSLogger log = new FPSLogger();
	
	@Override
	public void create () {
		Bullet.init();
		
		Central.initialize();
		StateAudio.initialize();
		StateKeyboard.initialize();
		
		/* Establish references */
		Central.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera = Central.camera;
		camera.translate(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		
		BindableInputSystem inputSystem = new BindableInputSystem();
		Central.bindings = inputSystem.new Bindings();
		
		Factory factory = new Factory();
		
		/* Build world */
		WorldConfiguration config = new WorldConfigurationBuilder()
			.with(
					factory,
					new ExitSystem(),
					inputSystem,
					new ApplyPhysicsSystem(),
					new CollideSystem(),
					new SpriteAnimationSystem(),
					new SpriteRenderer(camera)
			      )
			.build();
		
		world = new World(config);
		
		factory.testInit();
		
//		Entity e2 = world.createEntity();
//		e2.edit()
//			.add(new Sprite("cat"))
//			.add(new Position(0, 0, -2));
		
//		Entity e4 = world.createEntity();
//		e4.edit()
//			.add(new Sprite("test/long"))
//			.add(new Position(0, 1f, 0));
		
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		camera.update();

		world.setDelta(Gdx.graphics.getDeltaTime());
		world.process();

		log.log();

		StateKeyboard.dequeue();

	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		StateAudio.dispose();
		StateGraphic.dispose();
		world.dispose();
		Fetch.removeTemp();
	}

}