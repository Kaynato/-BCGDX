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
	private Matcher<T, U> matcher;
	
	public Conditional(Consumer<C> consumer, Matcher<T, U> condition){
		this.consumer = consumer;
		this.matcher = condition;
	}
	
	public void setConsumer(Consumer<C> consumer) {
		this.consumer = consumer;
	}

	public void setCondition(Matcher<T, U> condition) {
		this.matcher = condition;
	}
	
	public void eval(C input) {
		if (matcher.test())
			consumer.accept(input);
	}

	
}