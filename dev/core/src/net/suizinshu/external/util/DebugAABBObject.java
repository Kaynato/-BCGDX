package net.suizinshu.external.util;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public final class DebugAABBObject {

	public Matrix4 t;
	public Vector3 pos;
	public Vector3 dep;
	
	public DebugAABBObject(Matrix4 t, Vector3 pos, Vector3 dep) {
		super();
		this.t = t;
		this.pos = pos;
		this.dep = dep;
	}
	
}
