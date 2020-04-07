package com.dotsandboxes.backend;

public class Line {
	
	public static final int EMPTY = 0;
	public static final int FULL = 1;
	
	public static final int LEFT_LINE = 0;
	public static final int TOP_LINE = 1;
	public static final int RIGHT_LINE = 2;
	public static final int BOT_LINE = 3;
	
	private int value;
	
	public Line() {
		setValue(Line.EMPTY);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
