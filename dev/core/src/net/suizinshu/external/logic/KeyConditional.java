package net.suizinshu.external.logic;

public class KeyConditional extends Conditional<Integer, Byte, Byte> {
	
	public KeyConditional(Script script, KeyCondition condition) {
		super(script, condition);
	}
	
	public KeyConditional(Script script, Byte state, Byte[] keys) {
		super(script, new KeyMatcherAND(state, keys));
	}
	
	public KeyConditional(Script script, Byte state, Byte keys) {
		super(script, new KeyMatcherAND(state, new Byte[]{keys}));
	}
	
}