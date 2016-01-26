package net.suizinshu.external.logic;

/**
 * Set up a condition in the constructor, and then test it for a boolean.<br>
 * A {@link Condition}.
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
	
	/**
	 * Logical form this listcondition takes.<br>
	 * Accepts constants from {@link Condition}
	 */
	private final byte _type;
	
	/** Define a condition for target and inputs. */
	public ListCondition(byte type, T target, U[] inputs) {
		this._type = type;
		this.target = target;
		this.inputs = inputs;
	}
	
	/**
	 * Test the condition's truth value.
	 * @return	Condition's truth value.
	 */
	public boolean eval() {
		boolean output = true;
		for (int i = 0; i < inputs().length; i++)
			output &= (_type % 2 == 0) ? (evalSingle(i)) : (!evalSingle(i)); 
		return (_type % 3 == 0) ? (output) : (!output);
	}
	
	/**
	 * Evaluate a single member of the input array.
	 * @param i	Index
	 * @return	Evaluation
	 */
	public abstract boolean evalSingle(int i);
	
	/** Return target value of condition. */
	public T target() {return target;};
	
	/** Return inputs to examine. */
	public U[] inputs() {return inputs;};
	
	/** Set target. */
	public void target(T t) {target = t;};
	
	/** Set inputs. */
	public void inputs(U[] u) {inputs = u;};
	
}