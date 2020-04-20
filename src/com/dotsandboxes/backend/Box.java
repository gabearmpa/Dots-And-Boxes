package com.dotsandboxes.backend;

public class Box {
	
	public static final int EMPTY = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 2;
	
	private int value;
	private Line[] lines;
	
	public Box(Line leftLine, Line topLine, Line rightLine, Line botLine) {
		this.value = Box.EMPTY;
		
		lines = new Line[4];
		
		lines[Line.LEFT_LINE] = leftLine;
		lines[Line.TOP_LINE] = topLine;
		lines[Line.RIGHT_LINE] = rightLine;
		lines[Line.BOT_LINE] = botLine;
	}
	
	
	public boolean updateBox(int player) {
		
		boolean allFilled = true;
		
		for (Line l : lines) {
			if (l.getValue() == Line.EMPTY) {
				allFilled = false;
				break;
			}
		}
		
		if (allFilled) {
			this.value = player;
		}
		
		return allFilled;
		
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public Line getLine(int i) {
		return lines[i];
	}

}
