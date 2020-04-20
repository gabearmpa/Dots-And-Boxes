package com.dotsandboxes.backend;

import java.util.Scanner;

public class HumanPlayer extends Player {
	
	private Scanner keyboard;
	
	public HumanPlayer(int color) {
		super(color);
		this.keyboard = new Scanner(System.in);
	}

	@Override
	public Move play(Board b) {

		System.out.println("Enter two dots, as dot-one-row dot-one-col dot-two-row dot-two-col");
		System.out.print("  > ");
		
		Dot dot1 = new Dot(keyboard.nextInt(), keyboard.nextInt());
		Dot dot2 = new Dot(keyboard.nextInt(), keyboard.nextInt());
		
		return new Move(dot1, dot2);
		
	}

}
