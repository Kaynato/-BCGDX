package net.suizinshu.external;


public class ExclusionError extends Error {
	private static final long	serialVersionUID	= -4167117138960847272L;
	
	public ExclusionError() {
		super("I mean, really. Really. Come on. You made a mess of things and you know it.\n"
				+ "You put this in because you wanted to prevent yourself from doing something silly, ok?\n"
				+ "But, somehow, either because you were too lazy to fix the issue...\n"
				+ "Or just circumvented your own code...\n"
				+ "You've thrown this.\n"
				+ "Honestly, this should not be necessary. That this error exists...\n"
				+ "Well, it's not the best.\n"
				+ "Try harder to fix things, okay?");
	}
	
}
