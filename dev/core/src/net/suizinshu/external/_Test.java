package net.suizinshu.external;

import java.util.HashSet;
import java.util.Set;

public class _Test {
	
	public static void main(String[] args) {
		
		setTraverseTest();
		
		/*
		 * TreeSet
		 * 0.002149636507000002 average seconds elapsed over 1000 times.
		 * -0.014517029492999989 average seconds above 60fps over 1000 times.
		 * [DOES NOT SUPPORT OVERLAPS]
		 *
		 * LinkedList add and sort each time
		 * 0.001837009412999999 average seconds elapsed over 1000 times.
		 * -0.014829656586999997 average seconds above 60fps over 1000 times.
		 * 
		 * 0.0016282878932999936 average seconds elapsed over 10000 times.
		 * -0.015038378106700048 average seconds above 60fps over 10000 times.
		 *
		 *
		 * ArrayList add and sort each time
		 * 0.0017197394219999995 average seconds elapsed over 1000 times.
		 * -0.014946926577999993 average seconds above 60fps over 1000 times.
		 * 
		 * 0.001471320833300007 average seconds elapsed over 10000 times.
		 * -0.015195345166699946 average seconds above 60fps over 10000 times.
		 * 
		 * Add 100 times
		 * -0.013934054842000016 average seconds above 60fps over 1000 times.
		 * 
		 * Add 1000 times
		 * -0.004836171255999997 average seconds above 60fps over 1000 times.
		 * 
		 * TreeMap
		 * 
		 * 
		 * 
		 * Bag
		 * 
		 * 3.1259577000000076E-4 average seconds elapsed over 1000 times.
		 * -0.016354070229999913 average seconds above 60fps over 1000 times.
		 * 
		 * 
		 * 
		 * TreeSet
		 * 0.0018944291340000023 average seconds elapsed over 1000 times.
		 * -0.014772236866000021 average seconds above 60fps over 1000 times.
		 * 
		 * HashSet
		 * 5.293309799999994E-4 average seconds elapsed over 1000 times.
		 * -0.01613733502000001 average seconds above 60fps over 1000 times.
		 */
		
	}
//
//	private static double x;
	
	private static void setTraverseTest() {
		
		Set<Float> set = new HashSet<Float>();
		
		int count = 1000;
		
		double averageTaken = 0;
		double averageOvershot = 0;
		
		int cyc = 0;
		int cyclim = 20;
		
//		x = 0;
		
		for (int ind = 0; ind < count; ind++) {
			set.clear();
			
			long targ60nano = 1_000_000_000/60;
			long startTime = System.nanoTime();
			
			for (int i = 0; i < 10000; i++) {
//				SortableEntity e = new SortableEntity();
//				set.put(e.depth, e);
				set.add((float)Math.random());
			}
			
//			Collections.sort(set);
			
//			set.forEach((f, e) -> {
//				for (int j = 0; j < 1000; j++)
//					x += (double)(f * 0.001);
//			});
			
			long diffNano = System.nanoTime() - startTime;
			Double timeDiff = diffNano / 1e9;
			//		System.out.println(timeDiff.toString() + " seconds elapsed.");
			Double overshot = (diffNano - targ60nano) / 1e9;
			//		System.out.println(overshot.toString() + " seconds over 60fps.");
			//		System.out.println(x);
			averageTaken += timeDiff;
			averageOvershot += overshot;
			
			if (cyc < cyclim)
				cyc++;
			else {
				cyc = 1;
				System.out.println(ind);
			}
			
		}
		
		averageTaken /= count;
		averageOvershot /= count;
		
		System.out.println(averageTaken + " average seconds elapsed over " + count + " times.");
		System.out.println(averageOvershot + " average seconds above 60fps over " + count + " times.");
		
//		System.out.println(set);
//		System.out.println(x);
	}
	
	protected static void timeTest() {
		long targ60nano = 1_000_000_000/60;
		long targ30nano = 1_000_000_000/30;
		
		System.out.println(targ60nano);
		System.out.println(targ30nano);
		
		long startTime = System.nanoTime();
		long diffNano = System.nanoTime() - startTime;
		Double timeDiff = (diffNano / 1e9);
		
		System.out.println(timeDiff.toString() + " seconds elapsed.");
		
		long startM = System.currentTimeMillis();
		long diffM = System.currentTimeMillis() - startM;
		
		System.out.println(diffM);
	}
	
	private static double randRange(int range) {
		double x = Math.random();
		x = (int)((x * range) + 1);
		return x;
	}
	
	public static class SortableEntity implements Comparable<SortableEntity>{

		public float depth;
		
		public SortableEntity() {
			depth = (float)randRange(20);
		}
		
		@Override
		public int compareTo(SortableEntity o) {
			return (int)(this.depth - o.depth);
		}
		
		@Override
		public String toString() {
			return "E" + depth;
		}
		
	}
	
}
