package net.suizinshu.external.component;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.Keybinding;
import net.suizinshu.external.Manager_Keyboard.SingleKeybinding;

import com.artemis.Component;

public class BindableInput extends Component {
	
	public Keybinding[] bindings = new Keybinding[Manager_Keyboard.NUM_KEYS];
	
	public void bind(SingleKeybinding... toBind) {
		Manager_Keyboard.bind(bindings, toBind);
	}
	
	public BindableInput(SingleKeybinding... toBind) {
		bind(toBind);
	}
	
	public BindableInput(SingleKeybinding[]... toBind) {
		for (SingleKeybinding[] bindSet : toBind)
			bind(bindSet);
	}
	
}