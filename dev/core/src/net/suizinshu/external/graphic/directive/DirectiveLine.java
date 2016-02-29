package net.suizinshu.external.graphic.directive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public final class DirectiveLine extends Directive {

	public Vector3 a, b;
	
	public Color colorB;
	
	protected DirectiveLine(String name, ShapeType type, Color colorA, Vector3 a, Vector3 b, Color colorB) {
		super(name, type, colorA);
		this.a = a;
		this.b = b;
		this.colorB = colorB;
	}

	@Override
	protected void draw(ShapeRenderer shapeRenderer) {
		if (colorB != null)
			shapeRenderer.line(a.x, a.y, a.z, b.x, b.y, b.z, getColor(), colorB);
		else
			shapeRenderer.line(a.x, a.y, a.z, b.x, b.y, b.z);
	}

}
