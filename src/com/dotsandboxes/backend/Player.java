package com.dotsandboxes.backend;

public abstract class Player {
	
	public final int color;
	
	public Player(int color) {
		this.color = color;
	}
	
	public int otherColor() {
		if (color == Box.BLACK) {
			return Box.WHITE;
		}
		return Box.BLACK;
	}
	
	public abstract Move play(Board b);

}
