package net.suizinshu.external.util;

import java.util.function.BooleanSupplier;
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
public abstract class Conditional<C, T, U> implements Consumer<C> {
	
	private boolean active = true;
	private boolean hasSplit = false;
	
	private Consumer<C> consumer;
	
	private Consumer<C> alternate;
	
	/** BooleanSupplier which is examined by this conditional. */
	private BooleanSupplier condition;
	
	public Conditional(Consumer<C> consumer, BooleanSupplier condition){
		this.consumer = consumer;
		this.condition = condition;
	}
	
	public Conditional(Consumer<C> consumer, Consumer<C> alternate, BooleanSupplier condition){
		this.consumer = consumer;
		this.condition = condition;
		this.alternate = alternate;
		hasSplit = true;
	}
	
	public void setConsumer(Consumer<C> consumer) {
		this.consumer = consumer;
	}
	
	public void setAlternate(Consumer<C> alternate) {
		this.alternate = alternate;
		hasSplit = true;
	}

	public void setCondition(BooleanSupplier condition) {
		this.condition = condition;
	}
	
	public void setSplit(boolean doSplit) {
		if (alternate != null)
			hasSplit = doSplit;
		else
			hasSplit = false;
	}
	
	public void accept(C input) {
//		for (int i = 0; i < 1000000; i++)
//			condition.getAsBoolean();
		
		if (active && condition.getAsBoolean())
			consumer.accept(input);
		else if (hasSplit)
			alternate.accept(input);
	}
	
	/** Activate. */
	public void on() {active = true;}
	
	/** Deactivate. */
	public void off() {active = false;}
	
}