package net.suizinshu.external.component.collision;

import net.suizinshu.external.util.CollisionScript;

import com.artemis.Component;

public class CollisionBinding extends Component {

	public CollisionScript script;
	
	public CollisionBinding() {}
	
	public CollisionBinding(CollisionScript script) {this.script = script;}
	
}
