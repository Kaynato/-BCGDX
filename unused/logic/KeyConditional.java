package net.suizinshu.external.logic;

import java.util.function.Consumer;

import net.suizinshu.external.logic.KeyBinder.KeyCondition;

public class KeyConditional extends Conditional<Integer, Byte, Byte> implements KeyEvaluable {
	
	public KeyConditional(Consumer<Integer> script, KeyCondition condition) {
		super(script, condition);
	}
	
}