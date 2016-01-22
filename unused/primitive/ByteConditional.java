package net.suizinshu.external.logic.primitive;

import java.util.function.Consumer;

/**
 * A conditional is a container of a consumer and a condition.
 * When the condition is fulfilled, the consumer will activate.
 * @author Zicheng Gao
 *
 * @param <C>	Consumer Consumption Type
 */
public abstract class ByteConditional<C> {

	private Consumer<C> consumer;
	private ByteCondition bicondition;
	
	public ByteConditional(Consumer<C> consumer, ByteCondition bicondition){
		this.consumer = consumer;
		this.bicondition = bicondition;
	}
	
	public void setConsumer(Consumer<C> consumer) {
		this.consumer = consumer;
	}

	public void setCondition(ByteCondition bicondition) {
		this.bicondition = bicondition;
	}
	
	public void test(C input, byte target, byte... inputs) {
		if (bicondition.test(target, inputs))
			consumer.accept(input);
	}

	
}