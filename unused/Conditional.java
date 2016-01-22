package net.suizinshu.external.logic;

import java.util.function.Consumer;
/**
 * 
/**
 * A conditional is a container of a consumer and a condition.
 * When the condition is fulfilled, the consumer will activate.
 * @author Zicheng Gao
 *
 * @param <C>	Consumer type.
 * @param <T>	Condition type.
 */
public abstract class Conditional<C,T> {
	
	/*
	 * Apparently Lambda expressions aren't very optimized...?!
	 * Well, that's too bad.
	 */

	private Consumer<C> consumer;
	private Condition<T> condition;
	
	public Conditional(Consumer<C> consumer, Condition<T> condition){
		this.condition = condition;
		this.consumer = consumer;
	}
	
	public void setCondition(Condition<T> condition) {
		this.condition = condition;
	}
	
	public void setConsumer(Consumer<C> consumer) {
		this.consumer = consumer;
	}
	
	public void test(C input, T state) {
		if (condition.test(state))
			consumer.accept(input);
	}
	
}