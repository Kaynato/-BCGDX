package net.suizinshu.external.logic;

/**
 * Basically a predicate.
 * @author Zicheng Gao
 */
public interface Condition<T> {
	
	public boolean test(T t);
	
}