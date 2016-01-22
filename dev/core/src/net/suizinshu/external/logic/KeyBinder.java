package net.suizinshu.external.logic;

import com.artemis.utils.Bag;

public class KeyBinder extends Bag<KeyEvaluable> implements Script, Toggleable {
	
	private boolean active = true;
	
	public KeyBinder(KeyEvaluable... bindings) {
		for (KeyEvaluable binding : bindings)
			this.add(binding);
	}
	
	@Override
	public void accept(Integer entityId) {
		if (active)
			for (KeyEvaluable binding : this)
				binding.eval(entityId);;
	}

	public void on() { active = true; }

	public void off() { active = false; }
	
}