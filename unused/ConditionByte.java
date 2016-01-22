package net.suizinshu.external.logic;

import java.util.ArrayList;

import com.artemis.utils.Bag;


/**
 * Boolean condition - bytes only. Objects? Whatever. In the name of memory, I march forward.
 * Primitives...
 * @author Zicheng Gao
 */
public class ConditionByte {
	
	public static final byte
	MANDATE = 0x0,
	INCLUDE = 0x1,
	EXCLUDE = 0x2,
	UNITARY = 0x3,
	MANDATE_ANY = 0x4;
	
	/**
	 * A LIST OF
	 * 	ONE BYTE STATE - AT LEAST ONE KEY
	 * 
	 * BOOL_OP(checkablebyte thing, byte state)
	 * 
	 * Bunch of keys MATCH one byte state.
	 * 
	 * For example: One of the arrow keys but not the others.
	 */
	
	private Bag<CheckValueByte> mandate = new Bag<CheckValueByte>(16);
	private Bag<CheckValueByte> include = new Bag<CheckValueByte>(16);
	private Bag<CheckValueByte> exclude = new Bag<CheckValueByte>(16);
	
	private Bag<CheckValueByte> mandateOne = new Bag<CheckValueByte>(16);
	private Bag<CheckValueByte> mandateAny = new Bag<CheckValueByte>(16);
	
	/**
	 * Do not use unless necessary.
	 * Single-condition initializer.
	 * @param type	Type of check.
	 * @param state	Target state.
	 * @param check	Elements to check.
	 */
	protected ConditionByte (byte type, byte state, CheckableByte... check) {
		switch (type) {
			case MANDATE:
				mandate.add(new CheckValueByte(state, check));
				break;
			case INCLUDE:
				include.add(new CheckValueByte(state, check));
				break;
			case EXCLUDE:
				exclude.add(new CheckValueByte(state, check));
				break;
			case UNITARY:
				mandateOne.add(new CheckValueByte(state, check));
				break;
			case MANDATE_ANY:
				mandateAny.add(new CheckValueByte(state, check));
				break;
			default:
				throw new IndexOutOfBoundsException("Type of condition was incorrect.");
		}
	}
	
	public ConditionByte and(byte state, CheckableByte... check) {
		mandate.add(new CheckValueByte(state, check));
		return this;
	}
	
	public ConditionByte or(byte state, CheckableByte... check) {
		include.add(new CheckValueByte(state, check));
		return this;
	}
	
	public ConditionByte not(byte state, CheckableByte... check) {
		exclude.add(new CheckValueByte(state, check));
		return this;
	}
	
	public ConditionByte one(byte state, CheckableByte... check) {
		mandateOne.add(new CheckValueByte(state, check));
		return this;
	}

	public ConditionByte any(byte state, CheckableByte... check) {
		mandateAny.add(new CheckValueByte(state, check));
		return this;
	}
	
	public void clear() {
		mandate.clear();
		include.clear();
		exclude.clear();
		mandateOne.clear();
		mandateAny.clear();
	}
	
	public boolean check() {
		
		boolean and = true;
		boolean inc = true;
		boolean not = true;
		boolean one = true;
		boolean any = true;
		
		if (!mandate.isEmpty())
			for (CheckValueByte cvb : mandate)
				and &= cvb.all();
		
		if (!include.isEmpty()) {
			inc = false;
			for (CheckValueByte cvb : include)
				inc |= cvb.all();
		}
		
		if (!exclude.isEmpty())
			for (CheckValueByte cvb : exclude)
				and &= !cvb.all();
		
		if (!mandateOne.isEmpty())
			for (CheckValueByte cvb : mandateOne)
				one &= cvb.any();
		
		if (!mandateAny.isEmpty())
			for (CheckValueByte cvb : mandateAny)
				any &= cvb.any();
		
		return and && inc && not && one && any;
	}
	
	
	public class CheckValueByte {
		
		/** The byte value to check for among the held array. */
		public byte check;
		
		/** The array to check for the byte value. */
		public ArrayList<CheckableByte> held = new ArrayList<CheckableByte>();
		
		public CheckValueByte(byte check, CheckableByte... toHold) {
			this.check = check;
			for (CheckableByte toAdd : toHold)
				held.add(toAdd);
		}
		
		/** HOW MANY CHECKABLES MATCH THE TARGET */
		private int sum() {
			int sum = 0;
			for (CheckableByte toCheck : held)
				if (toCheck.check() == check)
					sum++;
			return sum;
		}
		
		/** Default - all checkables match the target. */
		public boolean all() {
			boolean match = true;
			for (CheckableByte toCheck : held)
				match &= (toCheck.check() == check);
			return match;
		}
		
		/** Any - any checkable matches the target. */
		public boolean any() {
			boolean match = false;
			for (CheckableByte toCheck : held)
				match |= (toCheck.check() == check);
			return match;
		}
		
		/** One - one checkable matches the target. */
		public boolean one() {
			return sum() == 1;
		}

		/** Arbitary number matching target. */
		public boolean match(int num) {
			return sum() == num;
		}
		
	}
	
}