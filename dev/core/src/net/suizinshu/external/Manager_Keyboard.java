package net.suizinshu.external;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Tracks key states and queues.
 * @author Zicheng Gao
 */
public class Manager_Keyboard {
	
	public static final class KeyConst {
		
		/** Actable key states. */
		public static final byte 
		KEY_NONE = 0x0,
		KEY_HELD = 0x1,
		KEY_PRESS = 0x2,
		KEY_RELEASE = 0x3;
		
		/** Keys to reference. */
		public static final byte
		UP 		= 0,
		DOWN 	= 1,
		LEFT 	= 2,
		RIGHT 	= 3,
		BIND1 	= 4,
		BIND2 	= 5,
		BIND3 	= 6,
		BIND4 	= 7,
		BIND5 	= 8,
		BIND6 	= 9,
		MENU 	= 10,
		ESC 	= 11,
		ANY 	= 12;
		
		/** FOR DETERMING SIZE OF KEYBINDING ARRAYS. */
		public static final int NUM_KEYS = 13;
		
	}
	
	public static final class KeyQuery {
		
		/** Shortcuts for down. True on HELD or PRESS. */
		public static final boolean U() {return down(KeyConst.UP);}
		public static final boolean D() {return down(KeyConst.DOWN);}
		public static final boolean L() {return down(KeyConst.LEFT);}
		public static final boolean R() {return down(KeyConst.RIGHT);}
		public static final boolean B1() {return down(KeyConst.BIND1);}
		public static final boolean B2() {return down(KeyConst.BIND2);}
		public static final boolean B3() {return down(KeyConst.BIND3);}
		public static final boolean B4() {return down(KeyConst.BIND4);}
		public static final boolean B5() {return down(KeyConst.BIND5);}
		public static final boolean B6() {return down(KeyConst.BIND6);}
		public static final boolean MENU() {return down(KeyConst.MENU);}
		public static final boolean ESC() {return down(KeyConst.ESC);}
		public static final boolean ANY() {return down(KeyConst.ANY);}
		
	}
	
	/** Reconfigurable keys. */
	public static int 
	k_up 	= Input.Keys.UP,
	k_down 	= Input.Keys.DOWN,
	k_left 	= Input.Keys.LEFT,
	k_right = Input.Keys.RIGHT,
	k_bind1 = Input.Keys.Z,
	k_bind2 = Input.Keys.X,
	k_bind3 = Input.Keys.C,
	k_bind4 = Input.Keys.A,
	k_bind5 = Input.Keys.S,
	k_bind6 = Input.Keys.D, 
	k_menu 	= Input.Keys.CONTROL_LEFT, 
	k_esc 	= Input.Keys.ESCAPE,
	k_any 	= Input.Keys.ANY_KEY;
	
	/** Array of all keys. */
	private static int[] keycodes = new int[] {
		k_up, k_down, k_left, k_right,
		k_bind1, k_bind2, k_bind3,
		k_bind4, k_bind5, k_bind6,
		k_menu, k_esc, k_any 
	};
	
	/** Corresponding array of all key states. */
	private static byte[] states = new byte[] {
		KeyConst.KEY_NONE, KeyConst.KEY_NONE, KeyConst.KEY_NONE, KeyConst.KEY_NONE,
		KeyConst.KEY_NONE, KeyConst.KEY_NONE, KeyConst.KEY_NONE, 
		KeyConst.KEY_NONE, KeyConst.KEY_NONE, KeyConst.KEY_NONE, 
		KeyConst.KEY_NONE, KeyConst.KEY_NONE, KeyConst.KEY_NONE
	};
	
	/** Number of keys pressed. */
	public static int numPressed = 0;
	
	
	///////////
	///////////
	///////////
	///////////
	///////////
	///////////
	///////////
	///////////
	///////////
	///////////
	
	/**
	 * Returns the state of a key (internally referenced key)
	 * @param key	Key queried.
	 * @return		State of key queried.
	 */
	public static byte state(byte key) {
		return states[key];
	}
	
	/**
	 * Queries the truth of a keystate equivalence.
	 * @param key	Key
	 * @param state	State
	 * @return		Equivalence
	 */
	public static boolean query(byte key, byte state) {
		return states[key] == state;
	}
	
	public static boolean down(byte key) {
		return query(key, KeyConst.KEY_PRESS) || query(key, KeyConst.KEY_HELD);
	}
	
	/** Dequeue all. Call at end of update. */
	public static void dequeue() {
		for (int i = 0; i < KeyConst.NUM_KEYS - 1; i++) {
			if (states[i] == KeyConst.KEY_PRESS)
				states[i] = KeyConst.KEY_HELD;
			else if (states[i] == KeyConst.KEY_RELEASE)
				states[i] = KeyConst.KEY_NONE;
		}
		
		if (numPressed > 0)
			set(k_any, KeyConst.KEY_HELD);
		else
			set(k_any, KeyConst.KEY_NONE);
	}
	
	public static void setKey(byte key, int keycode) {
		keycodes[key] = keycode;
	}
	
	/**
	 * Set a key to a value.
	 * @param keycode	Key to set value for.
	 * @param value	Value to set to.
	 */
	private static void set(int keycode, byte value) {
		for (byte i = 0; i < KeyConst.NUM_KEYS; i++)
			if (keycodes[i] == keycode)
				states[i] = value;
	}
	
	/**
	 * Link the key manager to the application. 
	 */
	public static void initialize() {
		Gdx.input.setInputProcessor(new InputAdapter () {
			@Override
			public boolean keyDown(int keycode) {
				numPressed++;
				set(k_any, KeyConst.KEY_PRESS);
				set(keycode, KeyConst.KEY_PRESS);
				return true;
			}
			
			@Override
			public boolean keyUp(int keycode) {
				numPressed--;
				set(k_any, KeyConst.KEY_RELEASE);
				set(keycode, KeyConst.KEY_RELEASE);
				return true;
			}
			
		});
	}
	
}