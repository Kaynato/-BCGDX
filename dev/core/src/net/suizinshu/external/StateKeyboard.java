package net.suizinshu.external;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Tracks key states and queues.
 * @author Zicheng Gao
 */
public class StateKeyboard {

	public class KeyByte {

		/** Actable key states. */
		public static final byte
		KEY_NONE 	= 0b00,
		KEY_HELD 	= 0b01,
		KEY_RELEASE = 0b10,
		KEY_PRESS 	= 0b11;

		/**
		 * Internal values of keys that can be polled within the game engine.<br>
		 * As indices, these allow for quick and efficient retrieval, since
		 * polling events take place every tick.
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

	/** Corresponding array of all key codes in the order that the keys index. */
	private static int[] keycodes = new int[] {
		k_up, k_down, k_left, k_right,
		k_bind1, k_bind2, k_bind3,
		k_bind4, k_bind5, k_bind6,
		k_menu, k_esc, k_any 
	};

	/** Corresponding array of all key states. */
	private static byte[] states = new byte[] {
		KeyByte.KEY_NONE, KeyByte.KEY_NONE, KeyByte.KEY_NONE, KeyByte.KEY_NONE,
		KeyByte.KEY_NONE, KeyByte.KEY_NONE, KeyByte.KEY_NONE, 
		KeyByte.KEY_NONE, KeyByte.KEY_NONE, KeyByte.KEY_NONE, 
		KeyByte.KEY_NONE, KeyByte.KEY_NONE, KeyByte.KEY_NONE
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

	/**
	 * Single operation returning true if keystate is DOWN or HELD.
	 * @param key	Key
	 * @return		If Key is DOWN or HELD.
	 */
	public static boolean isDown(byte key) {
		return states[key] % 2 == 1;
	}

	/** Dequeue all. Call at end of update. */
	public static void dequeue() {
		for (int i = 0; i < states.length - 1; i++)
			states[i] %= 2;

		if (numPressed > 0)
			set(k_any, KeyByte.KEY_HELD);
		else
			set(k_any, KeyByte.KEY_NONE);
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
		for (byte i = 0; i < states.length; i++)
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
				set(k_any, KeyByte.KEY_PRESS);
				set(keycode, KeyByte.KEY_PRESS);
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				numPressed--;
				set(k_any, KeyByte.KEY_RELEASE);
				set(keycode, KeyByte.KEY_RELEASE);
				return true;
			}

		});
	}

}