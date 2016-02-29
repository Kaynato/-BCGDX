package net.suizinshu.external.system;

import java.util.ArrayList;
import java.util.Collections;

import net.suizinshu.external.component.newtonian.Angle;
import net.suizinshu.external.component.newtonian.Position;
import net.suizinshu.external.component.render.DrawDirective;
import net.suizinshu.external.component.render.ForcedDepth;
import net.suizinshu.external.component.render.TransformScale;
import net.suizinshu.external.graphic.directive.Directive;
import net.suizinshu.external.util.DebugAABBObject;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class RendererDirective extends IteratingSystem {

	private Camera camera;
	
	private ArrayList<Integer> drawQueue;
	
	private ComponentMapper<DrawDirective> ddm;
	private ComponentMapper<TransformScale> tsm;
	private ComponentMapper<Angle> angm;
	private ComponentMapper<Position> pm;
	private ComponentMapper<ForcedDepth> fordepm;
	
	private ShapeRenderer renderer;
	
	public RendererDirective(Camera camera) {
		super(Aspect.all(DrawDirective.class, Position.class));
		this.camera = camera;
		renderer = new ShapeRenderer();
	}

	@Override
	protected void begin() {
		camera.update();
		renderer.setProjectionMatrix(camera.combined);
		drawQueue = new ArrayList<Integer>(64);
	}
	
	@Override
	protected void process(int entityId) {
		drawQueue.add(entityId);
	}
	
	
	@Override
	protected void end() {
		Collections.sort(drawQueue, 
				(a, b) -> (int) (fordepm.getSafe(b).depth - fordepm.getSafe(a).depth));
		
		renderer.setAutoShapeType(true);
		renderer.begin();
		
		for (Integer entityId : drawQueue)
			drawDirective(entityId);
		
		drawDebugQueue();
		
		renderer.end();
		
		
	}

	private void drawDirective(Integer entityId) {
		DrawDirective ddr = ddm.getSafe(entityId);
		
		Vector3 pos = pm.getSafe(entityId).vec;
		Angle ang = angm.getSafe(entityId);
		TransformScale ts = tsm.getSafe(entityId);
		
		renderer.identity();
		renderer.translate(pos.x, pos.y, pos.z);
		if (ang != null)
			renderer.rotate(ang.axis.x, ang.axis.y, ang.axis.z, ang.deg());
		if (ts != null)
			renderer.scale(ts.x, ts.y, 1);
		
		for (Directive d : ddr.directives) {
			renderer.set(d.getType());
			d.render(renderer);
		}
		
	}
	
	public static ArrayList<DebugAABBObject> debugDrawQueue = new ArrayList<DebugAABBObject>();
	
	private void drawDebugQueue() {
		for (DebugAABBObject abo : debugDrawQueue) {
			renderer.set(ShapeType.Filled);
			renderer.setTransformMatrix(abo.t);
			renderer.setColor(Color.RED);
			renderer.box(abo.pos.x, abo.pos.y, abo.pos.z, abo.dep.x, abo.dep.y, abo.dep.z);
			renderer.end();
		}
		debugDrawQueue.clear();
	}
		

}
