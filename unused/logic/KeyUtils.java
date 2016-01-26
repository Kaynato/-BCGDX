package net.suizinshu.external.logic;

import java.util.function.Consumer;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.KeyConst;
import net.suizinshu.external.logic.KeyBinder.KeyConditional;


public class KeyUtils {
	
	public static KeyConditional[] toggle(Consumer<Integer> on, Consumer<Integer> off, byte key) {
		
		return new KeyConditional[] {
				new KeyConditional(on, () -> Manager_Keyboard.query(key, KeyConst.KEY_PRESS)),
				new KeyConditional(off, () -> Manager_Keyboard.query(key, KeyConst.KEY_RELEASE))
		};
		
	}
	
}