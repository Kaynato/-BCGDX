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

public class RenderSpriteSystem extends IteratingSystem {
	private ComponentMapper<Sprite> sm;
	private ComponentMapper<Position> pm;
	private ComponentMapper<TransformTint> ttm;
	private ComponentMapper<TransformScale> tsm;
	
	private ComponentMapper<Angle> angm;
	
	private ComponentMapper<IsBackground> isbgm;
	private ComponentMapper<IsCentered> isctm;
	
	private ComponentMapper<Debug> debug;
	
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
		if (!isbgm.has(entityId))
			return position.vec.y + position.vec.z;
		else
			return -1e9f;
	}
	
	private void performDraw(int entityId) {
		Position position = pm.getSafe(entityId);
		Sprite sprite = sm.get(entityId);
		
		
		/* INITIALIZE VARIABLES */
		float posx = position.vec.x;
		float posy = position.vec.y + position.vec.z;
		
		float scax = 1;
		float scay = 1;
		float rot = 0;

		float orix = 0;
		float oriy = 0;
		
		int srcx = 0;
		int srcy = 0;

		int sizx = sprite.sprite.getWidth();
		int sizy = sprite.sprite.getHeight();
		
		int srcw = sizx;
		int srch = sizy;
		
		/* APPLY MODIFICATIONS */
		
		if (ttm.has(entityId))
			batch.setColor(ttm.get(entityId).tint);
		else
			batch.setColor(Color.WHITE);
		
		if (tsm.has(entityId)) {
			TransformScale scale = tsm.get(entityId);
			scax *= scale.x;
			scay *= scale.y;
		}
		
		if (angm.has(entityId))
			rot = angm.get(entityId).deg();
		
		if (isctm.has(entityId)) {
			posx -= sizx / 2;
			posy -= sizy / 2;
			orix += sizx / 2;
			oriy += sizy / 2;
		}
			
			
		
		if (debug.has(entityId)) {
//			System.out.println("POS " + posx + " " + posy);
//			System.out.println("SIZ " + sizx + " " + sizy);
//			if (angm.has(entityId))
//				System.out.println(angm.getSafe(entityId).q);
//			System.out.println(rot);
		}
		
		batch.draw(
				sprite.sprite, 
				posx, posy, 
				orix, oriy, 
				sizx, sizy, 
				scax, scay, 
				rot, 
				srcx, srcy,
				srcw, srch,
				false, false);
	}

}
