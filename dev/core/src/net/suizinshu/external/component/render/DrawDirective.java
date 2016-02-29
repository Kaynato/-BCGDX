package net.suizinshu.external.component.render;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.suizinshu.external.graphic.directive.Directive;
import net.suizinshu.external.util.SortingList;

import com.artemis.Component;

public class DrawDirective extends Component {

	/*
	 * This is inefficient but I am desperate.........
	 */
	
	public SortingList<Directive> directives = new SortingList<Directive>();
	public Map<String, Directive> map = new HashMap<String, Directive>();

	public DrawDirective(Directive... directives) {
		if (directives != null) {
			for (Directive directive : directives) {
				this.directives.add(directive);
				map.put(directive.getName(), directive);
			}
			Collections.sort(this.directives);
		}
		this.directives.monitor();
	}

	// In scenarios where directives' order could be altered, set the directives list to monitor.
	// In scenarios of retrieval, don't.

}
