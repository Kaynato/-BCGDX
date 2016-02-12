package net.suizinshu.external.system;

import net.suizinshu.external.Central;
import net.suizinshu.external.component.*;
import net.suizinshu.external.logic.KeyLogic.KeyBinder;
import net.suizinshu.external.system.SpriteAnimationSystem.AnimationType;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
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
		
		addHitShapeByTex(player, PrimShapeType.CUBOID, 0, 4);
//		addModelByTex(player, PrimShapeType.CUBOID, 0, 4, Color.WHITE);
		
		Entity block = world.createEntity();
		block.edit()
			.add(new DrawTexture("cat"))
//			.add(new IsCentered())
			.add(new LabelString("block"))
			.add(new Position(100, 100, 2))
			.add(new CollisionDetection(Central.WALL_FILTER, Central.PLAYER_FILTER));
		
		addHitShapeByTex(block, PrimShapeType.CUBOID, 0, 4);
//		addModelByTex(block, PrimShapeType.CUBOID, 0, 4, Color.BLUE);
	}
	
	//
	////
	//////
	////////
	////////..
	////////....
	////////....
	////////....
	////////..
	////////
	//////
	////
	//
	//
	////
	//////
	////////
	////////..
	////////....
	////////....
	////////....
	////////..
	////////
	//////
	////
	//
	//
	////
	//////
	////////
	////////..
	////////....
	////////....
	////////....
	////////..
	////////
	//////
	////
	//
	
	public enum PrimShapeType {
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
	public void addHitShapeByTex(Entity e, PrimShapeType type, float berth, float z) {
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
	
	private static int divisionsU = 20;
	private static int divisionsV = 20;
	
	/**
	 * Convenience method for adding a HitShape of BoxShape with the texture dimensions and width.
	 * @param e			entity to add to
	 * @param berth		pixels' affordance given. positive: hitbox smaller than tex, neg: more than tex<br>
	 * @param z			3d size portion if need be ("out of screen")
	 */
	public void addModelByTex(Entity e, PrimShapeType type, float berth, float z, Color color) {
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
			
			width = Math.max(width - 2*berth, 0.1f);
			height = Math.max(height - 2*berth, 0.1f);
			
			ModelBuilder mb = new ModelBuilder();
			mb.begin();
			mb.end();
			
			long usage = Usage.Position | Usage.Normal;
			Material redMaterial = new Material(ColorAttribute.createDiffuse(color));
			Model model = new Model();
			
			switch (type) {
			case CUBOID:
				model = mb.createBox(width, height, z, redMaterial, usage);
				break;
			case SPHERE:
				model = mb.createSphere(width, height, z, divisionsU, divisionsV, redMaterial, usage);
				break;
			case CYLINDER:
				model = mb.createCylinder(width, height, z, divisionsU, redMaterial, usage);
				break;
			case CAPSULE:
				float radiusCap = (width/2) - berth;
				float heightCap = (height) - (2 * berth) - (2 * radiusCap);
				model = mb.createCapsule(radiusCap, heightCap, divisionsU, redMaterial, usage);
				break;
			case CONE:
				model = mb.createCone(width, height, z, divisionsU, redMaterial, usage);
				break;
			}
			
			e.edit().add(new DrawModel(new ModelInstance(model)));
			
		}
		
	}
	

}
