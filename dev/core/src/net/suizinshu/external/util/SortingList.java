package net.suizinshu.external.util;

import java.util.ArrayList;
import java.util.Collections;

public class SortingList<T extends Comparable<T>> extends ArrayList<T> {

	private static final long serialVersionUID = -698847894879804206L;

	public boolean sorting = false;
	
	public void monitor() { sorting = true; }
	
	public void stop() { sorting = false; }
	
	@Override
	public boolean add(T e) {
		super.add(e);
		if (sorting)
			Collections.sort(this);
		return true;
	}
	
}
