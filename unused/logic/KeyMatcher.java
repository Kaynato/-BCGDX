package net.suizinshu.external.logic;

import net.suizinshu.external.Manager_Keyboard;


public class KeyMatcher extends ListCondition<Byte, Byte> implements KeyCondition {

	public KeyMatcher(byte type, Byte state, Byte... keys) {
		super(type, state, keys);
	}
	
	@Override
	public boolean evalSingle(int i) {
		return Manager_Keyboard.state(inputs()[i]) == target(); 
	}
	
}