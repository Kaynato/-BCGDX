package net.suizinshu.external.system;

import java.util.ArrayList;

import net.suizinshu.external.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.utils.Array;

/**
 * Collision handling system! Does the colliding thing.
 * @author Zicheng Gao
 */
public class CollideSystem extends IteratingSystem {

	public static class TempContactListener extends ContactListener {
		@Override
		public boolean onContactAdded(
				int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
			System.out.println("added");
			return true;
		}
		
		@Override
		public void onContactProcessed(int userValue0, boolean match0,
				int userValue1, boolean match1) {
			System.out.println("OK");
		}
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

	ComponentMapper<LabelString> labelM;
	
	private ArrayList<CollisionObject> processingShapes;
	private Array<Position> processingPositions;

	private btCollisionConfiguration collisionConfig;
	private btDispatcher dispatcher;
	private btBroadphaseInterface broadphase;
	private btCollisionWorld collisionWorld;
	private ContactListener contactListener;
	
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
		processingPositions = new Array<Position>();
	}
	
	@Override
	protected void inserted(int entityId) {
		CollisionObject collObj = collObjM.getSafe(entityId);
		CollisionDetection collDec = collm.getSafe(entityId);
		Cartesian cart = prepareCartesian(entityId);
//		System.out.println(labelM.getSafe(entityId).label);
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
		CollisionObject collObj = collObjM.getSafe(entityId);
		Position pos = pm.getSafe(entityId);
		Cartesian cart = prepareCartesian(entityId);

		// Operate on shapes
		// TODO handle scaling
		collObj.object.setWorldTransform(cart.transform);
		
		// Track shapes for disposal / postprocessing if necessary
		processingShapes.add(collObj);
		processingPositions.add(pos);
		
	}

	
	
	@Override
	protected void end() {
		// Anyway, collide.
//		collisionWorld.performDiscreteCollisionDetection();
		for (int i = 0; i < processingShapes.size() - 1; i++) {
			if (checkCollision(processingShapes.get(i).object, processingShapes.get(i + 1).object));
		}
		
		// if it is ok then push intent to move
		for (Position pos : processingPositions) {
			pos.vec.add(pos.intent);
			pos.intent.setZero();
		}
		
		processingShapes.clear();
		processingPositions.clear();
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
		
		cart.transform.translate(pm.getSafe(entityId).intent);
		
		if (angm.has(entityId))
			cart.transform.rotate(angm.getSafe(entityId).q);
		
		return cart;
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
        
        boolean r = numContacts > 0;

        dispatcher.freeCollisionAlgorithm(algorithm.getCPointer());
        result.dispose();
        info.dispose();
        co1.dispose();
        co0.dispose();

        return r;
    }

}
