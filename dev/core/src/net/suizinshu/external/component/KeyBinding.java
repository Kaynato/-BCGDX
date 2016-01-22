package net.suizinshu.external.component;

import net.suizinshu.external.logic.KeyBinder;

import com.artemis.Component;

public class KeyBinding extends Component {
	
	public KeyBinder bindings;
	
	public KeyBinding(KeyBinder bindings) {
		this.bindings = bindings;
	}
	
}