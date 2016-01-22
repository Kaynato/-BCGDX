package net.suizinshu.external.system;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.component.*;
import net.suizinshu.external.logic.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class BindableInputSystem extends IteratingSystem {
	private ComponentMapper<KeyBinding> act;
	
	public BindableInputSystem() {
		super(Aspect.all(KeyBinding.class));
	}
	
	@Override
	protected void process(int entityId) {
		act.get(entityId).bindings.accept(entityId);
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
						new KeyBoolCondition()
						.and(new KeyMatcherAND(Manager_Keyboard.KEY_HELD, Manager_Keyboard.UP))
						.not(new KeyMatcherAND(Manager_Keyboard.KEY_HELD, Manager_Keyboard.DOWN))
						);
			
			KeyConditional down = 
					new KeyConditional(
							(id) -> am.getSafe(id).next.y = -accel, 
							new KeyBoolCondition()
							.and(new KeyMatcherAND(Manager_Keyboard.KEY_HELD, Manager_Keyboard.DOWN))
							.not(new KeyMatcherAND(Manager_Keyboard.KEY_HELD, Manager_Keyboard.UP))
							);
			
			KeyConditional left = 
					new KeyConditional(
							(id) -> am.getSafe(id).next.x = -accel, 
							new KeyBoolCondition()
							.and(new KeyMatcherAND(Manager_Keyboard.KEY_HELD, Manager_Keyboard.LEFT))
							.not(new KeyMatcherAND(Manager_Keyboard.KEY_HELD, Manager_Keyboard.RIGHT))
							);
			
			KeyConditional right = 
					new KeyConditional(
							(id) -> am.getSafe(id).next.x = accel, 
							new KeyBoolCondition()
							.and(new KeyMatcherAND(Manager_Keyboard.KEY_HELD, Manager_Keyboard.RIGHT))
							.not(new KeyMatcherAND(Manager_Keyboard.KEY_HELD, Manager_Keyboard.LEFT))
							);
			
			KeyConditional frictionOn =
					new KeyConditional(
							toggleFriction(true),
							new KeyBoolCondition()
							.and(new KeyMatcherAND(Manager_Keyboard.KEY_NONE,
									Manager_Keyboard.UP,
									Manager_Keyboard.DOWN,
									Manager_Keyboard.LEFT,
									Manager_Keyboard.RIGHT))
							);
			
			KeyConditional frictionOff =
					new KeyConditional(
							toggleFriction(false),
							new KeyBoolCondition()
							.and(new KeyMatcherOR(Manager_Keyboard.KEY_HELD,
									Manager_Keyboard.UP,
									Manager_Keyboard.DOWN,
									Manager_Keyboard.LEFT,
									Manager_Keyboard.RIGHT))
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