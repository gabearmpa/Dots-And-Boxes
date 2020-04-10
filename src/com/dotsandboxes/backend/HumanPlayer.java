package com.dotsandboxes.backend;

import java.util.Scanner;

public class HumanPlayer extends Player {
	
	private Scanner keyboard;
	
	public HumanPlayer() {
		this.keyboard = new Scanner(System.in);
	}

	@Override
	public Dot[] play(Board b) {

		System.out.println("Enter two dots, as dot-one-row dot-one-col dot-two-row dot-two-col");
		System.out.print("  > ");
		
		Dot dot1 = new Dot(keyboard.nextInt(), keyboard.nextInt());
		Dot dot2 = new Dot(keyboard.nextInt(), keyboard.nextInt());
		
		return new Dot[] {dot1, dot2};
		
	}

}
