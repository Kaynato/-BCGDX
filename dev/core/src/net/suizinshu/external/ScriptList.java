package net.suizinshu.external;

import java.util.ArrayList;


public class ScriptList implements Script {
	
	private ArrayList<Script> list;
	
	public ScriptList(int initialCapacity) {
		list = new ArrayList<Script>(initialCapacity);
	}
	
	public ScriptList() {
		this(1);
	}

	public void clear() {
		list.clear();
	}
	
	public void set(Script scr) {
		clear();
		add(scr);
	}
	
	public void add(Script scr) {
		list.add(scr);
	}
	
	public boolean remove(Script scr) {
		list.remove(scr);
		return list.isEmpty();
	}
	
	@Override
	public void perform(int entityId) {
		for (Script scr : list)
			scr.perform(entityId);
	}
	
}
