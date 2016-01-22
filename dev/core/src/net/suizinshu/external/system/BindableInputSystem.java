package net.suizinshu.external.system;

import static net.suizinshu.external.Manager_Keyboard.KeyQuery.D;
import static net.suizinshu.external.Manager_Keyboard.KeyQuery.L;
import static net.suizinshu.external.Manager_Keyboard.KeyQuery.R;
import static net.suizinshu.external.Manager_Keyboard.KeyQuery.U;
import net.suizinshu.external.component.Acceleration;
import net.suizinshu.external.component.Friction;
import net.suizinshu.external.component.MovementInput;
import net.suizinshu.external.logic.KeyBinder;
import net.suizinshu.external.logic.KeyConditional;
import net.suizinshu.external.logic.Script;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class BindableInputSystem extends IteratingSystem {
	private ComponentMapper<MovementInput> act;
	
	public BindableInputSystem() {
		super(Aspect.all(MovementInput.class));
	}
	
	@Override
	protected void process(int entityId) {
		act.get(entityId).binder.accept(entityId);
	}
	
	//
	//
	//
	//
	//
	//
	//
	
//	private ComponentMapper<Velocity> vm;
	private ComponentMapper<Acceleration> am;
	private ComponentMapper<Friction> frm;
	
//	private ComponentMapper<Angle> angm;
//	private ComponentMapper<AngleVelocity> anvm;
	
	public class Bindings {
		
		public KeyBinder accelMovement(float accel) {
			
			KeyConditional up = 
					new KeyConditional(
							(id) -> am.getSafe(id).next.y = accel, 
							() -> U() && !D()
							);
			
			KeyConditional down = 
					new KeyConditional(
							(id) -> am.getSafe(id).next.y = -accel, 
							() -> D() && !U()
							);
			
			KeyConditional left = 
					new KeyConditional(
							(id) -> am.getSafe(id).next.x = -accel, 
							() -> L() && !R()
							);
			
			KeyConditional right = 
					new KeyConditional(
							(id) -> am.getSafe(id).next.x = accel, 
							() -> R() && !L()
							);
			
			KeyConditional frictionOn =
					new KeyConditional(
							toggleFriction(true),
							() -> !(U() || D() || L() || R()) 
							|| ((U() && D()) && !(L() || R()))
							|| ((L() && R()) && !(U() || D()))
							);
			
			KeyConditional frictionOff =
					new KeyConditional(
							toggleFriction(false),
							() -> (U() || D() || L() || R())
							);
			
			KeyBinder output = new KeyBinder(up, down, left, right, frictionOn, frictionOff);
			
			return output;
		}
//		
//		public KeyBinder rotate46(float degrees) {
//			KeyConditional bind4 = new KeyConditional(Manager_Keyboard.k_bind4);
//			bind4.toggle(angleVelQAdd(degrees), angleVelQAdd(-degrees));
//			
//			KeyConditional bind6 = new KeyConditional(Manager_Keyboard.k_bind6);
//			bind6.toggle(angleVelQAdd(-degrees), angleVelQAdd(degrees));
//			
//			KeyBinder output = new KeyBinder {bind4, bind6};
//			
//			return output;
//		}
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
				else
					System.err.println("INCOMPATIBLE KEYBINDING");
			};
		}
		
//		private Script angleVelQAdd(float degrees) {
//			return (id) -> {
//				if (anvm.has(id))
//					anvm.getSafe(id).deg += degrees;
//				else
//					System.err.println("INCOMPATIBLE KEYBINDING");
//			};
//		}
		
		
		
	}
	
}