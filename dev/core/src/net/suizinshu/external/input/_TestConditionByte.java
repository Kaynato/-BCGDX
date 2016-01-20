package net.suizinshu.external.input;


public class _TestConditionByte {
	
	public static void main(String[] args) {
		
		byte[] b = new byte[64];
		TestNum[] n = new TestNum[64];
		
		for (byte i = 0; i < 64; i++) {
			b[i] = i;
			n[i] = new TestNum(i);
		}
		
		long time = System.nanoTime();
		
		ConditionByte c = Condition.and(b[0], n[0]);
		m(c.check(), "Single AND:");
		
		c.and(b[1], n[1], n[1], n[1]);
		m(c.check(), "Two AND multi internal:");
		
		c.and(b[1], n[2], n[1], n[1]);
		m(!c.check(), "Unwhole AND:");
		
		c.and(b[1], n[1]);
		m(!c.check(), "Non-matching AND:");
		
		c.clear();
		m(c.check(), "Nothing:");
		
		c.or(b[1], n[1], n[2], n[3]);
		m(!c.check(), "Unwhole or:");
		
		c.or(b[1], n[1], n[1]);
		m(c.check(), "Whole or overtakes:");

		c.and(b[1], n[2]);
		m(!c.check(), "Unwhole and undertakes:");
		
		c.clear();
		m(c.check(), "Nothing:");
		
		c.not(b[10], n[9], n[8]);
		m(c.check(), "Exclusion:");

		c.not(b[10], n[10], n[10]);
		m(!c.check(), "Unwhole exclusion:");
		
		c.clear();
		m(c.check(), "Nothing:");
		
		c.any(b[10], n[1], n[2], n[10]);
		m(c.check(), "AND Any:");

		c.any(b[10], n[1], n[10], n[10]);
		m(c.check(), "AND Any 2:");

		c.any(b[10], n[1], n[2], n[2]);
		m(!c.check(), "Bad Any:");
		
		long diff = System.nanoTime() - time;
		System.out.println(diff / 1e9);
		
	}
	
	
	
	public static class TestNum implements CheckableByte {
		private byte self;
		public TestNum(byte self) { this.self = self; }
		public byte check() { return self; }
	}
	
	public static void m(boolean condition, String testname) {
		c(condition, testname + " Good.", testname + " Bad.");
	}
	
	public static void c(boolean condition, String yes, String no) {
		System.out.println((condition) ? (yes) : (no));
	}
	
}