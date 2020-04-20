package com.dotsandboxes.backend;

public class Move {
	
	private Dot a;
	private Dot b;
	
	public Move(Dot a, Dot b) {
		this.a = a;
		this.b = b;
	}
	
	public Dot first() {
		return a;
	}
	
	public Dot second() {
		return b;
	}
}