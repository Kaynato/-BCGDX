package net.suizinshu.external.component;

import net.suizinshu.external.StateGraphic;

import com.artemis.Component;

public final class DrawSubGridTexture extends Component {

	public int index = 0;
	
	public int maxIndex;
	
	public int width, height;
	
	public int row = 0, col = 0;
	
	public int xOff = 0, yOff = 0;
	
	public final int _rows, _cols;
	
	public DrawSubGridTexture(int totalWidth, int totalHeight, int columns, int rows, int maxIndex) {
		_rows = rows;
		_cols = columns;
		
		width = totalWidth / columns;
		height = totalHeight / rows;
		
		this.maxIndex = maxIndex;
	}
	
	public DrawSubGridTexture(String name, int columns, int rows, int maxIndex) {
		this(StateGraphic.get(name).getWidth(), StateGraphic.get(name).getHeight(), columns, rows, maxIndex);
	}
	
	/**
	 * Constructor specifying name, columns, and rows. This can go over every subgrid.
	 * @param name of file
	 * @param columns of grid
	 * @param rows of grid
	 */
	public DrawSubGridTexture(String name, int columns, int rows) {
		this(name, columns, rows, columns * rows - 1);
	}
	
	/**
	 * Constructor specifying also column and row set in beginning - USE ONLY IF LOCKING COLUMN OR ROW.<br>
	 * If neither row nor column is locked, the texture will reset to index zero and remain stationary.
	 * @param name of file
	 * @param columns of grid
	 * @param rows of grid
	 * @param maxIndex should not be entered
	 * @param col to lock at start
	 * @param row to lock at start
	 */
	public DrawSubGridTexture(String name, int columns, int rows, int maxIndex, int col, int row) {
		this(name, columns, rows, 0);
		this.row = row;
		this.col = col;
	}
	
	public DrawSubGridTexture(String name, int columns, int rows, int maxIndex, int index) {
		this(name, columns, rows, maxIndex);
		this.index = index;
	}
	
	//
	//
	//	
	
}
