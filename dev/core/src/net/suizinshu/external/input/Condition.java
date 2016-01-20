package net.suizinshu.external.input;

/**
 * Initializer class for Conditions.
 * @author Zicheng Gao
 *
 */
public class Condition {
	
	public static ConditionByte and(byte state, CheckableByte... check) {
		return new ConditionByte(ConditionByte.MANDATE, state, check);
	}
	
	public static ConditionByte or(byte state, CheckableByte... check) {
		return new ConditionByte(ConditionByte.INCLUDE, state, check);
	}
	
	public static ConditionByte not(byte state, CheckableByte... check) {
		return new ConditionByte(ConditionByte.EXCLUDE, state, check);
	}
	
	public static ConditionByte one(byte state, CheckableByte... check) {
		return new ConditionByte(ConditionByte.UNITARY, state, check);
	}
	
	public static ConditionByte any(byte state, CheckableByte... check) {
		return new ConditionByte(ConditionByte.MANDATE_ANY, state, check);
	}
	
}
