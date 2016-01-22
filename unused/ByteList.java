package net.suizinshu.external;

import java.util.ArrayList;

/**
 * When you make a thing and then realize you have to make a whole bunch of other things...
 * Ach! What was the saying about optimization?
 * @author Zicheng Gao
 */
public class ByteList {
	
	private static final int capacity = 16;
	
	/*
	 * Have bytes. Until you are going to fill in the last position, wherein you put an array there instead.
	 */
	
	/** Arraylist of byte arrays... This is so wonky. Oh god. */
	private ArrayList<byte[]> list = new ArrayList<byte[]>(capacity);
	
	private int nextFreeSpace = 0;
	private int internalNFS = 0;
	
	public ByteList() {
	}
	
	public ByteList(ByteList other) {
		this.list = other.list();
		nextFreeSpace = other.nfs();
		internalNFS = other.infs();
	}
	
	/** Add a bunch. */
	public void add(byte... bytes) {
		for (byte b : bytes)
			add(b);
	}
	
	/** Next one over until morning. */
	public void add(byte b) {
		int x = nextFreeSpace / capacity;
		int y = nextFreeSpace % capacity;
		
		if (list.get(x) == null)
			list.set(x, new byte[16]);
		
		list.get(x)[y] = b;
		nextFreeSpace++;
	}
	
	public byte get(int b) {
		int x = nextFreeSpace / capacity;
		int y = nextFreeSpace % capacity;
		
		byte[] get = list.get(x);
		if (get != null)
			return get[y];
		else
			throw new IndexOutOfBoundsException();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Please don't use this unless you have to.
	 * @return Arraylist of byte arrays.
	 */
	protected ArrayList<byte[]> list() {
		return list;
	}
	
	/**
	 * Don't use unless necessary.
	 * @return Index of next free space.
	 */
	protected int nfs() {
		return nextFreeSpace;
	}
	
	protected int infs() {
		return internalNFS;
	}
	
	
}
