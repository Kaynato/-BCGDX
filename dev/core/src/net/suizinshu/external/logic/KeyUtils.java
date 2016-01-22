package net.suizinshu.external.logic;

import net.suizinshu.external.Manager_Keyboard;


public class KeyUtils {
	
	public static KeyConditional[] toggle(byte key, Script on, Script off) {
		
		return new KeyConditional[] {
				new KeyConditional(on, Manager_Keyboard.KEY_PRESS, key),
				new KeyConditional(off, Manager_Keyboard.KEY_RELEASE, key)
		};
		
	}
	
	
	
}
