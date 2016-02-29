package net.suizinshu.external.graphic.directive;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class Directive implements Comparable<Directive> {

	private int order;
	private Color color;
	private String name;
	private ShapeType type;

	public Directive(String name, ShapeType type, Color color) {
		setOrder(0);
		setName(name);
		this.type = type;
		this.color = color;
	}
	
	public int getOrder() { return order; }
	public String getName() { return name; }
	public Color getColor() { return color; }
	public ShapeType getType() { return type; }
	
	public void setName(String name) { this.name = name; }
	public void setOrder(int order) { this.order = order; }
	public void setColor(Color color) { this.color = color;	}
	public void setType(ShapeType type) { this.type = type; }
	
	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(getColor());
		draw(shapeRenderer);
	}
	
	protected abstract void draw(ShapeRenderer render);
	
	@Override
	public int compareTo(Directive o) {
		return getOrder() - o.getOrder();
	}
	
	public static class DirectiveBuilder {
		
		private ShapeType type;
		private Color color;
		public DirectiveBuilder type(ShapeType type) { this.type = type; return this; }
		public DirectiveBuilder color(Color color) { this.color = color; return this; }
		
		private DirectiveBuilder(ShapeType type, Color color) {
			this.type = type;
			this.color = color;
		}
		
		public static DirectiveBuilder begin(ShapeType type, Color color) {
			return new DirectiveBuilder(type, color);
		}
		
		/** Point */
		public DirectivePoint point(String name, Vector3 a) {
			return new DirectivePoint(name, ShapeType.Point, color, a);
		}
		
		/** Line tapering to different color */
		public DirectiveLine line(String name, Vector3 a, Vector3 b, Color colorB) {
			return new DirectiveLine(name, type, color, a, b, colorB);
		}
		
		/** Uniform color line */
		public DirectiveLine line(String name, Vector3 a, Vector3 b) {
			return new DirectiveLine(name, type, color, a, b, null);
		}
		
		/**
		 * Curve
		 * @param a	start
		 * @param ac spline start
		 * @param bc spline end
		 * @param b	end
		 */
		public DirectiveCurve curve(String name, Vector2 a, Vector2 ac, Vector2 bc, Vector2 b, int segments) {
			return new DirectiveCurve(name, type, color, a, ac, bc, b, segments);
		}
		
		/** Triangle with colored vertices */
		public DirectiveTriangle triangle(String name, Vector2 a, Vector2 b, Vector2 c, 
				Color colorA, Color colorB, Color colorC) {
			return new DirectiveTriangle(name, type, a, b, c, colorA, colorB, colorC);
		}
		
		/** Triangle uniformly colored */
		public DirectiveTriangle triangle(String name, Vector2 a, Vector2 b, Vector2 c) {
			return new DirectiveTriangle(name, type, a, b, c, color, null, null);
		}
		
		/** Scaled rectangle with colored vertices */
		public DirectiveRectangle rectangle(String name, Vector2 x,
				Vector2 pivot, Vector2 depth, Vector2 scale, int degrees,
				Color colorA, Color colorB, Color colorC, Color colorD){
			return new DirectiveRectangle(name, type, colorA, x, pivot, depth, 
					scale, degrees, colorB, colorC, colorD);
		}
		
		/** Uniformly colored scaled rectangle */
		public DirectiveRectangle rectangle(String name, Vector2 x,
				Vector2 pivot, Vector2 depth, Vector2 scale, int degrees){
			return new DirectiveRectangle(name, type, color, x, pivot, depth, 
					scale, degrees, null, null, null);
		}
		
		/** Uniformly colored rectangle */
		public DirectiveRectangle rectangle(String name, Vector2 x,
				Vector2 pivot, Vector2 depth, int degrees){
			return new DirectiveRectangle(name, type, color, x, pivot, depth, 
					null, degrees, null, null, null);
		}
		
		/** Circle segmented */
		public DirectiveCircle circle(String name, Vector2 x, float radius, int segments) {
			return new DirectiveCircle(name, type, color, x, radius, segments);
		}
		
		/** Circle autosegment */
		public DirectiveCircle circle(String name, Vector2 x, float radius) {
			return new DirectiveCircle(name, type, color, x, radius, 0);
		}
		
	}
	
}
