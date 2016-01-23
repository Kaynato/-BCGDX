package net.suizinshu.external.logic;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.KeyConst;


public class KeyUtils {
	
	public static KeyConditional[] toggle(Script on, Script off, byte key) {
		
		return new KeyConditional[] {
				new KeyConditional(on, () -> Manager_Keyboard.query(key, KeyConst.KEY_PRESS)),
				new KeyConditional(off, () -> Manager_Keyboard.query(key, KeyConst.KEY_RELEASE))
		};
		
	}
	
}