package net.suizinshu.external.logic;

import net.suizinshu.external.Manager_Keyboard.Keybinding;




/**
 * Largest keybinding system.<br>
 * Binding of a scriptlist to a boolean collection of key state conditions.<br>
 * Do not extend.
 * @author Zicheng Gao
 */
public class BoolBind implements Keybinding {
	
	/** Editable. */
	public ScriptList list;
	
	private ConditionByte condition;
	
	public BoolBind(ScriptList list, ConditionByte condition) {
		this.list = list;
		this.condition = condition;
	}

	public void checkAndPerform(int entityId) {
		if (condition.check())
			list.accept(entityId);
	}
	
	public void clear() {
		condition.clear();
	}

	@Override
	public void add(byte state, Script script) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(byte state, Script script) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(byte state, Script script) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toggle(Script on, Script off) {
		// TODO Auto-generated method stub
		
	}
	
}
