package net.suizinshu.external.system;

import net.suizinshu.external.Central;
import net.suizinshu.external.component.*;
import net.suizinshu.external.logic.KeyLogic.KeyBinder;
import net.suizinshu.external.system.SpriteAnimationSystem.AnimationType;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;

/**
 * Dummy system: Entity creation zone.
 * @author Zicheng Gao
 */
public class Factory extends BaseSystem {
	ComponentMapper<DrawTexture> drawTexM;
	ComponentMapper<DrawSubGridTexture> drawSugTexM;
	ComponentMapper<DrawSubGridAnimator> drawSugAnimM;
	
	ComponentMapper<DrawModel> drawModelM;
	
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
	
	ComponentMapper<CollisionShape> collObjM;
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
			.add(new ForcedDepth(Central.BACKGROUND_DEPTH))
			.add(new TransformTint(0, 0, 0, 0));
		
		Entity player = world.createEntity();
		player.edit()
			.add(new DrawTexture("test/numgrid"))
			.add(new DrawSubGridTexture("test/numgrid", 10, 10, 99, 2, 3))
			.add(new DrawSubGridAnimator(AnimationType.BOUNCE, 1, true, false, 1))
//			.add(new IsCentered())
			.add(new Position(0, 0, 2))
			.add(new Velocity())
			.add(new Acceleration())
			.add(new ActiveFriction(0.2f))
			.add(new FrictionWhenEquilibrium())
//			.add(new Gravity(0, -0.00001f, 0, true))
			.add(new TransformScale(1, 1))
			.add(new MaxSpeed(2))
			.add(new LabelString("mover"))
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
		
		Entity block = world.createEntity();
		block.edit()
			.add(new DrawTexture("cat"))
//			.add(new IsCentered())
			.add(new LabelString("block"))
			.add(new Position(100, 100, 2))
			.add(new CollisionDetection(Central.WALL_FILTER, Central.PLAYER_FILTER));
		
		
	}

}
