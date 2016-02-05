package net.suizinshu.external;

import com.badlogic.gdx.math.Vector3;

public class _Test2 {

	public static void main(String[] args) {
		int base = 32;
		
		int length = base * base;
		
		CollideSphere[] thingList = new CollideSphere[length];
		
		for (int i = 0; i < thingList.length; i++) {
			thingList[i] = CollideSphere.rand();
		}
		
		long startTime = System.nanoTime();
		
		int collisionsProcessed = 0;
		int spheresCollided = 0;
		
		// Perform map checking
		for (int i = 0; i < thingList.length; i++)
			for (int j = 0; j < thingList.length; j++) {
				if ((i != j) && ((thingList[i].selfMask & thingList[j].otherMask) != 0)) {
					// Woe - only if this were a FUNCTIONAL LANGUAGE ;-;
					// then you can run the onCollide script that belongs to the thinglist[i]...
					collisionsProcessed++;
					// "process collision"
					if (collisionSphereSphere(thingList, i, j))
						spheresCollided++;
					
				}
			}
		
		
		
		
		long diffNano = System.nanoTime() - startTime;
		Double timeDiff = diffNano / 1e9;
		
		System.out.println(collisionsProcessed + " collisions processed.");
		System.out.println(spheresCollided + " spheres collided.");
		System.out.println(timeDiff.toString() + " seconds elapsed for " + length + " objects.");
		
		System.out.println(1f/60f + " is 60fps.");
		
	}

	public static boolean collisionSphereSphere(CollideSphere[] thingList, int i, int j) {
		if (thingList[i].center.dst(thingList[j].center) <= (thingList[i].radius + thingList[j].radius)) {
			thingList[i].collided = true;
			thingList[j].collided = true;
			return true;
		}
		return false;
	}

	private static int randInt() {
		return (int)(Math.random()*Integer.MAX_VALUE);
	}

	private static float randFloat() {
		return (float)(Math.random()*Float.MAX_VALUE);
	}
	
	public static class CollideSphere {
		
		public final Vector3 center;
		
		public final float radius;
		
		public final int selfMask;
		
		public final int otherMask;
		
		public boolean collided = false;
		
		private CollideSphere(Vector3 center, float radius, int selfMask, int otherMask) {
			this.center = center;
			this.radius = radius;
			this.selfMask = selfMask;
			this.otherMask = otherMask;
		}
		
		public static CollideSphere rand() {
			return new CollideSphere(new Vector3(randFloat(), randFloat(), randFloat()), 
					randFloat(), randInt(), randInt());
		}
		
	}

}
