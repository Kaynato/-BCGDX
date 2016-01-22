package net.suizinshu.external.logic;

@FunctionalInterface
public interface Evaluable<C> {
	
	public void eval(C input);
	
}
