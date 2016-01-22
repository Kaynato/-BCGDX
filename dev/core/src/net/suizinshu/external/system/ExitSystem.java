package net.suizinshu.external.system;

import net.suizinshu.external.Manager_Keyboard;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;


public class ExitSystem extends BaseSystem {
	
	@Override
	protected void processSystem() {
		if (Manager_Keyboard.k_esc.check() == Manager_Keyboard.KEY_HELD)
			performOnClose();
	}
	
	private void performOnClose() {
		Gdx.app.exit();
	}
	
}
