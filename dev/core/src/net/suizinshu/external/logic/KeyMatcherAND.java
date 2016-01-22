package net.suizinshu.external.logic;

import net.suizinshu.external.Manager_Keyboard;


public class KeyMatcherAND extends ListCondition<Byte, Byte> implements KeyCondition {

	public KeyMatcherAND(Byte state, Byte... keys) {
		super(state, keys);
	}
	
	@Override
	public boolean eval() {
		boolean output = true;
		for (int i = 0; i < inputs().length; i++)
			output &= Manager_Keyboard.state(inputs()[i]) == target(); 
		return output;
	}
	
}