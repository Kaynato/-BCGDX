package net.suizinshu.external.system;

import net.suizinshu.external.component.*;
import net.suizinshu.external.component.interfaces.ActPass;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class ApplyPhysicsSystem extends IteratingSystem {
	private ComponentMapper<Position> pm;
	private ComponentMapper<Velocity> vm;
	private ComponentMapper<Acceleration> am;
	
	private ComponentMapper<Angle> angm;
	private ComponentMapper<AngleVelocity> anvm;
	
	private ComponentMapper<ActiveFriction> frm;
	private ComponentMapper<MaxSpeed> msm;
	private ComponentMapper<Gravity> gvm;
	
//	private ComponentMapper<Debug> debug;
	
	public ApplyPhysicsSystem() {
		super(Aspect.all(Position.class, Velocity.class));
	}
	
	@Override
	protected void process(int entityId) {
		Position pos = pm.getSafe(entityId);
		Velocity vel = vm.getSafe(entityId);
		
//		if (KeyQuery.B1() && debug.has(entityId))
//			System.out.println("DEBUG INITIATED");

		processAngular(entityId);
		
		if (am.has(entityId))
			processAcceleration(entityId, vel);
		
		processVelocity(entityId, vel);
		
		pushPosition(pos, vel);
		
	}

	//__________________________________________________//
	//								    				//
	//								    				//	
	//								    				//
	//								    				//
	//				NEWTONIAN PROCESSING				//		  
	//								    				//
	//				   PRIVATE METHODS   				//
	//								    				//
	//__________________________________________________//
	//								    				//
	
	private void processAcceleration(int entityId, Velocity vel) {
		
		Acceleration ace = am.getSafe(entityId);
		
		processAccelPassive(entityId, ace);
		
		applyAccelPassive(entityId, ace);
		
		applyAccelActive(entityId, ace);
		
		/* BUMP NEXT ACTIVE */
		ActPass.bumpSet(ace);
		
		/* BUMP TO VELOCITY */
		ActPass.push(ace, vel);
	}
	
	private void processVelocity(int entityId, Velocity vel) {

		/* Bump ADD due to preservation of existing velocity. */
		ActPass.bumpAdd(vel);
		
		processVelocityPassive(entityId, vel);
		
		applyVelocityPassive(entityId, vel);
		
		applyVelocityActive(entityId, vel);
		
	}
	
	private void pushPosition(Position pos, Velocity vel) {
		pos.vec.add(vel.active());
		pos.vec.add(vel.passive());
	}
	
	//__________________________________________________//
	//								    				//
	//								    				//
	//					  INTERNALS     				//
	//								    				//
	//__________________________________________________//
	//								    				//

	private void processVelocityPassive(int entityId, Velocity vel) {
		
	}
	
	private void applyVelocityPassive(int entityId, Velocity vel) {
		
	}
	
	private void applyVelocityActive(int entityId, Velocity vel) {
		/* Apply friction to active component. */
		if (frm.has(entityId)) {
			ActiveFriction fr = frm.getSafe(entityId);
			if (fr.active)
				if (vel.active().isZero(fr.epsilon))
					vel.active().setZero();
				else
					vel.active().scl(1 - fr.coefficient);
		}
		
		/* Apply speed limiter for active component. */
		if (msm.has(entityId))
			vel.active().clamp(0, msm.get(entityId).maxspeed);
	}
	
	
	
	private void processAccelPassive(int entityId, Acceleration ace) {
		/* Gravity. */
		if (gvm.has(entityId)) {
			Gravity gv = gvm.getSafe(entityId);
			if (gv.active)
				ace.passive().add(gv.accel);
		}
	}
	
	private void applyAccelPassive(int entityId, Acceleration ace) {
		
	}
	
	private void applyAccelActive(int entityId, Acceleration ace) {
		
	}
	
	private void processAngular(int entityId) {
		/* Angular stuff. */
		if (angm.has(entityId) && anvm.has(entityId)) {
			/* Apply queue and adjust rotation. */
			Angle ang = angm.getSafe(entityId);
			AngleVelocity anv = anvm.getSafe(entityId);
			
			ang.add(anv.deg);
		}
	}
	
}
