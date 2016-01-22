package net.suizinshu.external.logic;

import net.suizinshu.external.Manager_Keyboard;


public class KeyCondition implements Condition<Byte, Byte> {

	@Override
	public boolean test(Byte state, Byte[] keys) {
		boolean output = true;
		for (int i = 0; i < keys.length; i++)
			output &= Manager_Keyboard.state(keys[i]) == state; 
		return output;
	}
	
}