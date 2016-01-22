package net.suizinshu.external.logic;

/**
 * Returns a boolean value when evaluated.
 * @author Zicheng Gao
 */
@FunctionalInterface
public interface Condition {
	
	public static final byte
	AND 	= 0b00,
	OR 		= 0b01,
	NAND 	= 0b10,
	NOR 	= 0b11;
	
	public boolean eval();
	
}
