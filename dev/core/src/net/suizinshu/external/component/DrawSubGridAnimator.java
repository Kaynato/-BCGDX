package net.suizinshu.external.component;

import net.suizinshu.external.system.SpriteAnimationSystem.AnimationType;

import com.artemis.Component;

public class DrawSubGridAnimator extends Component {
	
	public AnimationType type = AnimationType.UNIDIR;
	
	public boolean active = true;
	
	/** To add to the index. */
	public int step = 1;
	
	/** Time counter. */
	public int time = 0;
	
	/** Time trigger. */
	public int frameDuration;
	
	/** Whether the row is locked. Overrides maxIndex - use when intended only. */
	public boolean lockRow = false,
				   lockCol = false;
	
	public DrawSubGridAnimator(AnimationType type, int frameDuration) {
		this.type = type;
		this.frameDuration = frameDuration;
	}
	
	public DrawSubGridAnimator(AnimationType type, int frameDuration, boolean lockCol, boolean lockRow) {
		this(type, frameDuration);
		this.lockRow = lockRow;
		this.lockCol = lockCol;
	}
	
	public DrawSubGridAnimator(AnimationType type, int frameDuration, boolean lockCol, boolean lockRow, int step) {
		this(type, frameDuration, lockRow, lockCol);
		this.step = step;
	}
	
}
