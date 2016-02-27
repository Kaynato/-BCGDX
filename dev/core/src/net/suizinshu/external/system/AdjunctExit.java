package net.suizinshu.external.system;

import net.suizinshu.external.StateKeyboard;
import net.suizinshu.external.StateKeyboard.KeyByte;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;


public class AdjunctExit extends BaseSystem {
	
	@Override
	protected void processSystem() {
		if (StateKeyboard.query(KeyByte.ESC, KeyByte.KEY_HELD))
			performOnClose();
	}
	
	private void performOnClose() {
		Gdx.app.exit();
	}
	
}
