package com.dotsandboxes.backend;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Dots and Boxes");
		System.out.print("Enter number of rows: ");
		int rows = keyboard.nextInt();
		System.out.print("Enter number of columns: ");
		int cols = keyboard.nextInt();
		
		Game game = new Game(rows, cols);
		
		long startTime = System.nanoTime();
		
		game.gameLoop();
		
		long endTime = System.nanoTime();
		
		long duration = endTime - startTime;
		
		System.out.println("Duration (msec): " + (duration / 1000000));

		
		System.out.println("Place Line (msec): " + (Board.totalPlaceLineNanos / 1000000));
		
		System.out.println("Undo Line (msec): " + (Board.totalUndoLineNanos / 1000000));
		
		System.out.println("Get Moves (msec): " + (Board.totalGetMovesNanos / 1000000));
		
		System.out.println("Sort Moves (msec): " + (Board.totalSortMovesNanos / 1000000));
		
		keyboard.close();
	}

}
