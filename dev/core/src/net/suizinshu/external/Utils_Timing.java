package net.suizinshu.external;

public class Utils_Timing {

	public static final long BILLION = 1_000_000_000;
	
	public static long nanoTarget(int fps) {
		return (BILLION / fps);
	}

}
