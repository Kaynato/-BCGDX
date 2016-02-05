package net.suizinshu.external.system;

import java.util.ArrayList;

import net.suizinshu.external.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.*;

/**
 * Collision handling system! Does the colliding thing.
 * @author Zicheng Gao
 */
public class CollideSystem extends IteratingSystem {

	public static class TempContactListener extends ContactListener {
		
	}
	
	/* Absolutely, most certainly, safe. */
	@SuppressWarnings("unchecked")
	public CollideSystem() {
		super(Aspect.all(CollisionDetection.class, Position.class).one(CollisionObject.class));
	}

	ComponentMapper<Position> pm;
	ComponentMapper<Angle> angm;
	ComponentMapper<TransformScale> tsm;
	
	ComponentMapper<Cartesian> cartm;
	ComponentMapper<CollisionObject> collObjM;
	ComponentMapper<CollisionDetection> collm;

	ArrayList<CollisionObject> processingShapes;

	btCollisionConfiguration collisionConfig;
	btDispatcher dispatcher;
	btBroadphaseInterface broadphase;
	btCollisionWorld collisionWorld;
	ContactListener contactListener;
	
	@Override
	protected void initialize() {
		// Config
		collisionConfig = new btDefaultCollisionConfiguration();
		
		// Sends info to config
		dispatcher = new btCollisionDispatcher(collisionConfig);
		
		// Broad tree: "Dynamically Bounding Volume Tree" broadphase.
		broadphase = new btDbvtBroadphase();
		
		// Combine 'em
		collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfig);

		// Fires when collision happens - good for scripts.
		contactListener = new TempContactListener();
		
		processingShapes = new ArrayList<CollisionObject>();
	}
	
	@Override
	protected void inserted(int entityId) {
		CollisionObject collObj = collObjM.getSafe(entityId);
		CollisionDetection collDec = collm.getSafe(entityId);
		Cartesian cart = prepareCartesian(entityId);
		// Add and setup
		collisionWorld.addCollisionObject(collObj.object, collDec.filterGroup, collDec.filterMask);		
		
		collObj.object.setWorldTransform(cart.transform);
	}
	
	@Override
	protected void removed(int entityId) {
		CollisionObject collObj = collObjM.getSafe(entityId);
		collisionWorld.removeCollisionObject(collObj.object);
	}

	@Override
	protected void process(int entityId) {
		Position pos = pm.getSafe(entityId);
		Cartesian cart = prepareCartesian(entityId);
		
		// Track shapes for disposal if necessary
		CollisionObject collObj = collObjM.getSafe(entityId);
		processingShapes.add(collObj);

		// Operate on shapes
		// TODO handle scaling
		collObj.object.setWorldTransform(cart.transform);
		
		// We'll need a listener
		collisionWorld.performDiscreteCollisionDetection();
		
		// if it is ok then push intent to move
		pos.vec.add(pos.intent);
		pos.intent.setZero();
	}

	@Override
	protected void end() {
		processingShapes.clear();
	}

	@Override
	protected void dispose() {
		for (CollisionObject shape : processingShapes)
			shape.dispose();
		
		collisionWorld.dispose();
		broadphase.dispose();
		dispatcher.dispose();
		collisionConfig.dispose();

		contactListener.dispose();
	}

	private Cartesian prepareCartesian(int entityId) {
		// Ensure presence of cartesian (Thus manually adding it's unnecessary)
		if (!cartm.has(entityId))
			cartm.create(entityId);

		// Cartesian becomes worldtransform
		Cartesian cart = cartm.getSafe(entityId);
		if (cart.transform == null)
			cart.transform = new Matrix4();
		
		cart.transform.setToTranslation(pm.getSafe(entityId).vec);
		if (angm.has(entityId))
			cart.transform.rotate(angm.getSafe(entityId).q);
		
		return cart;
	}

}
