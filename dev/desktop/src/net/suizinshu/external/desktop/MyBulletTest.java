package net.suizinshu.external.desktop;

import org.lwjgl.input.Keyboard;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;

public class MyBulletTest implements ApplicationListener {

	/**
	 * Start bullet by itself,
	 * Figure it out,
	 * Get it to work,
	 * Then move it over to Artemis piece-by-piece
	 */
	
	boolean useMethodCollision = false;
	
	private PerspectiveCamera cam;
	private CameraInputController camController;
	private ModelBatch modelBatch;
	private Environment environment;

//	private Array<CuboidModel> instances;
	private IntMap<CuboidModel> instances;
	
	private btCollisionConfiguration collisionConfig;
	private btDispatcher dispatcher;
	private btBroadphaseInterface broadphase;
	private btCollisionWorld collisionWorld;
	private ContactListener contactListener;
	
	private CuboidModel big;
	private CuboidModel tiny;

	class MyContactListener extends ContactListener {
		@Override
		public boolean onContactAdded (int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
			System.out.print("tick");
			instances.get(userValue0).translate.add(5,5,5);
			return true;
		}
	}
	
	static final class CuboidModel implements Disposable {
		public Vector3 translate;
		public ModelInstance modelInst;
		public final btCollisionObject body;
		public btCollisionWorld collisionWorld;
		public boolean moving;
		public final int id;
		
		public CuboidModel (ModelBuilder mb, float width, float height, float z, float berth, Vector3 translate, Color color, int id) {
			this.id = id;
			this.translate = translate;
			body = new btCollisionObject();
			
			Vector3 boxHalfExtents = new Vector3(width, height, z);
			boxHalfExtents.scl(0.5f);
			boxHalfExtents.sub(berth);
			btCollisionShape shape = new btBoxShape(boxHalfExtents);
			body.setCollisionShape(shape);
			body.setUserValue(id);
			body.setCollisionFlags(body.getCollisionFlags() 
					| btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
			
			Model model = mb.createBox(
					width, height, z, 
					new Material(ColorAttribute.createDiffuse(color)), Usage.Position | Usage.Normal);
			modelInst = new ModelInstance(model);
			
			updatePosition();
		}
		
		public void updatePosition() {
			modelInst.transform.setTranslation(translate);
			body.setWorldTransform(modelInst.transform);
			System.out.println(body.getWorldTransform().getTranslation(translate));
		}
		
		public void addToWorld(btCollisionWorld collisionWorld) {
			this.collisionWorld = collisionWorld;
			collisionWorld.addCollisionObject(body);
		}
		
		public void removeFromWorld() {
			collisionWorld.removeCollisionObject(body);
			collisionWorld = null;
		}

		@Override
		public void dispose () {
			body.dispose();
		}

	}
	
	@Override
	public void create() {
		Bullet.init();
		
		instances = new IntMap<CuboidModel>(16);
		
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0f, 30f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);
		
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfig);
		contactListener = new MyContactListener();
		
		ModelBuilder mb = new ModelBuilder();
		
		big = new CuboidModel(mb, 5, 5, 5, 0, new Vector3(0,0,0), Color.BLUE, 0001);
		tiny = new CuboidModel(mb, 1, 1, 1, 0, new Vector3(10,10,0), Color.RED, 0002);
		
		instances.put(big.id, big);
		instances.put(tiny.id, tiny);
		
		short flag = 0x1;
		
		collisionWorld.addCollisionObject(big.body, flag, flag);
		collisionWorld.addCollisionObject(tiny.body, flag, flag);
		
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void render() {
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			useMethodCollision = true;
		else
			useMethodCollision = false;
		
//		final float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());
		float speed = 0.2f;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			tiny.translate.add(0, speed, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			tiny.translate.add(0, -speed, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			tiny.translate.add(-speed, 0, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			tiny.translate.add(speed, 0, 0);
		
		big.updatePosition();
		tiny.updatePosition();
		
		if (useMethodCollision)
			checkCollision(big.body, tiny.body);
		else
			collisionWorld.performDiscreteCollisionDetection();
		
		camController.update();

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(big.modelInst, environment);
		modelBatch.render(tiny.modelInst, environment);
		modelBatch.end();
		
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		collisionWorld.dispose();
		broadphase.dispose();
		dispatcher.dispose();
		collisionConfig.dispose();

		contactListener.dispose();
		big.dispose();
		tiny.dispose();
	}
	
	public static void main(String[] args) {
		new LwjglApplication(new MyBulletTest());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean checkCollision(btCollisionObject obj0, btCollisionObject obj1) {
        CollisionObjectWrapper co0 = new CollisionObjectWrapper(obj0);
        CollisionObjectWrapper co1 = new CollisionObjectWrapper(obj1);

        btCollisionAlgorithm algorithm = dispatcher.findAlgorithm(co0.wrapper, co1.wrapper);

        btDispatcherInfo info = new btDispatcherInfo();
        btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);
        
        /**
         * Even though the worldtransform says it's in the right place, the collision says that I'm
         * Colliding with a cat that hasn't been translated from the origin at all...
         */
        
        algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);
        
        int numContacts = result.getPersistentManifold().getNumContacts();
        System.out.println(numContacts );// TODO
        boolean r = numContacts > 0;

        dispatcher.freeCollisionAlgorithm(algorithm.getCPointer());
        result.dispose();
        info.dispose();
        co1.dispose();
        co0.dispose();
        
        return r;
    }

}
