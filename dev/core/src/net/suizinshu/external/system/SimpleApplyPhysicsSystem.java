package net.suizinshu.external.system;

import net.suizinshu.external.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class SimpleApplyPhysicsSystem extends IteratingSystem {
	ComponentMapper<Position> pm;
	ComponentMapper<Velocity> vm;
	ComponentMapper<Acceleration> am;
	ComponentMapper<Physics> phm;
	ComponentMapper<MaxSpeed> msm;
	ComponentMapper<Gravity> gvm;
	ComponentMapper<CollisionDetection> cdm;
	
	public SimpleApplyPhysicsSystem() {
		super(Aspect.all(Position.class, Velocity.class));
	}
	
	@Override
	protected void process(int entityId) {
		Velocity vel = vm.get(entityId);
		Position pos = pm.get(entityId);
		if (am.has(entityId)) {
			Acceleration accel = am.get(entityId);
			
			if (phm.has(entityId)) {
				Physics physics = phm.get(entityId);
				
				/* Account for gravity. */
				if (gvm.has(entityId))
					physics.apply.add(gvm.getSafe(entityId).force.cpy().scl(physics.mass));
				
				/* Account for normal forces from collision points. */
				if (cdm.has(entityId)) {
					
					physics.normal.add(cdm.getSafe(entityId).collisions);
					
				}
				
				accel.vec.set(physics.netforce.scl(1/physics.mass));
			}
			
			/* ADD ACCELERATION TO SPEED */
			vel.vec.add(accel.vec);
			
			/* Apply speed limiter. */
			if (msm.has(entityId)) {
				vel.vec.clamp(0, msm.get(entityId).maxspeed);
			}
			
		}
		pos.vec.add(vel.vec);
	}
	
}
