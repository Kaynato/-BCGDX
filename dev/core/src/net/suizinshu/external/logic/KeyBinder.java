package net.suizinshu.external.logic;

import com.artemis.utils.Bag;


public class KeyBinder implements Script {
	
	private Bag<KeyConditional> bindings;
	
	public KeyBinder(KeyConditional... bindings) {
		for (KeyConditional binding : bindings)
			this.bindings.add(binding);
	}
	
	@Override
	public void accept(Integer t) {
		for (KeyConditional binding : bindings)
			;
	}
	
}
