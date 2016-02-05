package net.suizinshu.external.system;

import net.suizinshu.external.Central;
import net.suizinshu.external.component.*;
import net.suizinshu.external.logic.KeyLogic.KeyBinder;
import net.suizinshu.external.system.SpriteAnimationSystem.AnimationType;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;

/**
 * Dummy system: Entity creation zone.
 * @author Zicheng Gao
 */
public class Factory extends BaseSystem {
	ComponentMapper<DrawTexture> drawTexM;
	ComponentMapper<DrawSubGridTexture> drawSugTexM;
	ComponentMapper<DrawSubGridAnimator> drawSugAnimM;
	
	ComponentMapper<ForcedDepth> depthM;
	
	ComponentMapper<TransformTint> tsTintM;
	ComponentMapper<TransformScale> tsSclM;
	
	ComponentMapper<Position> posM;
	ComponentMapper<Velocity> velM;
	ComponentMapper<Acceleration> aceM;
	
	ComponentMapper<MaxSpeed> maxSpdM;
	
	ComponentMapper<Angle> angM;
	ComponentMapper<AngleVelocity> angVelM;
	
	ComponentMapper<Gravity> gravM;
	
	ComponentMapper<ActiveFriction> fricM;
	ComponentMapper<FrictionWhenEquilibrium> fricWhenEqM;
	
	ComponentMapper<CollisionObject> collObjM;
	ComponentMapper<Cartesian> cartM;
	ComponentMapper<CollisionDetection> collDecM;
	
	ComponentMapper<IsCentered> isCenM;
	ComponentMapper<IsSolid> isSolM;
	
	ComponentMapper<InputBinder> inputM;

	@Override
	protected void processSystem() {}
	
	/**
	 * Temporary - just for initializing test entities for test purposes.
	 */
	public void testInit() {
		
		Entity testbackground = world.createEntity();
		testbackground.edit()
			.add(new DrawTexture("test/Bounds2"))
			.add(new Position(0, 0, 0))
			.add(new ForcedDepth(Central.BACKGROUND_DEPTH));
		
		Entity player = world.createEntity();
		player.edit()
			.add(new DrawTexture("test/numgrid"))
			.add(new DrawSubGridTexture("test/numgrid", 10, 10))
			.add(new DrawSubGridAnimator(AnimationType.UNIDIR, 1, false, false, 1))
			.add(new IsCentered())
			.add(new Position(320, 240, 2))
			.add(new Velocity())
			.add(new Acceleration())
			.add(new ActiveFriction(0.2f))
			.add(new FrictionWhenEquilibrium())
//			.add(new Gravity(0, -0.00001f, 0, true)
			.add(new TransformScale(1, 1))
			.add(new MaxSpeed(2))
			.add(new InputBinder(new KeyBinder(
					Central.bindings.accelMovement(0.1f),
					Central.bindings.rotate46(5),
					Central.bindings.scale1235(0.1f, 0.1f)
					)))
			.add(new Angle())
			.add(new AngleVelocity())
			.add(new CollisionDetection(
					Central.PLAYER_FILTER, 
					(short) (Central.WALL_FILTER | Central.BULLET_FILTER)
					))
			.add(new Debug());
		
		addHitShapeByTex(player, HitPrimType.CUBOID, 0, 1);
	}
	
	//
	//
	//
	
	public enum HitPrimType {
		CUBOID,
		SPHERE,
		CYLINDER,
		CAPSULE,
		CONE
	}
	
	/**
	 * Convenience method for adding a HitShape of BoxShape with the texture dimensions and width.
	 * @param e			entity to add to
	 * @param berth		pixels' affordance given. positive: hitbox smaller than tex, neg: more than tex<br>
	 * @param z			3d size portion if need be ("out of screen")
	 */
	public void addHitShapeByTex(Entity e, HitPrimType type, float berth, float z) {
		if (drawTexM.has(e)) {
			DrawTexture tex = drawTexM.getSafe(e);
			float width;
			float height;
			
			if (drawSugTexM.has(e)) {
				DrawSubGridTexture subGrid = drawSugTexM.get(e);
				width = subGrid.width;
				height = subGrid.height;
			}
			else {
				width = tex.texture.getWidth();
				height = tex.texture.getHeight();
			}
			
			btCollisionShape shape = new btEmptyShape();
			switch (type) {
			case CUBOID:
				Vector3 boxHalfExtents = new Vector3(width, height, z);
				boxHalfExtents.scl(0.5f);
				boxHalfExtents.sub(berth);
				shape = new btBoxShape(boxHalfExtents);
				break;
			case SPHERE:
				float radiusSph = Math.min(width, height)/2 - berth;
				shape = new btSphereShape(radiusSph);
				break;
			case CYLINDER:
				Vector3 halfExtents = new Vector3(width, height, z);
				halfExtents.scl(0.5f);
				halfExtents.sub(berth);
				shape = new btCylinderShape(halfExtents);
				break;
			case CAPSULE:
				float radiusCap = (width/2) - berth;
				float heightCap = (height) - (2 * berth) - (2 * radiusCap);
				shape = new btCapsuleShape(radiusCap, heightCap);
				break;
			case CONE:
				float radiusCone = (width/2) - berth;
				float heightCone = height - (2*berth);
				shape = new btConeShape(radiusCone, heightCone);
				break;
			}
			
			e.edit().add(new CollisionObject(shape));
			
		}
				
	}
	

}
