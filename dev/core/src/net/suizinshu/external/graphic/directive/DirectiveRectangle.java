package net.suizinshu.external.graphic.directive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class DirectiveRectangle extends Directive {

	public int degrees;
	public Vector2 x, pivot, depth, scale;
	public Color colorB, colorC, colorD;
	
	protected DirectiveRectangle(String name, ShapeType type, Color colorA, Vector2 x,
			Vector2 pivot, Vector2 depth, Vector2 scale, int degrees,
			Color colorB, Color colorC, Color colorD) {
		super(name, type, colorA);
		this.x = x;
		this.pivot = pivot;
		this.depth = depth;
		this.scale = (scale != null) ? (scale) : (new Vector2(1, 1));
		this.degrees = degrees;
		this.colorB = colorB;
		this.colorC = colorC;
		this.colorD = colorD;
	}

	@Override
	protected void draw(ShapeRenderer render) {
		if (colorB != null && colorC != null && colorD != null)
			render.rect(x.x, x.y, pivot.x, pivot.y, depth.x, depth.y, scale.x, scale.y, degrees, getColor(), colorB, colorC, colorD);
		else
			render.rect(x.x, x.y, pivot.x, pivot.y, depth.x, depth.y, scale.x, scale.y, degrees);
	}

}
