package net.suizinshu.external.graphic.directive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class DirectiveTriangle extends Directive {

	public Vector2 a, b, c;
	public Color colorB, colorC;
	
	protected DirectiveTriangle(String name, ShapeType type, Vector2 a, Vector2 b, Vector2 c, Color colorA, Color colorB, Color colorC) {
		super(name, type, colorA);
		this.a = a;
		this.b = b;
		this.c = c;
		this.colorB = colorB;
		this.colorC = colorC;
	}

	@Override
	protected void draw(ShapeRenderer render) {
		if (colorB != null && colorC != null)
			render.triangle(a.x, a.y, b.x, b.y, c.x, c.y, getColor(), colorB, colorC);
		else
			render.triangle(a.x, a.y, b.x, b.y, c.x, c.y);
	}

}
