package net.suizinshu.external.system;

import net.suizinshu.external.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;


public class RenderPlaneSystem extends IteratingSystem {
	private ComponentMapper<Position> pm;
	private ComponentMapper<SpriteDecal> sm;
	private ComponentMapper<TransformRotate> trm;
	private ComponentMapper<TransformScale> tsm;
	private ComponentMapper<TransformTint> ttm;
	private ComponentMapper<DoesAlwaysFaceCamera> dafcm;
	
	private Camera camera;
	
	private DecalBatch batch;
	
	
	public RenderPlaneSystem(Camera camera) {
		super(Aspect.all(Position.class, SpriteDecal.class));
		this.camera = camera;
	}
	
	@Override
	protected void begin() {
		batch = new DecalBatch(new CameraGroupStrategy(camera));
		camera.update();
	}
	
	@Override
	protected void process(int entityId) {
		Position pos = pm.get(entityId);
		SpriteDecal spr = sm.get(entityId);
		
		spr.decal.setPosition(pos.vec);
		
		
		if (!spr.scaled) {
			float wid = ((2 * spr.decal.getWidth()) / camera.viewportWidth);
			float hei = ((2 * spr.decal.getHeight()) / camera.viewportWidth);
			
			System.out.println("<" + wid + ", " + hei + ">");
			
			Vector3 dimsca = new Vector3(wid, hei, 0);
			
			spr.decal.setDimensions(dimsca.x, dimsca.y);
			spr.scaled = true;
		}
		
		
		if (ttm.has(entityId))
			spr.decal.setColor(ttm.get(entityId).tint);
		
		if (trm.has(entityId))
			spr.decal.setRotation(trm.get(entityId).q);
		
		if (tsm.has(entityId)) {
			TransformScale scale = tsm.get(entityId);
			spr.decal.setScale(scale.x, scale.y);
		}
		
		if (dafcm.has(entityId))
			spr.decal.lookAt(camera.position, camera.up);
		
		
		
		batch.add(spr.decal);
	}
	
	@Override
	protected void end() {
		camera.update();
		batch.flush();
	}
	
	@Override
	protected void dispose() {
		batch.dispose();
		super.dispose();
	}
	
}