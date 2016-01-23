package net.suizinshu.external.system;

import static net.suizinshu.external.Manager_Keyboard.KeyQuery.*;
import net.suizinshu.external.Manager_Keyboard.KeyConst;
import net.suizinshu.external.component.Acceleration;
import net.suizinshu.external.component.AngleVelocity;
import net.suizinshu.external.component.ActiveFriction;
import net.suizinshu.external.component.InputBinder;
import net.suizinshu.external.logic.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class BindableInputSystem extends IteratingSystem {
	private ComponentMapper<InputBinder> act;
	
	public BindableInputSystem() {
		super(Aspect.all(InputBinder.class));
	}
	
	@Override
	protected void process(int entityId) {
		act.get(entityId).binder.accept(entityId);
	}
	
	//
	//
	//
	// Here be bindings
	//
	//
	//
	
//	private ComponentMapper<Velocity> vm;
	private ComponentMapper<Acceleration> am;
	private ComponentMapper<ActiveFriction> frm;
	
//	private ComponentMapper<Angle> angm;
	private ComponentMapper<AngleVelocity> anvm;
	
	public class Bindings {
		
		public KeyBinder accelMovement(float accel) {
			
			KeyEvaluable up = 
					new KeyConditional(
							(id) -> am.getSafe(id).nextActive().y = accel, 
							() -> U() && !D()
							);
			
			KeyEvaluable down = 
					new KeyConditional(
							(id) -> am.getSafe(id).nextActive().y = -accel, 
							() -> D() && !U()
							);
			
			KeyEvaluable left = 
					new KeyConditional(
							(id) -> am.getSafe(id).nextActive().x = -accel, 
							() -> L() && !R()
							);
			
			KeyEvaluable right = 
					new KeyConditional(
							(id) -> am.getSafe(id).nextActive().x = accel, 
							() -> R() && !L()
							);
			
			KeyEvaluable frictionToggle =
					new KeySplitConditional(
							toggleFriction(true),
							toggleFriction(false),
							() -> (!D() || U()) && (D() || !U()) && (!L() || R()) && (L() || !R())
							);
			
			/*
			 *  !(U() || D() || L() || R()) 		// No arrow keys
			 *	|| ((U() && D()) && !(L() ^ R()))	// Vert. cancel w/o horiz single
			 *	|| ((L() && R()) && !(U() ^ D()))	// Horiz. cancel w/o vert single
			 * 
			 * CNF: (!D() || U()) && (D() || !U()) && (!L() || R()) && (L() || !R())
			 * CNF: (!D || U) && (D || !U) && (!L || R) && (L || !R)
			 */
			
			KeyBinder output = new KeyBinder(up, down, left, right, frictionToggle);
			
			return output;
		}
		
		public KeyBinder rotate46(float degrees) {
			KeyEvaluable[] bind4 =
					KeyUtils.toggle(angleVelQSet(degrees), angleVelQSet(-degrees), KeyConst.BIND4);
					
			KeyEvaluable[] bind6 = 
					KeyUtils.toggle(angleVelQSet(-degrees), angleVelQSet(degrees), KeyConst.BIND6);
			
			KeyBinder output = new KeyBinder(bind4, bind6);
			
			return output;
		}
//		
//		//     /////
//		///     ////
//		////     ///
//		/////     //
//		////     ///
//		///     ////
//		//     /////
//		
		
		private Script toggleFriction(boolean active) {
			return (id) -> {
				if (frm.has(id))
					frm.getSafe(id).active = active;
			};
		}
		
		private Script angleVelQSet(float degrees) {
			return (id) -> {
				if (anvm.has(id))
					anvm.getSafe(id).deg += degrees;
			};
		}
		
		
		
	}
	
}