package net.suizinshu.external.logic;

import net.suizinshu.external.Manager_Keyboard;


public class KeyMatcher extends Matcher<Byte, Byte> {

	public KeyMatcher(Byte state, Byte[] keys) {
		super(state, keys);
	}

	@Override
	public boolean test() {
		boolean output = true;
		for (int i = 0; i < inputs().length; i++)
			output &= Manager_Keyboard.state(inputs()[i]) == target(); 
		return output;
	}
	
}