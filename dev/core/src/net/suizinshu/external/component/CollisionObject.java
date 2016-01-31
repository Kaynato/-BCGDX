package net.suizinshu.external.component;

import com.artemis.Component;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.utils.Disposable;

public class CollisionObject extends Component implements Disposable{
	
	public btCollisionObject object;
	
	public CollisionObject(btCollisionShape shape) {
		object = new btCollisionObject();
		object.setCollisionShape(shape);
	}
	
	@Override
	public void dispose() {
		object.dispose();
	}
	
}
