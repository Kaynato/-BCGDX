package net.suizinshu.external.graphic.directive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class DirectiveCircle extends Directive {

	public Vector2 x;
	public float radius;
	public int segments;

	protected DirectiveCircle(String name, ShapeType type, Color color, Vector2 x,
			float radius, int segments) {
		super(name, type, color);
		this.x = x;
		this.radius = radius;
		this.segments = segments;
	}

	@Override
	protected void draw(ShapeRenderer render) {
		if (segments > 0)
			render.circle(x.x, x.y, radius, segments);
		else
			render.circle(x.x, x.y, radius);
	}

}
