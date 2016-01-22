package net.suizinshu.external.logic;

import com.artemis.utils.Bag;


public class BoolCondition<T, U> implements Condition, BoolChainable<BoolCondition<T, U>, Matcher<T, U>> {
	
	private Bag<Matcher<T, U>> mandate;
	private Bag<Matcher<T, U>> include;
	private Bag<Matcher<T, U>> exclude;
	
	public BoolCondition() {
		mandate = new Bag<Matcher<T, U>>();
		include = new Bag<Matcher<T, U>>();
		exclude = new Bag<Matcher<T, U>>();
	}
	
	public BoolCondition<T, U> and(Matcher<T, U> condition) {
		mandate.add(condition);
		return this;
	}
	
	public BoolCondition<T, U> or(Matcher<T, U> condition) {
		include.add(condition);
		return this;
	}
	
	public BoolCondition<T, U> not(Matcher<T, U> condition) {
		exclude.add(condition);
		return this;
	}
	
	@Override
	public boolean test() {
		/*
		 * Note: Boolean methods on Predicates generate lambdas.
		 * That is, to say, very inefficient. Bad. It's bad.
		 * Don't use them.
		 */
		boolean and = true;
		boolean inc = false;
		boolean not = true;
		
		if (!mandate.isEmpty())
			for (Matcher<T, U> condition : mandate)
				and &= condition.test();
		
		if (!include.isEmpty())
			for (Matcher<T, U> condition : include)
				inc |= condition.test();
		else
			inc = true;
		
		if (!exclude.isEmpty())
			for (Matcher<T, U> condition : exclude)
				not &= !condition.test();
		
		return and && inc && not;
	}
	
}