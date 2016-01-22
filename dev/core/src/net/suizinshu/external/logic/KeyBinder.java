package net.suizinshu.external.logic;

import com.artemis.utils.Bag;

public class KeyBinder extends Bag<KeyConditional> implements Script, Toggleable {
	
	private boolean active = true;
	
	public KeyBinder(KeyConditional... bindings) {
		for (KeyConditional binding : bindings)
			this.add(binding);
	}
	
	@Override
	public void accept(Integer entityId) {
		if (active)
			for (KeyConditional binding : this)
				binding.eval(entityId);;
	}

	public void on() { active = true; }

	public void off() { active = false; }
	
}