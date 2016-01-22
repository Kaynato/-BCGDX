package net.suizinshu.external.system;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.component.*;
import net.suizinshu.external.logic.KeyBinder;
import net.suizinshu.external.logic.KeyConditional;
import net.suizinshu.external.logic.Script;

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
	//
	//
	//
	
	private ComponentMapper<Velocity> vm;
	private ComponentMapper<Acceleration> am;
	private ComponentMapper<Friction> frm;
	
	private ComponentMapper<Angle> angm;
	private ComponentMapper<AngleVelocity> anvm;
	
	public class Bindings {
		
		public KeyBinder velocityPlanarMovement(float accel) {
			
//			KeyConditional up = new KeyConditional();
//			up.toggle(accelQueueAdd(0, accel, 0), accelQueueAdd(0, -accel, 0));
//			
//			KeyConditional down = new KeyConditional(Manager_Keyboard.k_down);
//			down.toggle(accelQueueAdd(0, -accel, 0), accelQueueAdd(0, accel, 0));
//			
//			KeyConditional left = new KeyConditional(Manager_Keyboard.k_left);
//			left.toggle(accelQueueAdd(-accel, 0, 0), accelQueueAdd(accel, 0, 0));
//			
//			KeyConditional right = new KeyConditional(Manager_Keyboard.k_right);
//			right.toggle(accelQueueAdd(accel, 0, 0), accelQueueAdd(-accel, 0, 0));
//			
//			KeyConditional any = new KeyConditional(Manager_Keyboard.k_any);
//			any.add(Manager_Keyboard.KEY_HELD, toggleFriction(false));
//			any.add(Manager_Keyboard.KEY_NONE, toggleFriction(true));
//			
//			KeyBinder output = new KeyBinder(up, down, left, right, any);
			KeyBinder output = null;
//			
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
		private Script accelQueueAdd(float x, float y, float z) {
			return (id) -> {
				if (am.has(id))
					am.getSafe(id).queue.add(x, y, z);
				else
					System.err.println("INCOMPATIBLE KEYBINDING");
			};
		}
		
		private Script toggleFriction(boolean active) {
			return (id) -> {
				if (frm.has(id))
					frm.getSafe(id).active = active;
				else
					System.err.println("INCOMPATIBLE KEYBINDING");
			};
		}
		
		private Script angleVelQAdd(float degrees) {
			return (id) -> {
				if (anvm.has(id))
					anvm.getSafe(id).deg += degrees;
				else
					System.err.println("INCOMPATIBLE KEYBINDING");
			};
		}
		
		
		
	}
	
}