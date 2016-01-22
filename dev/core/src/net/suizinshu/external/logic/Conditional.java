package net.suizinshu.external.logic;

import java.util.function.Consumer;

/**
 * A conditional is a container of a consumer and a condition.
 * When the condition is fulfilled, the consumer will activate.
 * @author Zicheng Gao
 *
 * @param <C>	Consumer Consumption Type
 * @param <T>	Biconditional type 1
 * @param <U>	Biconditional type 2
 */
public abstract class Conditional<C, T, U> {

	private Consumer<C> consumer;
	private Condition<T, U> condition;
	
	public Conditional(Consumer<C> consumer, Condition<T, U> condition){
		this.consumer = consumer;
		this.condition = condition;
	}
	
	public void setConsumer(Consumer<C> consumer) {
		this.consumer = consumer;
	}

	public void setCondition(Condition<T, U> condition) {
		this.condition = condition;
	}
	
	public void eval(C input, T target, U[] match) {
		if (condition.test(target, match))
			consumer.accept(input);
	}

	
}