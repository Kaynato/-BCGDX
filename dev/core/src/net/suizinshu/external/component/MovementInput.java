package net.suizinshu.external.component;

import net.suizinshu.external.logic.KeyBinder;

import com.artemis.Component;

public class MovementInput extends Component {
	
	public KeyBinder binder;
	
	public MovementInput(KeyBinder binder) {
		this.binder = binder;
	}
	
}