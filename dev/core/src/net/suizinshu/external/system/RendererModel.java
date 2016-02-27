package net.suizinshu.external.system;

import net.suizinshu.external.component.collision.Cartesian;
import net.suizinshu.external.component.render.DrawModel;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

@Deprecated
public class RendererModel extends IteratingSystem {

	private ComponentMapper<DrawModel> drawMM;
	private ComponentMapper<Cartesian> cartM;
	private Camera camera;
	
	private ModelBatch batch;
	
	public RendererModel(Camera camera) {
		super(Aspect.all(DrawModel.class, Cartesian.class));
		this.camera = camera;
	}

	@Override
	protected void initialize() {
		batch = new ModelBatch();
	}
	
	@Override
	protected void begin() {
		camera.update();
		batch.begin(camera);
	}
	
	@Override
	protected void process(int entityId) {
		ModelInstance dModel = drawMM.getSafe(entityId).model;
		dModel.transform = cartM.getSafe(entityId).transform;
		batch.render(dModel);
	}
	
	@Override
	protected void end() {
		batch.end();
	}

}
