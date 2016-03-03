package net.suizinshu.external.system;

import java.util.ArrayList;

import net.suizinshu.external.component.Debug;
import net.suizinshu.external.component.IsCentered;
import net.suizinshu.external.component.LabelString;
import net.suizinshu.external.component.collision.Cartesian;
import net.suizinshu.external.component.collision.CollisionBinding;
import net.suizinshu.external.component.collision.CollisionDetection;
import net.suizinshu.external.component.collision.CollisionObject;
import net.suizinshu.external.component.newtonian.Angle;
import net.suizinshu.external.component.newtonian.Position;
import net.suizinshu.external.component.render.TransformScale;
import net.suizinshu.external.util.DebugAABBObject;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.utils.Array;

/**
 * Collision handling system! Does the colliding thing.
 * @author Zicheng Gao
 */
public class SystemInclusiveCollide extends IteratingSystem {

	ComponentMapper<CollisionBinding> collBindM;
	ComponentMapper<Debug> debugM;
	
	public class TempContactListener extends ContactListener {
		
		@Override
		public boolean onContactAdded(
				int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
			
			collBindM.get(userValue0).script.perform(world, userValue0, userValue1);
			return true;
		}
		
	}
	
	/* Absolutely, most certainly, safe. */
	@SuppressWarnings("unchecked")
	public SystemInclusiveCollide() {
		super(Aspect.all(CollisionDetection.class, Position.class).one(CollisionObject.class));
	}

	ComponentMapper<Position> pm;
	ComponentMapper<Angle> angm;
	ComponentMapper<TransformScale> tsm;
	ComponentMapper<IsCentered> isCenterM;
	
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
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfig);
		
		contactListener = new TempContactListener();
		
		processingShapes = new ArrayList<CollisionObject>();
		processingPositions = new Array<Position>();
	}
	
	@Override
	protected void inserted(int entityId) {
		CollisionObject collObj = collObjM.getSafe(entityId);
		CollisionDetection collDec = collm.getSafe(entityId);
		Cartesian cart = prepareCartesian(entityId);
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
		
		Vector3 tempPos = new Vector3(0,0,0);
		
		collObj.object.getWorldTransform().getTranslation(tempPos);
		
//		System.out.print(entityId + ": " + tempPos + "\t");
		
		if (debugM.has(entityId)) {
			Matrix4 aabbMatrix = new Matrix4();
			Vector3 aabbMin = new Vector3();
			Vector3 aabbMax = new Vector3();
			Vector3 aabbDepth = aabbMax.cpy().sub(aabbMin);
			collObj.object.getCollisionShape().getAabb(aabbMatrix, aabbMin, aabbMax);
			RendererDirective.debugDrawQueue.add(
					new DebugAABBObject(aabbMatrix, aabbMin, aabbDepth));
			
		}
		// Handle centering.
		
		// Track shapes for disposal / post-processing if necessary
		processingShapes.add(collObj);
		processingPositions.add(pos);
		
	}

	
	
	@Override
	protected void end() {
		// Anyway, collide.
		collisionWorld.performDiscreteCollisionDetection();
		
		// if it is ok then push intent to move
		for (Position pos : processingPositions) {
			pos.vec.add(pos.intent);
			pos.intent.setZero();
		}
		
		processingShapes.clear();
		processingPositions.clear();
		
		System.out.println();
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
		
//		if (isCenterM.has(entityId))
//			cart.transform.translate()
			
		cart.transform.translate(pm.getSafe(entityId).intent);
		
		if (angm.has(entityId))
			cart.transform.rotate(angm.getSafe(entityId).q);
		
		return cart;
	}

}
