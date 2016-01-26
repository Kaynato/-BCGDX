package net.suizinshu.external;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Tracks key states and queues.
 * @author Zicheng Gao
 */
public class Manager_Keyboard {

	/**
	 * STATE is a bitmask of 26 bits. Do not change from 26 bits.<br>
	 * These 26 bits are used to represent 13 keys, 
	 */
	public static int STATE = 0;

	public class KeyConst {

		/** Actable key states. */
		public static final byte
		KEY_NONE 	= 0b00,
		KEY_HELD 	= 0b01,
		KEY_RELEASE = 0b10,
		KEY_PRESS 	= 0b11;

		/**
		 * Internal values of keys that can be polled within the game engine.<br>
		 * As indices, these allow for quick and efficient retrieval, since
		 * polling events take place every tick.<br> 
		 */
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
		public static final boolean U() {return isDown(KeyConst.UP);}
		public static final boolean D() {return isDown(KeyConst.DOWN);}
		public static final boolean L() {return isDown(KeyConst.LEFT);}
		public static final boolean R() {return isDown(KeyConst.RIGHT);}
		public static final boolean B1() {return isDown(KeyConst.BIND1);}
		public static final boolean B2() {return isDown(KeyConst.BIND2);}
		public static final boolean B3() {return isDown(KeyConst.BIND3);}
		public static final boolean B4() {return isDown(KeyConst.BIND4);}
		public static final boolean B5() {return isDown(KeyConst.BIND5);}
		public static final boolean B6() {return isDown(KeyConst.BIND6);}
		public static final boolean MENU() {return isDown(KeyConst.MENU);}
		public static final boolean ESC() {return isDown(KeyConst.ESC);}
		public static final boolean ANY() {return isDown(KeyConst.ANY);}

		/** i for instant. Shortcuts for press (true) and release (false). */
		public static final boolean iU(boolean type) {return imm(KeyConst.UP, type);}
		public static final boolean iD(boolean type) {return imm(KeyConst.DOWN, type);}
		public static final boolean iL(boolean type) {return imm(KeyConst.LEFT, type);}
		public static final boolean iR(boolean type) {return imm(KeyConst.RIGHT, type);}
		public static final boolean iB1(boolean type) {return imm(KeyConst.BIND1, type);}
		public static final boolean iB2(boolean type) {return imm(KeyConst.BIND2, type);}
		public static final boolean iB3(boolean type) {return imm(KeyConst.BIND3, type);}
		public static final boolean iB4(boolean type) {return imm(KeyConst.BIND4, type);}
		public static final boolean iB5(boolean type) {return imm(KeyConst.BIND5, type);}
		public static final boolean iB6(boolean type) {return imm(KeyConst.BIND6, type);}
		public static final boolean iMENU(boolean type) {return imm(KeyConst.MENU, type);}
		public static final boolean iESC(boolean type) {return imm(KeyConst.ESC, type);}
		public static final boolean iANY(boolean type) {return imm(KeyConst.ANY, type);}

	}

	/** Reconfigurable keys. */
	private static int 
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

	public static boolean isDown(byte key) {
		return states[key] % 2 == 1;
	}

	/**
	 * Return immediate state.
	 * @param key	Key
	 * @param type	True: PRESS, False: RELEASE
	 * @return		Query corectness
	 */
	public static boolean imm(byte key, boolean type) {
		return query(key, (type) ? (KeyConst.KEY_PRESS) : (KeyConst.KEY_RELEASE));
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