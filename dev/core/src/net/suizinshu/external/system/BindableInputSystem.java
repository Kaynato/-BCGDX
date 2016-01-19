package net.suizinshu.external.system;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.Keybinding;
import net.suizinshu.external.Script;
import net.suizinshu.external.component.Acceleration;
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
	
	public class Bindings {
		
		public Keybinding[] velocityPlanarMovement(float accel) {
			Keybinding up = new Keybinding(Manager_Keyboard.up);
			up.toggle(accelQueueAdd(0, accel, 0), accelQueueAdd(0, -accel, 0));
					
			Keybinding down = new Keybinding(Manager_Keyboard.down);
			down.toggle(accelQueueAdd(0, -accel, 0), accelQueueAdd(0, accel, 0));
			
			Keybinding left = new Keybinding(Manager_Keyboard.left);
			left.toggle(accelQueueAdd(-accel, 0, 0), accelQueueAdd(accel, 0, 0));
			
			Keybinding right = new Keybinding(Manager_Keyboard.right);
			right.toggle(accelQueueAdd(accel, 0, 0), accelQueueAdd(-accel, 0, 0));
			
			Keybinding any = new Keybinding(Manager_Keyboard.any);
			any.add(Manager_Keyboard.KEY_HELD, toggleFriction(false));
			any.add(Manager_Keyboard.KEY_NONE, toggleFriction(true));
			
			Keybinding[] output = new Keybinding[] {up, down, left, right, any};
			
			return output;
		}
		
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
		
	}
	
}