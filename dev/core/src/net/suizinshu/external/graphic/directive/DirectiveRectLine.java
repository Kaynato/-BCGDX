package net.suizinshu.external.graphic.directive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class DirectiveRectLine extends Directive {

	public Vector2 a, b;
	public float width;
	
	protected DirectiveRectLine(String name, ShapeType type, Color color, Vector2 a, Vector2 b,
			float width) {
		super(name, type, color);
		this.a = a;
		this.b = b;
		this.width = width;
	}

	@Override
	protected void draw(ShapeRenderer render) {
		render.rectLine(a.x, a.y, b.x, b.y, width);
	}

}
