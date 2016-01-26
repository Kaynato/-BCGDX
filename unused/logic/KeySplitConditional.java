package net.suizinshu.external.logic;

import java.util.function.Consumer;

import net.suizinshu.external.logic.KeyBinder.KeyCondition;

public class KeySplitConditional extends SplitConditional<Integer, Byte, Byte> implements KeyEvaluable {
	
	public KeySplitConditional(Consumer<Integer> onTrue, Consumer<Integer> onOff, KeyCondition condition) {
		super(onTrue, onOff, condition);
	}
	
}
