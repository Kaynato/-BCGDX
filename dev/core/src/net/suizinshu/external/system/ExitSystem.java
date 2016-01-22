package net.suizinshu.external.system;

import net.suizinshu.external.Manager_Keyboard;
import net.suizinshu.external.Manager_Keyboard.KeyConst;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;


public class ExitSystem extends BaseSystem {
	
	@Override
	protected void processSystem() {
		if (Manager_Keyboard.state(KeyConst.ESC) == KeyConst.KEY_HELD)
			performOnClose();
	}
	
	private void performOnClose() {
		Gdx.app.exit();
	}
	
}
