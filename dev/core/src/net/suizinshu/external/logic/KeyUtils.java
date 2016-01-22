package net.suizinshu.external.logic;

import net.suizinshu.external.Manager_Keyboard.KeyConst;


public class KeyUtils {
	
	public static KeyConditional[] toggle(Script on, Script off, Byte... key) {
		
		return new KeyConditional[] {
				new KeyConditional(on, Condition.AND, KeyConst.KEY_PRESS, key),
				new KeyConditional(off, Condition.AND, KeyConst.KEY_RELEASE, key)
		};
		
	}
	
}