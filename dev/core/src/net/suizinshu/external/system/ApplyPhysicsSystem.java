package net.suizinshu.external.system;

import net.suizinshu.external.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class ApplyPhysicsSystem extends IteratingSystem {
	private ComponentMapper<Position> pm;
	private ComponentMapper<Velocity> vm;
	private ComponentMapper<Acceleration> am;
	
	private ComponentMapper<Friction> frm;
	private ComponentMapper<MaxSpeed> msm;
	private ComponentMapper<Gravity> gvm;
	
	public ApplyPhysicsSystem() {
		super(Aspect.all(Position.class, Velocity.class));
	}
	
	@Override
	protected void process(int entityId) {
		Velocity vel = vm.get(entityId);
		Position pos = pm.get(entityId);
		
		/* APPLY EXTERNAL FORCES */
		
		/* Acceleration. */
		if (am.has(entityId)) {
			Acceleration ace = am.get(entityId);
			
			/* Gravity. */
			if (gvm.has(entityId))
				if (gvm.getSafe(entityId).active)
					ace.queue.add(gvm.getSafe(entityId).accel);
			
			/* Apply queue. */
			ace.vec.add(ace.queue);
			ace.queue.setZero();
			
			vel.queue.add(ace.vec);
			
		}

		/* Apply queue. */
		vel.vec.add(vel.queue);
		vel.queue.setZero();
		
		/* POST PROCESSING */
		
		/* Apply speed limiter. */
		if (msm.has(entityId))
			vel.vec.clamp(0, msm.get(entityId).maxspeed);
		
		if (frm.has(entityId)) {
			Friction fr = frm.getSafe(entityId);
			if (!vel.vec.isZero(fr.epsilon))
				vel.vec.scl(1 - fr.mu);
			else
				vel.vec.setZero();
		}
			
		/* ADJUST POSITION */
		pos.vec.add(vel.vec);
	}
	
}
