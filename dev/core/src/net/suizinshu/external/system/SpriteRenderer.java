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

public class SpriteRenderer extends IteratingSystem {
	private ComponentMapper<DrawTexture> tm;
	
	private ComponentMapper<DrawSubGridTexture> tsgm;
	
	private ComponentMapper<TransformTint> ttm;
	private ComponentMapper<TransformScale> tsm;
	
	private ComponentMapper<Angle> angm;
	private ComponentMapper<Position> pm;
	
	private ComponentMapper<IsCentered> isctm;
	
	private ComponentMapper<ForcedDepth> depthm;
	
	private Camera camera;
	private SpriteBatch batch;
	private ArrayList<Integer> drawQueueList;
	
	public SpriteRenderer(Camera camera) {
		super(Aspect.all(DrawTexture.class, Position.class));
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
		Collections.sort(drawQueueList, compareEntityByDepth);
		
		// Draw list
		for (int entityId : drawQueueList)
			drawTexture(entityId);
		
		// Clear list
		drawQueueList.clear();
		
		// End batch
		batch.end();
	}

	private Comparator<Integer> compareEntityByDepth = (o1, o2) -> {
		float difference = calculateDepth(o1) - calculateDepth(o2);
		if (difference > 0) 		return (int)(difference + 1);
		else if (difference < 0) 	return (int)(difference - 1);
		else 						return 0;
	};
	
	private float calculateDepth(int entityId) {
		if (depthm.has(entityId))
			return depthm.getSafe(entityId).depth;
		Position position = pm.getSafe(entityId);
		return position.vec.y + position.vec.z;
	}
	
	private void drawTexture(int entityId) {
		Position position = pm.getSafe(entityId);
		DrawTexture drawTexture = tm.get(entityId);
		
		/* INITIALIZE VARIABLES */
		float posx 	= position.vec.x;
		float posy 	= position.vec.y + position.vec.z;			// TODO haha, no, no, no, use angle
		
		float scax 	= 1;										// Scale
		float scay 	= 1;
		
		float rot 	= 0;

		float orix 	= 0;
		float oriy 	= 0;
		
		int srcx 	= 0;
		int srcy 	= 0;

		int sizx 	= drawTexture.texture.getWidth();
		int sizy 	= drawTexture.texture.getHeight();
		
		int srcw 	= sizx;
		int srch 	= sizy;
		
		/* PROCESSING FOR ANIMATION COMPONENTS */
		
		if (tsgm.has(entityId)) {
			DrawSubGridTexture subgrid = tsgm.getSafe(entityId);
			srcx = subgrid.xOff;
			srcy = subgrid.yOff;
			sizx = subgrid.width;
			sizy = subgrid.height;
			srcw = sizx;
			srch = sizy;
		}
		
		/* POST - PROCESSING */
		
		/* Tint */
		if (ttm.has(entityId))
			batch.setColor(ttm.get(entityId).tint);
		else
			batch.setColor(Color.WHITE);
		
		/* Scale */
		if (tsm.has(entityId)) {
			TransformScale scale = tsm.get(entityId);
			scax *= scale.x;
			scay *= scale.y;
		}
		
		/* Rotation */
		if (angm.has(entityId))
			rot = -angm.get(entityId).deg();
		
		/* Center */
		if (isctm.has(entityId)) {
			posx -= sizx / 2;
			posy -= sizy / 2;
			orix += sizx / 2;
			oriy += sizy / 2;
		}
			
		/* DRAW */
		
		batch.draw(
				drawTexture.texture, 
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
