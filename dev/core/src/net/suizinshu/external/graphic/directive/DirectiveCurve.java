package net.suizinshu.external.graphic.directive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class DirectiveCurve extends Directive {

	public Vector2 a, ac, bc, b;
	
	public int segments;
	
	protected DirectiveCurve(String name, ShapeType type, Color color, 
			Vector2 a, Vector2 ac, Vector2 bc, Vector2 b, int segments) {
		super(name, type, color);
		this.a = a;
		this.ac = ac;
		this.bc = bc;
		this.b = b;
		this.segments = segments;
	}

	@Override
	protected void draw(ShapeRenderer shapeRenderer) {
		shapeRenderer.curve(a.x, a.y, ac.x, ac.y, bc.x, bc.y, b.x, b.y, segments);
	}

}
