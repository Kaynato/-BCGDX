package net.suizinshu.external.system;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.Keybinding;
import net.suizinshu.external.Script;
import net.suizinshu.external.component.Acceleration;
import net.suizinshu.external.component.BindableInput;
import net.suizinshu.external.component.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class BindableInputSystem extends IteratingSystem {
	ComponentMapper<BindableInput> act;
	
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
	
	ComponentMapper<Velocity> vm;
	ComponentMapper<Acceleration> am;
	
	public class Bindings {
		
		public Keybinding[] velocityPlanarMovement() {
			Keybinding up = new Keybinding(Manager_Keyboard.up);
			up.toggle(accelQueueAdd(0, 1, 0), accelQueueAdd(0, -1, 0));
					
			Keybinding down = new Keybinding(Manager_Keyboard.down);
			down.toggle(accelQueueAdd(0, -1, 0), accelQueueAdd(0, 1, 0));
			
			Keybinding left = new Keybinding(Manager_Keyboard.left);
			left.toggle(accelQueueAdd(-1, 0, 0), accelQueueAdd(1, 0, 0));
			
			Keybinding right = new Keybinding(Manager_Keyboard.right);
			right.toggle(accelQueueAdd(1, 0, 0), accelQueueAdd(-1, 0, 0));
			
			Keybinding[] output = new Keybinding[] {up, down, left, right}; 
			
			return output;
		}
		
		public Script accelQueueAdd(float x, float y, float z) {
			return (id) -> {
				if (am.has(id))
					am.getSafe(id).queue.add(x, y, z);
				else
					System.err.println("INCOMPATIBLE KEYBINDING");
			};
		}
		
	}
	
}