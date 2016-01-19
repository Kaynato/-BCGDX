package net.suizinshu.external.component;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.Keybinding;

import com.artemis.Component;

public class BindableInput extends Component {
	
	public Keybinding[] bindings = new Keybinding[Manager_Keyboard.NUM_KEYS];
	
	public void bind(Keybinding... toBind) {
		Manager_Keyboard.bind(bindings, toBind);
	}
	
	public BindableInput(Keybinding... toBind) {
		bind(toBind);
	}
	
	public BindableInput(Keybinding[]... toBind) {
		for (Keybinding[] bindSet : toBind)
			bind(bindSet);
	}
	
}