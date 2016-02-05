package net.suizinshu.external.collision;


public class CollisionAlgorithm {

	public CollisionAlgorithm() {
		// TODO Auto-generated constructor stub
	}
	
	@FunctionalInterface
	public static interface CollisionFunction {
		public boolean check(CollisionBase base1, CollisionBase base2);
	};

}
