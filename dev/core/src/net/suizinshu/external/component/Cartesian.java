package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * Cartesian: Includes TRANSLATION, SCALE, ROTATION<br>
 * This is not used by the systems, so far.<br>
 * Built with Bullet in mind, but...
 * @author Zicheng Gao
 */
public class Cartesian extends Component {

	/**
	 * Transformation matrix for this Cartesian.
	 * Includes translation, rotation, and scaling.
	 */
	public Matrix4 transform;
	
	/**
	 * Returns a CartesianBuilder.
	 * @param x
	 * @param y
	 * @param z
	 * @return cartesianbuilder
	 */
	public static CartesianBuilder coords(float x, float y, float z) {
		return new CartesianBuilder(x, y, z);
	}
	
	public static CartesianBuilder coords(Vector3 vec) {
		return new CartesianBuilder(vec.x, vec.y, vec.z);
	}
	
	private Cartesian(Matrix4 transform) {
		this.transform = transform;
	}
	
	/**
	 * Only call when transform will be set through other means
	 */
	public Cartesian() {
	}
	
	/**
	 * Builder class for Cartesian.
	 * @author Zicheng Gao
	 */
	public static class CartesianBuilder {
		
		private Matrix4 transform;
		
		private CartesianBuilder(float x, float y, float z) {
			transform = new Matrix4();
			transform.setToTranslation(x, y, z);
		}
		
		public CartesianBuilder rotate(float degrees, float axisX, float axisY, float axisZ) {
			transform.rotate(axisX, axisY, axisZ, degrees);
			return this;
		}
		
		public Cartesian build() {
			return new Cartesian(transform);
		}
		
	}
	
	@Override
	public String toString() {
		Vector3 position = new Vector3();
		transform.getTranslation(position);
		return (position.toString());
	}
	
}
