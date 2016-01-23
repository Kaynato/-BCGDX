package net.suizinshu.external.logic;

public class KeyConditional extends Conditional<Integer, Byte, Byte> implements KeyEvaluable {
	
	public KeyConditional(Script script, KeyCondition condition) {
		super(script, condition);
	}
	
}