package net.suizinshu.external.logic;


/**
 * Set up a condition in the constructor, and then test it for a boolean.
 * @author Zicheng Gao
 *
 * @param <T>	Target
 * @param <U>	Inputs to match
 */
public abstract class ListCondition<T, U> implements Condition {
	
	/** Target to check for. */
	private T target;
	
	/** Inputs to examine in some way. */
	private U[] inputs;
	
	/** Define a condition for target and inputs. */
	public ListCondition(T target, U[] inputs) {
		this.target = target;
		this.inputs = inputs;
	}
	
	/**
	 * Test the condition's truth value.
	 * @return	Condition's truth value.
	 */
	public abstract boolean eval();
	
	/** Return target value of condition. */
	public T target() {return target;};
	
	/** Return inputs to examine. */
	public U[] inputs() {return inputs;};
	
	/** Set target. */
	public void target(T t) {target = t;};
	
	/** Set inputs. */
	public void inputs(U[] u) {inputs = u;};
	
}