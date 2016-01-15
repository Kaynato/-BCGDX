package net.suizinshu.external.system;

import net.suizinshu.external.component.Position;
import net.suizinshu.external.component.Sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteRenderSystem extends IteratingSystem {
	private ComponentMapper<Sprite> sm;
	private ComponentMapper<Position> pm;

	private SpriteBatch batch;
	private Camera camera;

	public SpriteRenderSystem(Camera camera) {
		super(Aspect.all(Sprite.class, Position.class));
		this.camera = camera;
	}


	@Override
	protected void initialize() {
		batch = new SpriteBatch();	
	}

	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}

	@Override
	protected void process(int entityId) {
		Position position = pm.getSafe(entityId);
		Sprite sprite = sm.get(entityId);

		batch.setColor(sprite.r, sprite.g, sprite.b, sprite.a);
		float posx = position.vec.x;
		float posy = position.vec.y;

		batch.draw(sprite.sprite, posx, posy);
		
//		System.out.println("Entity id " + entityId + " at <" + posx + ", " + posy + ">");
	}

	@Override
	protected void end() {
		batch.end();
	}

}
