package net.suizinshu.external.logic;

import com.artemis.utils.Bag;


public abstract class BoolCondition<C> implements Condition<C>, BoolChainable<BoolCondition<C>, Condition<C>> {
	
	private Bag<Condition<C>> mandate;
	private Bag<Condition<C>> include;
	private Bag<Condition<C>> exclude;
	
	public BoolCondition() {
		mandate = new Bag<Condition<C>>();
		include = new Bag<Condition<C>>();
		exclude = new Bag<Condition<C>>();
	}
	
	public BoolCondition<C> and(Condition<C> condition) {
		mandate.add(condition);
		return this;
	}
	
	public BoolCondition<C> or(Condition<C> condition) {
		include.add(condition);
		return this;
	}
	
	public BoolCondition<C> not(Condition<C> condition) {
		exclude.add(condition);
		return this;
	}
	
	@Override
	public boolean test(C t) {
		/*
		 * Note: Boolean methods on Conditions generate lambdas.
		 * That is, to say, very inefficient. Bad. It's bad.
		 * Don't use them.
		 */
		boolean and = true;
		boolean inc = false;
		boolean not = true;
		
		if (!mandate.isEmpty())
			for (Condition<C> condition : mandate)
				and &= condition.test(t);
		
		if (!include.isEmpty())
			for (Condition<C> condition : include)
				inc |= condition.test(t);
		else
			inc = true;
		
		if (!exclude.isEmpty())
			for (Condition<C> condition : exclude)
				not &= !condition.test(t);
		
		return and && inc && not;
	}
	
}
