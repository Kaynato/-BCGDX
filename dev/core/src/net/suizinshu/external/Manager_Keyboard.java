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
	
	public static final int NUM_KEYS = 12;
	
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
	
	/**
	 * Bind an action to a key and state. Can override existing bindings!
	 * @param key		Key to bind action to	
	 * @param state		State to bind action to
	 * @param binding	Action to bind
	 */
	public static void bind(Keybinding[] bindings, Key key, byte state, Script binding) {
		int index = key.INDEX;
		
		if (bindings[index] == null)
			bindings[index] = new Keybinding(key);
		
		bindings[index].add(state, binding);
	}
	
	public static void bind(Keybinding[] bindings, Keybinding toBind) {
		bindings[toBind.keyCallback.INDEX] = toBind;
	}
	
	public static void bind(Keybinding[] bindings, Keybinding[] toBind) {
		for (Keybinding binding : toBind)
			Manager_Keyboard.bind(bindings, binding);
	}
	
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
	
	public static class Keybinding {
		
		private final Key keyCallback;
		
		private ScriptList onPress = new ScriptList();
		private ScriptList onRelease = new ScriptList();
		private ScriptList onHeld = new ScriptList();
		private ScriptList onNone = new ScriptList();
		
		private boolean chkPress = false;
		private boolean chkRelease = false;
		private boolean chkHeld = false;
		private boolean chkNone = false;
		
		public Keybinding(Key key) {
			this.keyCallback = key;
		}
		
		public void add(byte state, Script script) {
			Script action = script;
			
			switch (state) {
				case KEY_PRESS:
					onPress.add(action);
					chkPress = true;
					break;
				case KEY_RELEASE:
					onRelease.add(action);
					chkRelease = true;
					break;
				case KEY_HELD:
					onHeld.add(action);
					chkHeld = true;
					break;
				case KEY_NONE:
					onNone.add(action);
					chkNone = true;
					break;
			}
		}
		
		public void set(byte state, Script action) {
			switch (state) {
				case KEY_PRESS:
					onPress.set(action);
					chkPress = true;
					break;
				case KEY_RELEASE:
					onRelease.set(action);
					chkRelease = true;
					break;
				case KEY_HELD:
					onHeld.set(action);
					chkHeld = true;
					break;
				case KEY_NONE:
					onNone.set(action);
					chkNone = true;
					break;
			}
		}
		
		public void remove(byte state, Script action) {
			switch (state) {
				case KEY_PRESS:
					chkPress = !onPress.remove(action);
					break;
				case KEY_RELEASE:
					chkRelease = !onRelease.remove(action);
					break;
				case KEY_HELD:
					chkHeld = !onHeld.remove(action);;
					break;
				case KEY_NONE:
					chkNone = !onNone.remove(action);;
					break;
			}
		}
		
		public void checkAndPerform(int entityId) {
			if (keyCallback.state() == KEY_PRESS) {
				if (chkPress)
					onPress.perform(entityId);
			}
			else if (keyCallback.state() == KEY_RELEASE) {
				if (chkRelease)
					onRelease.perform(entityId);
			}
			else if (keyCallback.state() == KEY_HELD) {
				if (chkHeld)
					onHeld.perform(entityId);
			}
			else if (keyCallback.state() == KEY_NONE) {
				if (chkNone)
					onNone.perform(entityId);
			}
		}
		
		public void toggle(Script on, Script off) {
			set(KEY_PRESS, on);
			set(KEY_RELEASE, off);
		}
	}
	
}
