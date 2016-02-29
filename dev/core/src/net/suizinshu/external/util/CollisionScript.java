package net.suizinshu.external.util;

import com.artemis.World;

@FunctionalInterface
public interface CollisionScript {

	public boolean perform(World world, int self, int other);
	
}
