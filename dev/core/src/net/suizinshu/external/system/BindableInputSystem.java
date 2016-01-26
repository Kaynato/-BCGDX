package net.suizinshu.external.system;

import java.util.function.Consumer;

import net.suizinshu.external.StateKeyboard;
import net.suizinshu.external.StateKeyboard.KeyByte;
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
	
//	private ComponentMapper<Angle> angm;
	private ComponentMapper<AngleVelocity> anvm;
	
	private ComponentMapper<TransformScale> tsfsm;
	
	public class Bindings {
		
		private KeyCondition 
		upNotDown = () -> StateKeyboard.isDown(KeyByte.UP) && !StateKeyboard.isDown(KeyByte.DOWN),
		downNotUp = () -> StateKeyboard.isDown(KeyByte.DOWN) && !StateKeyboard.isDown(KeyByte.UP),
		leftNotRight = () -> StateKeyboard.isDown(KeyByte.LEFT) && !StateKeyboard.isDown(KeyByte.RIGHT),
		rightNotLeft = () -> StateKeyboard.isDown(KeyByte.RIGHT) && !StateKeyboard.isDown(KeyByte.LEFT),
		bind1NotBind3 = () -> StateKeyboard.isDown(KeyByte.BIND1) && !StateKeyboard.isDown(KeyByte.BIND3),
		bind3NotBind1 = () -> StateKeyboard.isDown(KeyByte.BIND3) && !StateKeyboard.isDown(KeyByte.BIND1),
		bind2NotBind5 = () -> StateKeyboard.isDown(KeyByte.BIND2) && !StateKeyboard.isDown(KeyByte.BIND5),
		bind5NotBind2 = () -> StateKeyboard.isDown(KeyByte.BIND5) && !StateKeyboard.isDown(KeyByte.BIND2);		
		
		public KeyBinder accelMovement(float accel) {
			
			KeyEvaluable up =
					new KeyConditional((id) -> am.getSafe(id).nextActive().y = accel, upNotDown);
			
			KeyEvaluable down = 
					new KeyConditional((id) -> am.getSafe(id).nextActive().y = -accel, downNotUp);
			
			KeyEvaluable left = 
					new KeyConditional((id) -> am.getSafe(id).nextActive().x = -accel, leftNotRight);
			
			KeyEvaluable right = 
					new KeyConditional((id) -> am.getSafe(id).nextActive().x = accel, rightNotLeft);
			
			
			KeyBinder output = new KeyBinder(up, down, left, right);
			
			return output;
		}
		
		public KeyBinder rotate46(float degrees) {
			KeyEvaluable[] bind4 =
					KeyLogic.toggle(setAngleVelocity(degrees), setAngleVelocity(-degrees), KeyByte.BIND4);
					
			KeyEvaluable[] bind6 = 
					KeyLogic.toggle(setAngleVelocity(-degrees), setAngleVelocity(degrees), KeyByte.BIND6);
			
			KeyBinder output = new KeyBinder(bind4, bind6);
			
			return output;
		}
		
		public KeyBinder scale1235(float x, float y) {
			KeyEvaluable bind1 =
					new KeyConditional(setScale(-x, 0), bind1NotBind3);
			KeyEvaluable bind2 =
					new KeyConditional(setScale(0, -y), bind2NotBind5);
			KeyEvaluable bind3 =
					new KeyConditional(setScale(x, 0), bind3NotBind1);
			KeyEvaluable bind5 =
					new KeyConditional(setScale(0, y), bind5NotBind2);
			
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