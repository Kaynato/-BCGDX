package net.suizinshu.external;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Tracks key states and queues.
 * @author Zicheng Gao
 */
public class Manager_Keyboard {

	/** Actable key states. */
	public static final byte 
			KEY_NONE = 0x0,
			KEY_HELD = 0x1,
			KEY_PRESS = 0x2,
			KEY_RELEASE = 0x3;

	public static final int NUM_KEYS = 12;

	/** Directional keys. */
	public static Key 
			up = new Key(Input.Keys.UP, 0),
			down = new Key(Input.Keys.DOWN, 1),
			left = new Key(Input.Keys.LEFT, 2),
			right = new Key(Input.Keys.RIGHT, 3);

	/** Bindable quick-keys. */
	public static Key 
			bind1 = new Key(Input.Keys.Z, 4),
			bind2 = new Key(Input.Keys.X, 5),
			bind3 = new Key(Input.Keys.C, 6),
			bind4 = new Key(Input.Keys.A, 7),
			bind5 = new Key(Input.Keys.S, 8),
			bind6 = new Key(Input.Keys.D, 9);

	/** Action menu key. */
	public static Key menu = new Key(Input.Keys.CONTROL_LEFT, 10);

	/** Escape key. */
	public static Key escape = new Key(Input.Keys.ESCAPE, 11);

	/** Array of all keys. */
	public static Key[] keys = new Key[] {
		up, down, left, right,
		bind1, bind2, bind3,
		bind4, bind5, bind6,
		menu, escape };
	
	public static void press(int key) {
		for (Key chkKey : keys)
			if (chkKey.same(key))
				chkKey.press();
	}

	public static void release(int key) {
		for (Key chkKey : keys)
			if (chkKey.same(key))
				chkKey.release();
	}

	public static void dequeue() {
		for (Key chkKey : keys)
			chkKey.dequeue();
	}
	
	public static void initialize() {
		Gdx.input.setInputProcessor(new InputAdapter () {
			@Override
			public boolean keyDown(int keycode) {
				Manager_Keyboard.press(keycode);
				return true;
			}
			
			@Override
			public boolean keyUp(int keycode) {
				Manager_Keyboard.release(keycode);
				return true;
			}

		});
	}
	
	public static class Key {

		private int key;
		public final int INDEX;
		private byte state = KEY_NONE;

		public Key(int key, int index) {
			this.key = key;
			this.INDEX = index;
		}

		public void setKey(int key) { this.key = key; }

		public boolean same(int key) { return this.key == key; }

		public void press() { state = KEY_PRESS; }

		public void release() { state = KEY_RELEASE; }

		public byte state() { return state; }

		public void dequeue() {
			if (state == KEY_PRESS)
				state = KEY_HELD;
			if (state == KEY_RELEASE)
				state = KEY_NONE;
		}
	}

}
