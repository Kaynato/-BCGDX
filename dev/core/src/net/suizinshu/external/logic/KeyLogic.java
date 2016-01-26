package net.suizinshu.external.logic;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.KeyConst;

import com.artemis.utils.Bag;

/**
 * Utility class for Key-related implementations of Conditional and KeyBinder.<br>
 * A containing class for Key binding and helpful methods.
 * @author Zicheng Gao
 */
public class KeyLogic {

	/** Prohibited. */
	private KeyLogic() {}
	
	/**
	 * Utility and shortcut for assigning two scripts to respectively the press and release of a key.
	 * @param on	Script to activate on press.
	 * @param off	Script to activate on release.
	 * @param key	Key to check for.
	 * @return		Two KeyConditionals containing these conditionals.
	 */
	public static KeyConditional[] toggle(Consumer<Integer> on, Consumer<Integer> off, byte key) {
		return new KeyConditional[] {
				new KeyConditional(on, () -> Manager_Keyboard.query(key, KeyConst.KEY_PRESS)),
				new KeyConditional(off, () -> Manager_Keyboard.query(key, KeyConst.KEY_RELEASE))
		};	
	}
	
	@FunctionalInterface
	public static interface KeyCondition extends BooleanSupplier {}
	
	@FunctionalInterface
	public interface KeyEvaluable extends Consumer<Integer> {}

	public static class KeyConditional extends Conditional<Integer, Byte, Byte> implements KeyEvaluable {
		
		public KeyConditional(Consumer<Integer> script, KeyCondition condition) {
			super(script, condition);
		}
		
		public KeyConditional(Consumer<Integer> script, Consumer<Integer> alternate, KeyCondition condition) {
			super(script, alternate, condition);
		}
		
	}
	
	public static class KeyBinder extends Bag<KeyEvaluable> implements Consumer<Integer>, Toggleable {
		
		private boolean active = true;
		
		public KeyBinder(KeyEvaluable... bindings) {
			for (KeyEvaluable binding : bindings)
				this.add(binding);
		}
		
		public KeyBinder(KeyEvaluable[]... bindingss) {
			for (KeyEvaluable[] bindings : bindingss)
				for (KeyEvaluable binding : bindings)
					this.add(binding);
		}
		
		public KeyBinder(KeyBinder... binders) {
			for (KeyBinder binder : binders)
				this.addAll(binder);
		}
		
		@Override
		public void accept(Integer entityId) {
			if (active)
				for (KeyEvaluable binding : this)
					binding.accept(entityId);;
		}

		public void on() { active = true; }

		public void off() { active = false; }
		
	}

}
