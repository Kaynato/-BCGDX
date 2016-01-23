package net.suizinshu.external.component.interfaces;

import com.badlogic.gdx.math.Vector3;

public interface ActPass {
	
	public Vector3 active();
	
	public Vector3 nextActive();
	
	public Vector3 passive();
	
	public static void bumpSet(ActPass actPass) {
		actPass.active().set(actPass.nextActive());
		actPass.nextActive().setZero();
	}

	public static void bumpAdd(ActPass actPass) {
		actPass.active().add(actPass.nextActive());
		actPass.nextActive().setZero();
	}

	public static void push(ActPass from, ActPass to) {
		to.passive().add(from.passive());
		to.nextActive().add(from.active());
	}
	
}
