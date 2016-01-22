package net.suizinshu.external.logic;



public class KeyConditional extends Conditional<Integer, Byte, Byte> {
	
	public KeyConditional(ScriptList consumer, Condition<Byte, Byte> condition) {
		super(consumer, condition);
	}
	
}