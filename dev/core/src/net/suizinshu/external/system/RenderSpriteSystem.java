package net.suizinshu.external.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.suizinshu.external.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class RenderSpriteSystem extends IteratingSystem {
	private static final Vector3 UP = new Vector3(0, 0, 1);
	
	private ComponentMapper<Sprite> sm;
	private ComponentMapper<Position> pm;
	private ComponentMapper<TransformTint> ttm;
	private ComponentMapper<TransformScale> tsm;
	private ComponentMapper<TransformRotate> trm;
	
	private ComponentMapper<IsBackground> isbg;
	
	private Camera camera;
	private SpriteBatch batch;
	private ArrayList<Integer> drawQueueList;
	
	public RenderSpriteSystem(Camera camera) {
		super(Aspect.all(Sprite.class, Position.class));
		this.camera = camera;
	}


	@Override
	protected void initialize() {
		batch = new SpriteBatch();	
		drawQueueList = new ArrayList<Integer>(64);
	}

	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}

	@Override
	protected void process(int entityId) {
		// Add entity to list
		drawQueueList.add(entityId);
	}

	@Override
	protected void end() {
		// Sort list by depth
		Collections.sort(drawQueueList, compareEntityByDepth());
		
		// Draw list
		for (int entityId : drawQueueList)
			performDraw(entityId);
		
		// Clear list
		drawQueueList.clear();
		
		// End batch
		batch.end();
	}
	
	
	
	
	
	private Comparator<Integer> compareEntityByDepth() {
		return new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				float difference = calculateDepth(o1) - calculateDepth(o2);
				if (difference > 0)
					return (int)(difference + 1);
				else if (difference < 0)
					return (int)(difference - 1);
				else
					return 0;
			}
			
		};
	}
	
	private float calculateDepth(int entityId) {
		Position position = pm.getSafe(entityId);
		if (!isbg.has(entityId))
			return position.vec.y + position.vec.z;
		else
			return -1e9f;
	}
	
	private void performDraw(int entityId) {
		Position position = pm.getSafe(entityId);
		Sprite sprite = sm.get(entityId);
		
		float posx = position.vec.x;
		float posy = position.vec.y + position.vec.z;
		
		float scax = 1;
		float scay = 1;
		float rot = 0;

		int srcx = 0;
		int srcy = 0;

		int sizx = sprite.sprite.getWidth();
		int sizy = sprite.sprite.getHeight();
		
		
		if (ttm.has(entityId))
			batch.setColor(ttm.get(entityId).tint);
		else
			batch.setColor(Color.WHITE);
		
		if (tsm.has(entityId)) {
			TransformScale scale = tsm.get(entityId);
			scax *= scale.x;
			scay *= scale.y;
		}
		
		if (trm.has(entityId))
			rot = trm.get(entityId).q.getAngleAround(UP);
		
//		if (debug.has(entityId)) {
//			System.out.println("POS " + posx + " " + posy);
//			System.out.println("SIZ " + sizx + " " + sizy);
//		}
		
		batch.draw(
				sprite.sprite, 
				posx, posy, 
				posx, posy, 
				sizx, sizy, 
				scax, scay, 
				rot, 
				srcx, srcy,
				sizx, sizy,
				false, false);
	}

}
