package net.suizinshu.external.system;

import net.suizinshu.external.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class RenderSpriteSystem extends IteratingSystem {
	private static final Vector3 UP = new Vector3(0, 0, 1);
	
	private ComponentMapper<Sprite> sm;
	private ComponentMapper<Position> pm;
	private ComponentMapper<TransformRotate> trm;
	private ComponentMapper<TransformScale> tsm;
	private ComponentMapper<TransformTint> ttm;
	
	private SpriteBatch batch;

	public RenderSpriteSystem() {
		super(Aspect.all(Sprite.class, Position.class));
	}


	@Override
	protected void initialize() {
		batch = new SpriteBatch();	
	}

	@Override
	protected void begin() {
		batch.begin();
	}

	@Override
	protected void process(int entityId) {
		Position position = pm.getSafe(entityId);
		Sprite sprite = sm.get(entityId);
		
		if (ttm.has(entityId))
			batch.setColor(ttm.get(entityId).tint);
		else
			batch.setColor(Color.WHITE);
		
		float posx = position.vec.x;
		float posy = position.vec.y;
		
		int sizx = sprite.sprite.getWidth();
		int sizy = sprite.sprite.getHeight();
		
		float scax = 1;
		float scay = 1;
		
		if (tsm.has(entityId)) {
			TransformScale scale = tsm.get(entityId);
			scax *= scale.x;
			scay *= scale.y;
		}
		
		float rot = 0;
		
		if (trm.has(entityId))
			rot = trm.get(entityId).q.getAngleAround(UP);

		batch.draw(sprite.sprite, posx, posy, posx, posy, sizx, sizy, scax, scay, 
				rot, 0, 0, sizx, sizy, false, false);
		
//		System.out.println("Entity id " + entityId + " at <" + posx + ", " + posy + ">");
	}

	@Override
	protected void end() {
		batch.end();
	}

}
