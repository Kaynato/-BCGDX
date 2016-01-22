package net.suizinshu.external.logic;

import com.artemis.utils.Bag;

public class KeyBinder extends Bag<KeyConditional> implements Script {
	
	public KeyBinder(KeyConditional... bindings) {
		for (KeyConditional binding : bindings)
			this.add(binding);
	}
	
	@Override
	public void accept(Integer entityId) {
		for (KeyConditional binding : this)
			binding.eval(entityId);;
	}
	
}