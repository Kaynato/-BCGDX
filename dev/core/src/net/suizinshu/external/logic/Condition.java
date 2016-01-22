package net.suizinshu.external.logic;


/**
 * Set up a condition in the constructor, and then test it for a boolean.
 * @author Zicheng Gao
 *
 * @param <T>	Target
 * @param <U>	Inputs to match
 */
public interface Condition<T, U> {
	
	
	public boolean test(T target, U[] inputs);
	
}