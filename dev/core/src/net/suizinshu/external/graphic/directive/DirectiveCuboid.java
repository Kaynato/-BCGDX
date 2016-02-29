package net.suizinshu.external.graphic.directive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class DirectiveCuboid extends Directive {

	public Vector3 x, depth;

	protected DirectiveCuboid(String name, ShapeType type, Color color, Vector3 x, Vector3 depth) {
		super(name, type, color);
		this.x = x;
		this.depth = depth;
	}

	@Override
	protected void draw(ShapeRenderer render) {
		render.box(x.x, x.y, x.z, depth.x, depth.y, depth.z);
	}

}
