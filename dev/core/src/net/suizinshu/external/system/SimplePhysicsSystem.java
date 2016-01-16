package net.suizinshu.external.system;

import net.suizinshu.external.component.Acceleration;
import net.suizinshu.external.component.Physics;
import net.suizinshu.external.component.Position;
import net.suizinshu.external.component.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class SimplePhysicsSystem extends IteratingSystem {
	ComponentMapper<Position> pm;
	ComponentMapper<Velocity> vm;
	ComponentMapper<Acceleration> am;
	ComponentMapper<Physics> phm;
	
	public SimplePhysicsSystem() {
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
				accel.vec.set(physics.netforce.scl(1/physics.mass));
			}
			
			vel.vec.add(accel.vec);
		}
		pos.vec.add(vel.vec);
	}
	
}
