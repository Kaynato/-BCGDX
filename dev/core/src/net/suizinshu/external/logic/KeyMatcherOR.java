package net.suizinshu.external.logic;

import net.suizinshu.external.Manager_Keyboard;


public class KeyMatcherOR extends ListCondition<Byte, Byte> implements KeyCondition {

	public KeyMatcherOR(Byte state, Byte... keys) {
		super(state, keys);
	}

	@Override
	public boolean eval() {
		boolean output = (inputs().length == 0) ? (true) : (false);
		for (int i = 0; i < inputs().length; i++)
			output |= Manager_Keyboard.state(inputs()[i]) == target(); 
		return output;
	}
	
}