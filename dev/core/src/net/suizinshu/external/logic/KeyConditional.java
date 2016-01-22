package net.suizinshu.external.logic;

public class KeyConditional extends Conditional<Integer, Byte, Byte> implements KeyEvaluable {
	
	public KeyConditional(Script script, KeyCondition condition) {
		super(script, condition);
	}
	
	public KeyConditional(Script script, byte type, Byte state, Byte[] keys) {
		super(script, new KeyMatcher(type, state, keys));
	}
	
	public KeyConditional(Script script, byte type, Byte state, Byte keys) {
		super(script, new KeyMatcher(type, state, new Byte[]{keys}));
	}
	
}