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

		System.out.println("Enter two neighboring space-separated symbols (case sensitive) for dots");
		System.out.print("  > ");
		
		Dot dot1 = b.getDot(keyboard.next().charAt(0));
		Dot dot2 = b.getDot(keyboard.next().charAt(0));
		
		return new Move(dot1, dot2);
		
	}

}
