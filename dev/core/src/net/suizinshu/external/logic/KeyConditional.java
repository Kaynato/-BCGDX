package net.suizinshu.external.logic;

public class KeyConditional extends Conditional<Integer, Byte, Byte> {
	
	public KeyConditional(Script script, Matcher<Byte, Byte> condition) {
		super(script, condition);
	}
	
	public KeyConditional(Script script, Byte state, Byte[] keys) {
		super(script, new KeyMatcher(state, keys));
	}
	
}