package net.suizinshu.external.logic;

/**
 * Indicates that you can build with boolean arguments.
 * @author Zicheng Gao
 *
 * @param <S>	Class implementing this interface.
 * @param <I>	Argument.
 */
public interface BoolChainable<S,I> {
	
	/**
	 * Chainable AND operator.
	 * @param condition
	 * @return
	 */
	public S and(I condition);
	
	/**
	 * Chainable OR operator.
	 * @param condition
	 * @return
	 */
	public S or(I condition);
	
	/**
	 * Chainable NOT operator.
	 * @param condition
	 * @return
	 */
	public S not(I condition);
	
}
