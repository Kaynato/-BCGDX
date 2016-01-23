package net.suizinshu.external.logic;

public class KeySplitConditional extends SplitConditional<Integer, Byte, Byte> implements KeyEvaluable {
	
	public KeySplitConditional(Script onTrue, Script onOff, KeyCondition condition) {
		super(onTrue, onOff, condition);
	}
	
}
