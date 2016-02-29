package net.suizinshu.external.util;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import net.suizinshu.external.StateKeyboard;
import net.suizinshu.external.StateKeyboard.KeyByte;

import com.artemis.utils.Bag;

/**
 * Utility class for Key-related implementations of Conditional and KeyBinder.<br>
 * A containing class for Key binding and helpful methods.<br>
 * <br>
 * A robust, composed key-event handler.
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
	 * @return		Two {@link KeyConditional}s containing these conditionals.
	 */
	public static KeyConditional[] toggle(Consumer<Integer> on, Consumer<Integer> off, byte key) {
		return new KeyConditional[] {
				new KeyConditional(on, () -> StateKeyboard.query(key, KeyByte.KEY_PRESS)),
				new KeyConditional(off, () -> StateKeyboard.query(key, KeyByte.KEY_RELEASE))
		};	
	}
	
	//_____________________________//
	//                             //
	//                             //
	//          INTERFACES         //
	//                             //
	//_____________________________//
	//							   //
	
	/**
	 * Identifier class for BooleanSuppliers that query keystates.<br>
	 * Unfortunately, it's best to use this because BooleanSupplier by itself as an identifier
	 * could easily be confused and misused.
	 * @author Zicheng Gao
	 */
	@FunctionalInterface
	public static interface KeyCondition extends BooleanSupplier {}
	
	/**
	 * Identifier class for {@link Consumer} that specifies Integer 
	 * and makes key-related types more distinct. 
	 * @author Zicheng Gao
	 */
	@FunctionalInterface
	public interface KeyEvaluable extends Consumer<Integer> {}

	//_____________________________//
	//                             //
	//                             //
	//          SUBCLASSES         //
	//                             //
	//_____________________________//
	//							   //
	
	/**
	 * A {@link Conditional} which acts upon keys.
	 * @author Zicheng Gao
	 */
	public static class KeyConditional extends Conditional<Integer, Byte, Byte> implements KeyEvaluable {
		
		/**
		 * Constructor for act-on-true conditional.
		 * @param script	Script to run on true
		 * @param condition	Condition to test
		 */
		public KeyConditional(Consumer<Integer> script, KeyCondition condition) {
			super(script, condition);
		}
		
		/**
		 * Constructor for act-on-true and other-on-false conditional.
		 * @param script	runs on true
		 * @param alternate	runs on false
		 * @param condition	gets tested
		 */
		public KeyConditional(Consumer<Integer> script, Consumer<Integer> alternate, KeyCondition condition) {
			super(script, alternate, condition);
		}
		
	}
	
	/**
	 * The end-all-be-all of the Keybinding system.<br>
	 * A {@link Bag} of {@link KeyEvaluable}. A collection for individual containment as a unit.
	 * @author Zicheng Gao
	 */
	public static class KeyBinder extends Bag<KeyEvaluable> implements Consumer<Integer> {
		
		private boolean active = true;
		
		/* Factory class possible for this? */
		
		public KeyBinder(KeyEvaluable... bindings) {
			for (KeyEvaluable binding : bindings)
				this.add(binding);
		}
		
		public KeyBinder(KeyEvaluable[]... bindingss) {
			for (KeyEvaluable[] bindings : bindingss)
				for (KeyEvaluable binding : bindings)
					this.add(binding);
		}
		
		/**
		 * Autocomposition constructor.
		 * @param binders	Elements to compose.
		 */
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

		/** Activate. */
		public void on() { active = true; }

		/** Deactivate. */
		public void off() { active = false; }
		
	}

}
