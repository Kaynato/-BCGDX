package net.suizinshu.external.system;

import net.suizinshu.external.component.CollisionDetection;
import net.suizinshu.external.component.HitShape;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;


public class CollideSystem extends IteratingSystem {
	
	/* Absolutely, most certainly, safe. */
	@SuppressWarnings("unchecked")
	public CollideSystem() {
		super(Aspect.all(CollisionDetection.class).one(HitShape.class));
	}

	@Override
	protected void process(int entityId) {
		// TODO Auto-generated method stub
		
	}
	
}
