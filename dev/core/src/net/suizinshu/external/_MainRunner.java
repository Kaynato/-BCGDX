package net.suizinshu.external;

import net.suizinshu.external.component.*;
import net.suizinshu.external.logic.KeyBinder;
import net.suizinshu.external.system.*;
import net.suizinshu.external.system.BindableInputSystem.Bindings;
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
import com.badlogic.gdx.graphics.OrthographicCamera;

public class _MainRunner implements ApplicationListener {

//	private long nanotime;
//	private final long targ60nano = Utils_Timing.nanoTarget(60);
	
	private World world;
	private Bindings bindings;
	private Camera camera;
	
	private FPSLogger log = new FPSLogger();
	
	@Override
	public void create () {
		Central.initialize();
		Manager_Audio.initialize();
		Manager_Keyboard.initialize();
		
		/* Establish references */
		Central.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera = Central.camera;
		camera.translate(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		
		BindableInputSystem inputSystem = new BindableInputSystem();
		bindings = inputSystem.new Bindings();
		
		WorldConfiguration config = new WorldConfigurationBuilder()
			.with(
					new ExitSystem(),
					inputSystem,
					new ApplyPhysicsSystem(), 
					new RenderSpriteSystem(camera)
			      )
			.build();
		
		world = new World(config);
		
		Entity e3 = world.createEntity();
		e3.edit()
			.add(new Sprite("test/Bounds2"))
			.add(new Position(0, 0, 0))
			.add(new IsBackground());
		
		Entity e1 = world.createEntity();
		e1.edit()
			.add(new Sprite("tieman"))
			.add(new Position(320, 240, 2))
			.add(new IsCentered())
			.add(new Velocity())
			.add(new Acceleration())
			.add(new ActiveFriction(0.4f))
//			.add(new Gravity(0, -0.00001f, 0, true))
			.add(new TransformScale(0.3f, 0.3f))
			.add(new MaxSpeed(2))
			.add(new InputBinder(new KeyBinder(
					bindings.accelMovement(0.1f),
					bindings.rotate46(5),
					bindings.scale1235(0.1f, 0.1f)
					)))
			.add(new Angle())
			.add(new AngleVelocity())
			.add(new Debug());
		
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

		// Set goal time at targ60.
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		
		camera.update();

		//		world.setDelta(lastTime);
		world.process();
		
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