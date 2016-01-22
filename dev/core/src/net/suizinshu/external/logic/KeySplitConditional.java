package net.suizinshu.external.logic;

public class KeySplitConditional extends SplitConditional<Integer, Byte, Byte> implements KeyEvaluable {
	
	public KeySplitConditional(Script onTrue, Script onOff, KeyCondition condition) {
		super(onTrue, onOff, condition);
	}
	
	public KeySplitConditional(Script onTrue, Script onOff, byte type, Byte state, Byte[] keys) {
		super(onTrue, onOff, new KeyMatcher(type, state, keys));
	}
	
	public KeySplitConditional(Script onTrue, Script onOff, byte type, Byte state, Byte keys) {
		super(onTrue, onOff, new KeyMatcher(type, state, new Byte[]{keys}));
	}
	
}
