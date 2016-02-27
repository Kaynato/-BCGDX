package net.suizinshu.external.component.render;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class DrawModel extends Component {

	public ModelInstance model;
	
	public DrawModel(ModelInstance model) {
		this.model = model;
	}

}
