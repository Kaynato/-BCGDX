package net.suizinshu.external;

public class _Test {
	
	public static void main(String[] args) {
		timeTest();
	}

	private static void timeTest() {
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

}
