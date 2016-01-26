package net.suizinshu.external.system;

import static net.suizinshu.external.Manager_Keyboard.KeyQuery.*;

import java.util.function.Consumer;

import net.suizinshu.external.Manager_Keyboard.KeyConst;
import net.suizinshu.external.component.*;
import net.suizinshu.external.logic.*;
import net.suizinshu.external.logic.KeyLogic.KeyBinder;
import net.suizinshu.external.logic.KeyLogic.KeyCondition;
import net.suizinshu.external.logic.KeyLogic.KeyConditional;
import net.suizinshu.external.logic.KeyLogic.KeyEvaluable;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;


public class BindableInputSystem extends IteratingSystem {
	private ComponentMapper<InputBinder> act;
	
	public BindableInputSystem() {
		super(Aspect.all(InputBinder.class));
	}
	
	@Override
	protected void process(int entityId) {
		act.get(entityId).binder.accept(entityId);
	}
	
	//
	//
	//
	// Here be bindings
	//
	//
	//
	
//	private ComponentMapper<Velocity> vm;
	private ComponentMapper<Acceleration> am;
	private ComponentMapper<ActiveFriction> frm;
	
//	private ComponentMapper<Angle> angm;
	private ComponentMapper<AngleVelocity> anvm;
	
	private ComponentMapper<TransformScale> tsfsm;
	
	public class Bindings {
		
		/*
		 * A benchmark was performed, querying the condition with a large loop.
		 * Only at the loping 1000000 times did performance decrease.
		 * Before that, 100000 times caused a possible FPS drop.
		 * 
		 * Using KeyQuery shortcuts:
		 * 		1000000 - 11-12 FPS using KeyQuery.
		 * 
		 * Replaced Condition with BooleanSupplier.
		 * 		1000000 - 11-12 FPS using KeyQuery.
		 * 
		 * Replaced Script with Consumer<Integer>.
		 * 		1000000 - 12    FPS using KeyQuery.
		 * 
		 * Smashed everything into KeyLogic.
		 *      1000000 - 11-12 FPS using KeyQuery. 
		 *      
		 * Got rid of SplitConditional and inheritors.
		 * 		1000000 - 12 	FPS using KeyQuery.
		 * 		~260 MB
		 * 
		 * Moved util methods out of KeyUtils and into the containing KeyLogic.
		 * 		1000000 - 12	FPS using KeyQuery.
		 * 		~258 MB
		 * 
		 * Moved KeyCondition lambdas to variables in Bindings class.
		 * 		1000000 - 12	FPS using KeyQuery.
		 * 		~260 MB
		 * 
		 * Replaced the frictiontoggle with FrictionWhenEquilibrium component.
		 * 		1000000 - 13-15 FPS using KeyQuery.
		 * 		~258 MB
		 */
		
		private KeyCondition upNotDown = () -> U() && !D();
		private KeyCondition downNotUp = () -> D() && !U();
		private KeyCondition leftNotRight = () -> L() && !R();
		private KeyCondition rightNotLeft = () -> R() && !L();
		
		public KeyBinder accelMovement(float accel) {
			
			KeyEvaluable up =
					new KeyConditional((id) -> am.getSafe(id).nextActive().y = accel, upNotDown);
			
			KeyEvaluable down = 
					new KeyConditional((id) -> am.getSafe(id).nextActive().y = -accel, downNotUp);
			
			KeyEvaluable left = 
					new KeyConditional((id) -> am.getSafe(id).nextActive().x = -accel, leftNotRight);
			
			KeyEvaluable right = 
					new KeyConditional((id) -> am.getSafe(id).nextActive().x = accel, rightNotLeft);
			
			/*
			 *  !(U() || D() || L() || R()) 		// No arrow keys
			 *	|| ((U() && D()) && !(L() ^ R()))	// Vert. cancel w/o horiz single
			 *	|| ((L() && R()) && !(U() ^ D()))	// Horiz. cancel w/o vert single
			 * 
			 * CNF: (!D() || U()) && (D() || !U()) && (!L() || R()) && (L() || !R())
			 * CNF: (!D || U) && (D || !U) && (!L || R) && (L || !R)
			 */
			
			KeyBinder output = new KeyBinder(up, down, left, right);
			
			return output;
		}
		
		public KeyBinder rotate46(float degrees) {
			KeyEvaluable[] bind4 =
					KeyLogic.toggle(setAngleVelocity(degrees), setAngleVelocity(-degrees), KeyConst.BIND4);
					
			KeyEvaluable[] bind6 = 
					KeyLogic.toggle(setAngleVelocity(-degrees), setAngleVelocity(degrees), KeyConst.BIND6);
			
			KeyBinder output = new KeyBinder(bind4, bind6);
			
			return output;
		}
		
		public KeyBinder scale1235(float x, float y) {
			KeyEvaluable bind1 =
					new KeyConditional(setScale(-x, 0), () -> B1() && !B3());
			KeyEvaluable bind2 =
					new KeyConditional(setScale(0, -y), () -> B2() && !B5());
			KeyEvaluable bind3 =
					new KeyConditional(setScale(x, 0), () -> B3() && !B1());
			KeyEvaluable bind5 =
					new KeyConditional(setScale(0, y), () -> B5() && !B2());
			
			KeyBinder output = new KeyBinder(bind1, bind2, bind3, bind5);
			
			return output;
		}
		
		
//		
//		//     /////
//		///     ////
//		////     ///
//		/////     //
//		////     ///
//		///     ////
//		//     /////
//		
		
		private Consumer<Integer> setAngleVelocity(float degrees) {
			return (id) -> {
				if (anvm.has(id))
					anvm.getSafe(id).deg += degrees;
			};
		}
		
		private Consumer<Integer> setScale(float xScale, float yScale) {
			return (id) -> {
				if (tsfsm.has(id)) {
					tsfsm.getSafe(id).x += xScale;
					tsfsm.getSafe(id).y += yScale;
				}
			};
		}
		
	}
	
}