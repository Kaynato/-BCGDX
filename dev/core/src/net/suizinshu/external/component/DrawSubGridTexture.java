package net.suizinshu.external.component;

import net.suizinshu.external.StateGraphic;
import net.suizinshu.external.component.interfaces.Tickable;

import com.artemis.Component;

public final class DrawSubGridTexture extends Component implements Tickable {
	


	private int index = 0;
	
	//
	
	public int maxIndex;
	
	public int xInterval, yInterval;
	
	public int row = 0, col = 0;
	
	public int xOff = 0, yOff = 0;
	
	public final int _rows, _cols;
	
	public DrawSubGridTexture(int width, int height, int columns, int rows, int maxIndex) {
		_rows = rows;
		_cols = columns;
		
		xInterval = width / columns;
		yInterval = height / rows;
		
		this.maxIndex = maxIndex;
	}
	
	public DrawSubGridTexture(String name, int columns, int rows, int maxIndex) {
		this(StateGraphic.get(name).getWidth(), StateGraphic.get(name).getHeight(), columns, rows, maxIndex);
	}
	
	//
	//
	//
	
	public void update() {
		row = index % _rows;
		col = index / _cols;
		xOff = row * xInterval;
		yOff = col * yInterval;
	}
	
	public int index() { return index; }
	
	public void index(int index) { this.index = index; update(); }
	
	//
	//
	//
	//
	// Well, I mean, I guess, er, I guess...
	
	//
	//
	// I don't really know how to stick this part into a system?
	
	//
	//
	// Logic in the component...
	
	public byte dir = 1;
	
	public int time = 0;
	
	public int threshold = 30;
	
	public void tick(int ticks) {
		time += ticks;
		if (time > threshold) {
			time = 0;
			increment();
		}
	}
	
	public void tick() {
		tick(1);
	}
	
	/**
	 * Increment the index by dir DIRECTLY. Use with caution.
	 */
	public void increment() {
		index += dir;
		while (index < 0 || index > maxIndex)
			if (index < 0)
				index += maxIndex;
			else if (index > maxIndex)
				index %= (maxIndex + 1);
		update();
	}
	
}
