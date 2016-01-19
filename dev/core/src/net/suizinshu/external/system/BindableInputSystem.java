package net.suizinshu.external.system;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.Keybinding;
import net.suizinshu.external.Manager_Keyboard.SingleKeybinding;
import net.suizinshu.external.Script;
import net.suizinshu.external.component.Acceleration;
import net.suizinshu.external.component.AngleVelocity;
import net.suizinshu.external.component.BindableInput;
import net.suizinshu.external.component.Friction;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class BindableInputSystem extends IteratingSystem {
	private ComponentMapper<BindableInput> act;
	
	public BindableInputSystem() {
		super(Aspect.all(BindableInput.class));
	}
	
	@Override
	protected void process(int entityId) {
		for (Keybinding binding : act.get(entityId).bindings)
			if (binding != null)
				binding.checkAndPerform(entityId);
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
	
//	private ComponentMapper<Velocity> vm;
	private ComponentMapper<Acceleration> am;
	private ComponentMapper<Friction> frm;
	
//	private ComponentMapper<Angle> angm;
	private ComponentMapper<AngleVelocity> anvm;
	
	public class Bindings {
		
		public SingleKeybinding[] velocityPlanarMovement(float accel) {
			SingleKeybinding up = new SingleKeybinding(Manager_Keyboard.up);
			up.toggle(accelQueueAdd(0, accel, 0), accelQueueAdd(0, -accel, 0));
			
			SingleKeybinding down = new SingleKeybinding(Manager_Keyboard.down);
			down.toggle(accelQueueAdd(0, -accel, 0), accelQueueAdd(0, accel, 0));
			
			SingleKeybinding left = new SingleKeybinding(Manager_Keyboard.left);
			left.toggle(accelQueueAdd(-accel, 0, 0), accelQueueAdd(accel, 0, 0));
			
			SingleKeybinding right = new SingleKeybinding(Manager_Keyboard.right);
			right.toggle(accelQueueAdd(accel, 0, 0), accelQueueAdd(-accel, 0, 0));
			
			SingleKeybinding any = new SingleKeybinding(Manager_Keyboard.any);
			any.add(Manager_Keyboard.KEY_HELD, toggleFriction(false));
			any.add(Manager_Keyboard.KEY_NONE, toggleFriction(true));
			
			SingleKeybinding[] output = new SingleKeybinding[] {up, down, left, right, any};
			
			return output;
		}
		
		public SingleKeybinding[] rotate46(float degrees) {
			SingleKeybinding bind4 = new SingleKeybinding(Manager_Keyboard.bind4);
			bind4.toggle(angleVelQAdd(degrees), angleVelQAdd(-degrees));
			
			SingleKeybinding bind6 = new SingleKeybinding(Manager_Keyboard.bind6);
			bind6.toggle(angleVelQAdd(-degrees), angleVelQAdd(degrees));
			
			SingleKeybinding[] output = new SingleKeybinding[] {bind4, bind6};
			
			return output;
		}
		
		//     /////
		///     ////
		////     ///
		/////     //
		////     ///
		///     ////
		//     /////
		
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