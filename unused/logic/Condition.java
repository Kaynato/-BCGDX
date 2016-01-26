package net.suizinshu.external.logic;

/**
 * Returns a boolean value when evaluated.
 * @author Zicheng Gao
 */
@FunctionalInterface
public interface Condition {
	
	public boolean eval();
	
}
