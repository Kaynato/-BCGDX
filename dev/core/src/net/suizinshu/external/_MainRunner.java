package net.suizinshu.external;

import net.suizinshu.external.system.*;
import net.suizinshu.file.Fetch;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.physics.bullet.Bullet;

public class _MainRunner implements ApplicationListener {

//	private long nanotime;
//	private final long targ60nano = Utils_Timing.nanoTarget(60);
	
	private World world;
	private Camera camera;
	private Environment environment;
	
	private FPSLogger log = new FPSLogger();
	
	@Override
	public void create () {
		Bullet.init();
		
		Central.initialize();
		StateAudio.initialize();
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

//		farPerspectiveCam();
		
		StateKeyboard.setInputProcessor();
		
		/* Establish references */
		prepCamera();
		
		SystemInclusiveInput inputSystem = new SystemInclusiveInput();
		Central.bindings = inputSystem.new Bindings();
		
		AdjunctFactory adjunctFactory = new AdjunctFactory();
		
		/* Build world */
		WorldConfiguration config = new WorldConfigurationBuilder()
			.with(
					adjunctFactory,
					new AdjunctExit(),
					inputSystem,
					new SystemPhysicsApply(),
					new SystemInclusiveCollide(),
					new SystemSpriteAnimator(),
					new RendererSprite(camera)
			      )
			.build();
		
		world = new World(config);
		
		adjunctFactory.testInit();
		
	}

	private void prepCamera() {
		Central.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera = Central.camera;
		camera.translate(camera.viewportWidth/2, camera.viewportHeight/2, 0);
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