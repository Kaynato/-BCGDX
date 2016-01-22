package net.suizinshu.external.logic;

import com.artemis.utils.Bag;


public class BoolCondition<T, U> implements Condition<T, U>,
		BoolChainable<BoolCondition<T, U>, Condition<T, U>> {
	
	private Bag<Condition<T, U>> mandate;
	private Bag<Condition<T, U>> include;
	private Bag<Condition<T, U>> exclude;
	
	public BoolCondition() {
		mandate = new Bag<Condition<T, U>>();
		include = new Bag<Condition<T, U>>();
		exclude = new Bag<Condition<T, U>>();
	}
	
	public BoolCondition<T, U> and(Condition<T, U> condition) {
		mandate.add(condition);
		return this;
	}
	
	public BoolCondition<T, U> or(Condition<T, U> condition) {
		include.add(condition);
		return this;
	}
	
	public BoolCondition<T, U> not(Condition<T, U> condition) {
		exclude.add(condition);
		return this;
	}
	
	@Override
	public boolean test(T t, U[] u) {
		/*
		 * Note: Boolean methods on Predicates generate lambdas.
		 * That is, to say, very inefficient. Bad. It's bad.
		 * Don't use them.
		 */
		boolean and = true;
		boolean inc = false;
		boolean not = true;
		
		if (!mandate.isEmpty())
			for (Condition<T, U> condition : mandate)
				and &= condition.test(t, u);
		
		if (!include.isEmpty())
			for (Condition<T, U> condition : include)
				inc |= condition.test(t, u);
		else
			inc = true;
		
		if (!exclude.isEmpty())
			for (Condition<T, U> condition : exclude)
				not &= !condition.test(t, u);
		
		return and && inc && not;
	}
	
}
