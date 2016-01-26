package net.suizinshu.external.logic;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.KeyConst;
import net.suizinshu.external.logic.KeyBinder.KeyEvaluable;

import com.artemis.utils.Bag;

public class KeyBinder extends Bag<KeyEvaluable> implements Consumer<Integer>, Toggleable {
	
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
	
	//
	//
	//
	
	@FunctionalInterface
	public static interface KeyCondition extends BooleanSupplier {
		
	}
	
	@FunctionalInterface
	public interface KeyEvaluable extends Consumer<Integer> {
		
	}
	
	//
	//
	
	public static class KeySplitConditional extends SplitConditional<Integer, Byte, Byte> implements KeyEvaluable {
		
		public KeySplitConditional(Consumer<Integer> onTrue, Consumer<Integer> onOff, KeyCondition condition) {
			super(onTrue, onOff, condition);
		}
		
	}

	public static class KeyConditional extends Conditional<Integer, Byte, Byte> implements KeyEvaluable {
		
		public KeyConditional(Consumer<Integer> script, KeyCondition condition) {
			super(script, condition);
		}
		
	}
	
	public static class KeyUtils {
		
		public static KeyConditional[] toggle(Consumer<Integer> on, Consumer<Integer> off, byte key) {
			
			return new KeyConditional[] {
					new KeyConditional(on, () -> Manager_Keyboard.query(key, KeyConst.KEY_PRESS)),
					new KeyConditional(off, () -> Manager_Keyboard.query(key, KeyConst.KEY_RELEASE))
			};
			
		}
		
	}
	
}