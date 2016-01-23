package net.suizinshu.external.component;

import net.suizinshu.external.logic.KeyBinder;

import com.artemis.Component;

public class InputBinder extends Component {
	
	public KeyBinder binder;
	
	public InputBinder(KeyBinder binder) {
		this.binder = binder;
	}
	
}