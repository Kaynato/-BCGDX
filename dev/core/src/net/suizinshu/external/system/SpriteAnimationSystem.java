package net.suizinshu.external.system;

import net.suizinshu.external.component.DrawSubGridAnimator;
import net.suizinshu.external.component.DrawSubGridTexture;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

public class SpriteAnimationSystem extends IteratingSystem {
	ComponentMapper<DrawSubGridTexture> sgm;
	ComponentMapper<DrawSubGridAnimator> ahm;
	
	public enum AnimationType {
		UNIDIR,
		BOUNCE,
		RANDOM
	}
	
	public SpriteAnimationSystem() {
		super(Aspect.all(DrawSubGridTexture.class, DrawSubGridAnimator.class));
	}

	@Override
	protected void process(int entityId) {
		if (ahm.getSafe(entityId).active)
			tick(sgm.getSafe(entityId), ahm.getSafe(entityId), 1);
	}

	

	public void tick(DrawSubGridTexture sug, DrawSubGridAnimator anim, int ticks) {
		anim.time += ticks;
		if (anim.time > anim.frameDuration) {
			anim.time = 0;
			increment(sug, anim);
		}
	}
	
	/**
	 * Increment the index by dir DIRECTLY. Use with caution.
	 */
	public void increment(DrawSubGridTexture sug, DrawSubGridAnimator anim) {
		if (sug._cols <= 1 && sug._rows <= 1)
			return;
		
		if ((anim.lockCol && anim.lockRow) || (!anim.lockCol && !anim.lockRow && sug.maxIndex == 0))
			return;
		
		switch (anim.type) {
		case UNIDIR:
			sug.index += anim.step;
			if (anim.lockCol) {
				if (sug.index >= sug._rows)
					sug.index %= sug._rows;
				if (sug.index < 0)
					sug.index = sug._rows - 1;
			}
			else if (anim.lockRow) {
				if (sug.index >= sug._cols)
					sug.index %= sug._cols;
				if (sug.index < 0)
					sug.index = sug._cols - 1;
			}
			else {
				if (sug.index > sug.maxIndex)
					sug.index %= (sug.maxIndex + 1);
				if (sug.index < 0)
					while (sug.index < 0)
						sug.index += sug.maxIndex + 1;					// TODO
			}
			break;
		case BOUNCE:
			sug.index += anim.step;
			
			if (anim.lockCol) {
				if (sug.index >= sug._rows) {
					sug.index = (sug._rows - 1) - (sug.index % (sug._rows - 1));
					anim.step *= -1;
				}
				if (sug.index < 0) {
					sug.index = -sug.index % sug._rows;
					anim.step *= -1;
				}
			}
			else if (anim.lockRow) {
				if (sug.index >= sug._cols) {
					sug.index = (sug._cols - 1) - (sug.index % (sug._cols - 1));
					anim.step *= -1;
				}
				if (sug.index < 0) {
					sug.index = -sug.index % sug._cols;
					anim.step *= -1;
				}
			}
			else {
				if (sug.index > sug.maxIndex) {
					sug.index = (sug.maxIndex) - (sug.index % sug.maxIndex);
					anim.step *= -1;
				}
				if (sug.index < 0) {
					sug.index = -sug.index % (sug.maxIndex + 1);
					anim.step *= -1;
				}
			}
			break;
		case RANDOM:
			if (anim.lockCol)
				sug.index = (int)(Math.random() * sug._rows);
			else if (anim.lockRow)
				sug.index = (int)(Math.random() * sug._cols);
			else
				sug.index = (int)(Math.random() * sug.maxIndex + 1);
			break;
		}
		
		update(sug, anim);
		
//		System.out.println(sug.index);
	}
	
	public void update(DrawSubGridTexture sug, DrawSubGridAnimator anim) {
		if (anim.lockCol)
			sug.col = sug.index;
		else if (anim.lockRow)
			sug.row = sug.index;
		else {
			sug.row = sug.index % sug._rows;
			sug.col = sug.index / sug._cols;
		}
		sug.xOff = sug.row * sug.width;
		sug.yOff = sug.col * sug.height;
	}
	
}
