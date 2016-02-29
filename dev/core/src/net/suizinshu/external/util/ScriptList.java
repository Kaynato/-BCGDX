package net.suizinshu.external.util;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Allows for composition of scripts.
 * @author Zicheng Gao
 */
public class ScriptList implements Consumer<Integer> {
	
	private ArrayList<Consumer<Integer>> list;
	
	public ScriptList(int initialCapacity) {
		list = new ArrayList<Consumer<Integer>>(initialCapacity);
	}
	
	public ScriptList() {
		this(1);
	}

	public void clear() {
		list.clear();
	}
	
	public void set(Consumer<Integer> scr) {
		clear();
		add(scr);
	}
	
	public void add(Consumer<Integer> scr) {
		list.add(scr);
	}
	
	public boolean remove(Consumer<Integer> scr) {
		list.remove(scr);
		return list.isEmpty();
	}
	
	@Override
	public void accept(Integer entityId) {
		for (Consumer<Integer> scr : list)
			scr.accept(entityId);
	}
	
}
