package net.suizinshu.external.logic;

import com.artemis.utils.Bag;

public class BoolCondition<T, U> implements Condition, BoolChainable<BoolCondition<T, U>, ListCondition<T, U>> {
	
	private Bag<ListCondition<T, U>> mandate;
	private Bag<ListCondition<T, U>> include;
	private Bag<ListCondition<T, U>> exclude;
	
	public BoolCondition() {
		mandate = new Bag<ListCondition<T, U>>();
		include = new Bag<ListCondition<T, U>>();
		exclude = new Bag<ListCondition<T, U>>();
	}
	
	public BoolCondition<T, U> and(ListCondition<T, U> condition) {
		mandate.add(condition);
		return this;
	}
	
	public BoolCondition<T, U> or(ListCondition<T, U> condition) {
		include.add(condition);
		return this;
	}
	
	public BoolCondition<T, U> not(ListCondition<T, U> condition) {
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
			for (ListCondition<T, U> condition : mandate)
				and &= condition.eval();
		
		if (!include.isEmpty())
			for (ListCondition<T, U> condition : include)
				inc |= condition.eval();
		else
			inc = true;
		
		if (!exclude.isEmpty())
			for (ListCondition<T, U> condition : exclude)
				not &= !condition.eval();
		
		return and && inc && not;
	}
	
}