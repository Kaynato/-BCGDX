package net.suizinshu.external.component;

import net.suizinshu.external.Manager_Keyboard.KeyBindSys;

import com.artemis.Component;

public class BindableInput extends Component {
	
	public KeyBindSys bindings;
	
	public BindableInput(KeyBindSys bindings) {
		this.bindings = bindings;
	}
	
}