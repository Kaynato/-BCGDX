package net.suizinshu.external.graphic.directive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class DirectivePoint extends Directive {

	public Vector3 a;
	
	protected DirectivePoint(String name, ShapeType type, Color color, Vector3 a) {
		super(name, type, color);
		this.a = a;
	}

	@Override
	protected void draw(ShapeRenderer shapeRenderer) {
		shapeRenderer.point(a.x, a.y, a.z);
	}

}
