package net.suizinshu.external.component;

import net.suizinshu.external.Manager_Keyboard.KeyBindSys;

import com.artemis.Component;

public class BindableInput extends Component {
	
	public KeyBinder bindings;
	
	public BindableInput(KeyBinder bindings) {
		this.bindings = bindings;
	}
	
}