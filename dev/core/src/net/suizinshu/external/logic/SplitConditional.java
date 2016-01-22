package net.suizinshu.external.logic;

import java.util.function.Consumer;

public abstract class SplitConditional<C, T, U> extends Conditional<C, T, U> {

	private Consumer<C> onFalse;
	
	public SplitConditional(Consumer<C> onTrue, Consumer<C> onFalse, Condition condition) {
		super(onTrue, condition);
		this.onFalse = onFalse;
	}
	
	@Override
	public void alternate(C input) {
		onFalse.accept(input);
	}
	
}
