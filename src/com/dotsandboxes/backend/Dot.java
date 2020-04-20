package com.dotsandboxes.backend;

public class Dot {
	
	public final int r;
	public final int c;
	
	public Dot(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	public String toString() {
		return "r:" + r + ",c:" + c;
	}
}
