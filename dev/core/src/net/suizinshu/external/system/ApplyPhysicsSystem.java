package net.suizinshu.external.system;

import net.suizinshu.external.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class ApplyPhysicsSystem extends IteratingSystem {
	private ComponentMapper<Position> pm;
	private ComponentMapper<Velocity> vm;
	private ComponentMapper<Acceleration> am;
	
	private ComponentMapper<Angle> angm;
	private ComponentMapper<AngleVelocity> anvm;
	
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
					ace.next.add(gvm.getSafe(entityId).accel);
			
			/* Apply queue. */
			ace.vec.set(ace.next);
			ace.next.setZero();
			
			vel.queue.add(ace.vec);
			
		}
		
		/* Apply queue. */
		vel.vec.add(vel.queue);
		vel.queue.setZero();
		
		if (angm.has(entityId) && anvm.has(entityId)) {
			/* Apply queue and adjust rotation. */
			Angle ang = angm.getSafe(entityId);
			AngleVelocity anv = anvm.getSafe(entityId);
			
			ang.add(anv.deg);
//			ang.q.mul(anv.q);
		}
		
		
		/* POST PROCESSING */
		
		/* Apply speed limiter. */
		if (msm.has(entityId))
			vel.vec.clamp(0, msm.get(entityId).maxspeed);
		
		/* Apply friction. */
		if (frm.has(entityId)) {
			Friction fr = frm.getSafe(entityId);
			if (fr.active)
				if (vel.vec.isZero(fr.epsilon))
					vel.vec.setZero();
				else
					vel.vec.scl(1 - fr.mu);
		}
		
		/* ADJUST POSITION */
		pos.vec.add(vel.vec);
	}
	
}
