package net.suizinshu.external.logic;

import com.artemis.utils.Bag;

public class KeyBoolCondition implements KeyCondition, BoolChainable<KeyBoolCondition, KeyCondition>{
	
	private int TODO_ALERT;
	
	/**
	 * There was a problem with returning BoolCondition because of weird things.
	 * Guess it'll be a TODO for tomorrow's me.
	 * That is,
	 * 
	 * USE GENERICS BETTER
	 * 
	 * Or something.
	 */
	
	private Bag<KeyCondition> mandate;
	private Bag<KeyCondition> include;
	private Bag<KeyCondition> exclude;
	
	public KeyBoolCondition() {
		mandate = new Bag<KeyCondition>();
		include = new Bag<KeyCondition>();
		exclude = new Bag<KeyCondition>();
	}
	
	public KeyBoolCondition and(KeyCondition condition) {
		mandate.add(condition);
		return this;
	}
	
	public KeyBoolCondition or(KeyCondition condition) {
		include.add(condition);
		return this;
	}
	
	public KeyBoolCondition not(KeyCondition condition) {
		exclude.add(condition);
		return this;
	}
	
	@Override
	public boolean eval() {
		/*
		 * Note: Boolean methods on Predicates generate lambdas.
		 * That is, to say, very inefficient. Bad. It's bad.
		 * Don't use them.
		 */
		boolean and = true;
		boolean inc = false;
		boolean not = true;
		
		if (!mandate.isEmpty())
			for (KeyCondition condition : mandate)
				and &= condition.eval();
		
		if (!include.isEmpty())
			for (KeyCondition condition : include)
				inc |= condition.eval();
		else
			inc = true;
		
		if (!exclude.isEmpty())
			for (KeyCondition condition : exclude)
				not &= !condition.eval();
		
		return and && inc && not;
	}
	
}