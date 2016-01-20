package net.suizinshu.external;

import net.suizinshu.external.input.CheckableByte;
import net.suizinshu.external.input.Script;
import net.suizinshu.external.input.ScriptList;

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
	
	/** Any key. */
	public static Key any = new Key(Input.Keys.ANY_KEY, 12);
	
	/** Number of keys pressed. */
	public static int numPressed = 0;
	
	/** FOR DETERMING SIZE OF KEYBINDING ARRAYS. */
	public static final int NUM_KEYS = 13;
	
	/** Array of all keys except for 'any'  */
	private static Key[] keys = new Key[] {
		up, down, left, right,
		bind1, bind2, bind3,
		bind4, bind5, bind6,
		menu, escape };
	
	
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
		numPressed++;
		any.press();
		for (Key chkKey : keys)
			if (chkKey.same(key))
				chkKey.press();
	}
	
	public static void release(int key) {
		numPressed--;
		any.release();
		for (Key chkKey : keys)
			if (chkKey.same(key))
				chkKey.release();
	}
	
	public static void dequeue() {
		if (numPressed > 0)
			any.state = KEY_HELD;
		else
			any.state = KEY_NONE;
		for (Key chkKey : keys)
			chkKey.dequeue();
	}
	
	/**
	 * Bind an action to a key and state. Can override existing bindings!
	 * @param key		Key to bind action to	
	 * @param state		State to bind action to
	 * @param binding	Action to bind
	 */
	public static void skBind(Keybinding[] bindings, Key key, byte state, Script binding) {
		int index = key.INDEX;
		
		if (bindings[index] == null)
			bindings[index] = new SingleKeybinding(key);
		
		bindings[index].add(state, binding);
	}
	
	public static void skBind(Keybinding[] bindings, SingleKeybinding toBind) {
		bindings[toBind.keyCallback.INDEX] = toBind;
	}
	
	public static void skBind(Keybinding[] bindings, SingleKeybinding[] toBind) {
		for (SingleKeybinding binding : toBind)
			Manager_Keyboard.skBind(bindings, binding);
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
	
	public static class Key implements CheckableByte {
		
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
		
		public byte check() { return state; }
		
		public void dequeue() {
			if (state == KEY_PRESS)
				state = KEY_HELD;
			if (state == KEY_RELEASE)
				state = KEY_NONE;
		}
	}
	
	public static interface Keybinding {
		public void add(byte state, Script script);
		
		public void set(byte state, Script script);
		
		public void remove(byte state, Script script);
		
		public void checkAndPerform(int entityId);
		
		public void toggle(Script on, Script off);
	}
	
	public static abstract class KeyBindSys {
		
		public KeyBindSys(Keybinding... bindings) {
			for (Keybinding kb : bindings)
				bind(kb);
		}
		
		public abstract void bind(Keybinding binding);
		
		public abstract void checkAndPerform(int entityId);
		
	}
	
	public static class SingleKeyBindSys extends KeyBindSys {

		public SingleKeybinding[] bindings = new SingleKeybinding[NUM_KEYS];

		public SingleKeyBindSys(SingleKeybinding... bindings) {
			super(bindings);
		}
		
		@Override
		public void checkAndPerform(int entityId) {
			for (SingleKeybinding skb : bindings) {
				skb.checkAndPerform(entityId);
			}
		}

		@Override
		public void bind(Keybinding binding) {
			skBind(this.bindings, bindings);
		}
		
	}
	
	public static class SingleKeybinding implements Keybinding {
		
		private final Key keyCallback;
		
		private ScriptList onPress = new ScriptList();
		private ScriptList onRelease = new ScriptList();
		private ScriptList onHeld = new ScriptList();
		private ScriptList onNone = new ScriptList();
		
		private boolean chkPress = false;
		private boolean chkRelease = false;
		private boolean chkHeld = false;
		private boolean chkNone = false;
		
		public SingleKeybinding(Key key) {
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
			if (keyCallback.check() == KEY_PRESS) {
				if (chkPress)
					onPress.perform(entityId);
			}
			else if (keyCallback.check() == KEY_RELEASE) {
				if (chkRelease)
					onRelease.perform(entityId);
			}
			else if (keyCallback.check() == KEY_HELD) {
				if (chkHeld)
					onHeld.perform(entityId);
			}
			else if (keyCallback.check() == KEY_NONE) {
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
