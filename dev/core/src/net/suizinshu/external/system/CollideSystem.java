package net.suizinshu.external.system;

import net.suizinshu.external.component.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;

/**
 * Collision handling system! Does the colliding thing.
 * @author Zicheng Gao
 */
public class CollideSystem extends IteratingSystem {
	
	public CollideSystem() {
		super(Aspect.all(CollisionDetection.class, Position.class));
	}

	ComponentMapper<Position> pm;
	ComponentMapper<Angle> angm;
	ComponentMapper<TransformScale> tsm;
	
	ComponentMapper<CollisionDetection> collm;

	ComponentMapper<LabelString> labelM;
	
	private Array<Position> processingPositions;
	
	@Override
	protected void initialize() {
		processingPositions = new Array<Position>();
	}
	
	@Override
	protected void process(int entityId) {
	
		Position pos = pm.getSafe(entityId);
		processingPositions.add(pos);
		
	}

	
	
	@Override
	protected void end() {
		// Anyway, collide.

		
		// if it is ok then push intent to move
		for (Position pos : processingPositions) {
			pos.vec.add(pos.intent);
			pos.intent.setZero();
		}
		
		processingPositions.clear();
	}
	
	

}
