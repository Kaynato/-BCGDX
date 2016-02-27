package net.suizinshu.external.component.collision;

import com.artemis.Component;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.utils.Disposable;

public class CollisionObject extends Component implements Disposable{
	
	public final btCollisionObject object;
	
	public CollisionObject(btCollisionShape shape) {
		object = new btCollisionObject();
		object.setCollisionShape(shape);
		object.setCollisionFlags(object.getCollisionFlags() | 
				btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
		object.activate();
	}
	
	@Override
	public void dispose() {
		object.dispose();
	}
	
}
